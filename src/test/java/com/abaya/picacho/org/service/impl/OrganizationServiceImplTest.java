package com.abaya.picacho.org.service.impl;

import com.abaya.picacho.org.service.OrganizationAidService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationServiceImplTest {
    @Autowired
    private OrganizationAidService service;

    @Test
    public void testGenerateFullName() {
        System.out.println(service.generateOrganizationFullName("ZHANGSAN"));
    }
}