package com.abaya.picacho;

import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.biz.account.model.RuleType;
import com.abaya.picacho.biz.account.repository.AccountRepository;
import com.abaya.picacho.biz.organization.entity.Organization;
import com.abaya.picacho.biz.organization.model.OrgType;
import com.abaya.picacho.biz.organization.repository.OrganizationRepository;
import com.abaya.picacho.common.config.FileStorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

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
        };
    }

    @Bean
    public CommandLineRunner createOrganization(OrganizationRepository repository) {
        return (args) -> {
            Organization organization = new Organization(0, "ROOT", null, OrgType.department, "公司", "公司根节点");
            if (repository.findByCodeIgnoreCase("ROOT") == null) {
                repository.save(organization);
            }
        };
    }
}
