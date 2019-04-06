package com.abaya.picacho.biz.schedule.service.impl;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.service.AccountService;
import com.abaya.picacho.biz.resource.entity.Resource;
import com.abaya.picacho.biz.resource.service.ResourceService;
import com.abaya.picacho.biz.schedule.entity.Schedule;
import com.abaya.picacho.biz.schedule.entity.ScheduleAttachment;
import com.abaya.picacho.biz.schedule.entity.ScheduleParticipant;
import com.abaya.picacho.biz.schedule.entity.ScheduleResource;
import com.abaya.picacho.biz.schedule.repository.ScheduleAttachmentRepository;
import com.abaya.picacho.biz.schedule.repository.ScheduleRepository;
import com.abaya.picacho.biz.schedule.service.ScheduleService;
import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private AccountService accountService = null;

    @Autowired
    private ResourceService resourceService = null;

    @Autowired
    private ScheduleRepository scheduleRepository = null;

    @Autowired
    private ScheduleAttachmentRepository attachmentRepository = null;

    @Override
    public List<Schedule> queryAll() {
        return scheduleRepository.findAll();
    }

    @Override
    @Transactional
    public Schedule insertSchedule(Schedule schedule) throws ServiceException {
        Assert.notNull(schedule, "传入待插入日程不能为空！");

        LocalDateTime startTime = schedule.getStartTime();
        LocalDateTime finishTime = schedule.getFinishTime();
        if (!startTime.isBefore(finishTime)) throw new ServiceException("时间设置错误，开始时间晚于结束时间");

        String message = validateParticipate(schedule.getParticipants(), startTime, finishTime);
        if (message != null) throw new ServiceException(message);

        message = validateResource(schedule.getResources(), startTime, finishTime);
        if (message != null) throw new ServiceException(message);

        EntityUtils.overrideOperator(schedule.getParticipants(), schedule.getModifier());
        EntityUtils.overrideOperator(schedule.getAttachments(), schedule.getModifier());
        EntityUtils.overrideOperator(schedule.getResources(), schedule.getModifier());

        schedule.getParticipants().forEach(participant -> participant.setSchedule(schedule));
        schedule.getAttachments().forEach(attachment -> attachment.setSchedule(schedule));
        schedule.getResources().forEach(resource -> resource.setSchedule(schedule));

        return scheduleRepository.save(schedule);
    }

    private String validateParticipate(List<ScheduleParticipant> participants, LocalDateTime startTime, LocalDateTime finishTime) {
        Assert.notNull(participants, "传入的参加人员清单不能为空！");
        for (ScheduleParticipant participant : participants) {
            String message = validateUsername(participant.getUsername(), startTime, finishTime);
            if (message != null) return message;
        }

        return null;
    }

    private String validateUsername(String username, LocalDateTime startTime, LocalDateTime finishTime) {
        Assert.notNull(username, "传入的参会人员的登录名不能为空");

        // 确认该用户是否存在
        if (!accountService.isValidAccount(username)) {
            return format("用户[{0}]不存在，请确认参会人员的合法性！", username);
        }

        // 确认该用户在`startTime`和`finishTime`之间没有其他安排
        List<Schedule> participateSchedules = scheduleRepository.findAllByUsernameAndStartTimeAndEndTime(username, startTime, finishTime);
        if (participateSchedules != null && participateSchedules.size() > 0) {
            return format("用户[{0}]在指定时间段内有其他安排，无法参加该活动！", username);
        }

        return null;
    }

    private String validateResource(List<ScheduleResource> resources, LocalDateTime startTime, LocalDateTime finishTime) throws ServiceException {
        if (resources == null) return null;
        for (ScheduleResource resource : resources) {
            String message = validateResource(resource, startTime, finishTime);
            if (message != null) return message;
        }

        return null;
    }

    private String validateResource(ScheduleResource resource, LocalDateTime startTime, LocalDateTime finishTime) throws ServiceException {
        if (resource == null) return null;
        if (!resourceService.resourceIsValid(resource.getResourceId())) return format("资源[{0}]无法使用，无法新增活动！", resource.getResourceId());
        List<Schedule> resourceUsedSchedules = scheduleRepository.findAllByResourceIdAndStartTimeAndEndTime(resource.getResourceId(), startTime, finishTime);
        if (resourceUsedSchedules != null && resourceUsedSchedules.size() > 0) {
            return format("资源[{0}]在规定时间段内已被占用，无法使用", resource.getResourceId());
        }
        return null;
    }

    @Override
    public List<Account> queryIdleAccount(LocalDateTime startTime, LocalDateTime finishTime) {
        Assert.notNull(startTime, "开始时间不能为空");
        Assert.notNull(finishTime, "结束时间不能为空");

        Assert.isTrue(startTime.isBefore(finishTime), "开始时间必须小于结束时间");
        List<Account> accounts = accountService.queryAllValidAccount();
        if (accounts == null || accounts.size() == 0) return new ArrayList<>();

        return accounts.stream().filter(account -> {
            List<Schedule> schedules = scheduleRepository.findAllByUsernameAndStartTimeAndEndTime(account.getUsername(), startTime, finishTime);
            return schedules == null || schedules.size() == 0;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Schedule> queryAllSchedules(String username) {
        return scheduleRepository.findAllByUsernameOrderByStartTimeDesc(username);
    }

    @Override
    public Schedule querySingleSchedule(Long id) throws ServiceException {
        Schedule schedule = scheduleRepository.findById(id).orElse(new Schedule());
        if (schedule.getId() == null) throw new ServiceException("待查询日程不存在！");

        return schedule;
    }

    @Override
    public List<Resource> queryIdleResource(LocalDateTime startTime, LocalDateTime finishTime) {
        Assert.notNull(startTime, "开始时间不能为空");
        Assert.notNull(finishTime, "结束时间不能为空");

        Assert.isTrue(startTime.isBefore(finishTime), "开始时间必须小于结束时间");
        List<Resource> resources = resourceService.queryAllValidResource();
        if (resources == null || resources.size() == 0) return new ArrayList<>();

        List<Resource> result =  resources.stream().filter(resource -> {
            List<Schedule> schedules =  scheduleRepository.findAllByResourceIdAndStartTimeAndEndTime(resource.getId(), startTime, finishTime);
            return schedules == null || schedules.size() == 0;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public ScheduleAttachment addAttachment(ScheduleAttachment attachment) {
        Assert.notNull(attachment, "附件内容不能为空！");
        return attachmentRepository.save(attachment);
    }

    @Override
    public ScheduleAttachment queryAttachment(Long id, String operator) {
        Assert.notNull(id, "附件编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");
        Optional<ScheduleAttachment> scheduleAttachment = attachmentRepository.findById(id);
        if (scheduleAttachment.isPresent()) return scheduleAttachment.get();

        return null;
    }

    @Override
    public void deleteAttachment(Long id, String operator) {
        Assert.notNull(id, "附件编号不能为空");
        Assert.notNull(operator, "操作人员不能为空");
        attachmentRepository.deleteById(id);
    }
}
