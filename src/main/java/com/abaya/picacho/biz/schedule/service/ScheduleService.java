package com.abaya.picacho.biz.schedule.service;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.resource.entity.Resource;
import com.abaya.picacho.biz.schedule.entity.Schedule;
import com.abaya.picacho.biz.schedule.entity.ScheduleAttachment;
import com.abaya.picacho.common.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {
    List<Schedule> queryAll();

    Schedule insertSchedule(Schedule schedule) throws ServiceException;

    List<Account> queryIdleAccount(LocalDateTime startTime, LocalDateTime finishTime);

    List<Schedule> queryAllSchedules(String username);

    Schedule querySingleSchedule(Long id) throws ServiceException;

    List<Resource> queryIdleResource(LocalDateTime startTime, LocalDateTime finishTime);

    ScheduleAttachment addAttachment(ScheduleAttachment attachment);

    ScheduleAttachment queryAttachment(Long id, String operator);

    void deleteAttachment(Long id, String operator);
}
