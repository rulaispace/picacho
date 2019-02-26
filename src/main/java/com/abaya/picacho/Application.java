package com.abaya.picacho;

import com.abaya.picacho.matrix.entity.*;
import com.abaya.picacho.matrix.repository.*;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.repository.OrganizationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // @Bean
    public CommandLineRunner createAdminAccount(AccountRepository repository) {
        return (args) -> {
          Account account = new Account("admin", "admin", "管理员", "admin");
          repository.save(account);

          account = new Account("zhangsan", "zhangsan", "张三", "user");
          repository.save(account);
        };
    }

    // @Bean
    public CommandLineRunner createNotifications(NotificationRepository repository) {
        return (args) -> {
            Notification notification = new Notification("春节放假通知", "变更", "王小二", new Date());
            repository.save(notification);

            notification = new Notification("内部管理会会议纪要", "通知", "张小三", new Date());
            repository.save(notification);
        };
    }

    // @Bean
    public CommandLineRunner createSchedule(ScheduleRepository repository) {
        return (args) -> {
            Schedule schedule = new Schedule(8, "晨会");
            repository.save(schedule);

            for (int i=9; i<20; i++) {
                schedule = new Schedule(i, "晨会");
                repository.save(schedule);
            }
        };
    }

    // @Bean
    public CommandLineRunner createDocument(DocumentRepository repository) {
        return (args) -> {
            for (int i=0; i<10; i++) {
                Document document = new Document("参考文献【" + i + "】");
                repository.save(document);
            }
        };
    }

    // @Bean
    public CommandLineRunner createOrganization(OrganizationRepository repository) {
        return (args) -> {
            Organization organization = new Organization(0, "ROOT", null, "department", "公司", "公司根节点");
            repository.save(organization);

            organization = new Organization(1, "JSB", "ROOT", "department", "技术部", "负责开发工作");
            repository.save(organization);

            organization = new Organization(1, "RLZYB", "ROOT", "department", "人力资源部", "招聘、薪酬、培训");
            repository.save(organization);

            organization = new Organization(2, "RLZYB-XCS", "RLZYB", "department", "人力资源部薪酬室", "薪酬");
            repository.save(organization);

            organization = new Organization(1, "BGS", "ROOT", "department", "办公室", "负责相关行政事宜");
            repository.save(organization);

            organization = new Organization(2, "ZHANGSAN", "BGS", "employee", "张三", "办公室主任");
            repository.save(organization);
        };
    }

    // @Bean
    public CommandLineRunner createResource(ResourceRepository repository) {
        return args -> {
            for (int i=0; i<6; i++) {
                Resource resource = new Resource("会议室" + i, "ROOM-" + i, "可用", new Date());
                repository.save(resource);
            }
        };
    }

    // @Bean
    public CommandLineRunner createAnnouncement(AnnouncementRepository repository) {
        return args -> {
            Announcement announcement = new Announcement("春假放假通知", "通知", "在途", new Date());
            repository.save(announcement);

            announcement = new Announcement("内部管理会会议纪要", "通知", "撤销", new Date());
            repository.save(announcement);
        };
    }

    // @Bean
    public CommandLineRunner createRegulation(RegulationRepository repository) {
        return args -> {
            Regulation regulation = new Regulation("请假制度", "人事制度", "在途", new Date());
            repository.save(regulation);

            regulation = new Regulation("会议室使用安排", "管理制度", "撤销", new Date());
            repository.save(regulation);
        };
    }
}
