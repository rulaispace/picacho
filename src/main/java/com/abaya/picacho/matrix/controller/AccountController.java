package com.abaya.picacho.matrix.controller;


import com.abaya.picacho.matrix.entity.Account;
import com.abaya.picacho.matrix.entity.AccountBase;
import com.abaya.picacho.matrix.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class AccountController {
    @Autowired
    private AccountService service = null;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "login")
    public Account login(@RequestBody AccountBase request) throws Exception {
        return service.login(request.getUsername(), request.getPassword());
    }
}