package com.abaya.picacho;

import com.abaya.picacho.biz.entity.Document;
import com.abaya.picacho.biz.entity.Regulation;
import com.abaya.picacho.biz.entity.Schedule;
import com.abaya.picacho.biz.repository.DocumentRepository;
import com.abaya.picacho.biz.repository.RegulationRepository;
import com.abaya.picacho.biz.repository.ScheduleRepository;
import com.abaya.picacho.common.config.FileStorageProperties;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgType;
import com.abaya.picacho.org.repository.OrganizationRepository;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.RuleType;
import com.abaya.picacho.user.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner createDefaultAccount(AccountRepository repository) {
        return (args) -> {
            Account account = new Account("ADMIN", "admin", "管理员", RuleType.admin);
            if (repository.findByUsernameIgnoreCase("ADMIN") == null) {
                repository.save(account);
            }
            // account = new Account("ZHANGSAN", "zhangsan", "张三", RuleType.employee);
            // repository.save(account);
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

    @Bean
    public CommandLineRunner createOrganization(OrganizationRepository repository) {
        return (args) -> {
            Organization organization = new Organization(0, "ROOT", null, OrgType.department, "公司", "公司根节点");
            if (repository.findByCodeIgnoreCase("ROOT") == null) {
                repository.save(organization);
            }

            /*organization = new Organization(1, "JSB", "ROOT", OrgType.department, "技术部", "负责开发工作");
            repository.save(organization);

            organization = new Organization(1, "RLZYB", "ROOT", OrgType.department, "人力资源部", "招聘、薪酬、培训");
            repository.save(organization);

            organization = new Organization(2, "RLZYB-XCS", "RLZYB", OrgType.department, "人力资源部薪酬室", "薪酬");
            repository.save(organization);

            organization = new Organization(1, "BGS", "ROOT", OrgType.department, "办公室", "负责相关行政事宜");
            repository.save(organization);

            organization = new Organization(2, "ZHANGSAN", "BGS", OrgType.employee, "张三", "办公室主任");
            repository.save(organization);*/
        };
    }

    // @Bean
    /*public CommandLineRunner createAnnouncement(AnnouncementRepository repository) {
        return args -> {
            Announcement announcement = new Announcement("春假放假通知", "通知", "在途", LocalDateTime.now());
            repository.save(announcement);

            announcement = new Announcement("内部管理会会议纪要", "通知", "撤销", LocalDateTime.now());
            repository.save(announcement);
        };
    }*/

    // @Bean
    public CommandLineRunner createRegulation(RegulationRepository repository) {
        return args -> {
            Regulation regulation = new Regulation("请假制度", "人事制度", "在途", LocalDateTime.now());
            repository.save(regulation);

            regulation = new Regulation("会议室使用安排", "管理制度", "撤销", LocalDateTime.now());
            repository.save(regulation);
        };
    }
}
