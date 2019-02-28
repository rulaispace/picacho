package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.org.service.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationServiceImplTest {
    @Autowired
    private OrganizationService organizationService;

    @Test
    public void testGenerateFullName() {
        System.out.println(organizationService.generateOrganizationFullName("ZHANGSAN"));
    }

}