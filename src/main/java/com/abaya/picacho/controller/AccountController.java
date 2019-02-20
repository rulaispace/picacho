package com.abaya.picacho.controller;


import com.abaya.picacho.model.Account;
import com.abaya.picacho.util.RandomStringUuidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AccountController {
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "login")
    public Account login(@RequestBody Account account) throws Exception {
        Assert.notNull(account, "传入参数不能为空！");
        account.setToken(RandomStringUuidUtils.uuid());
        throw new Exception("Some Exception");
        // return account;
    }
}
