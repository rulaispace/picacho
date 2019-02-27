package com.abaya.picacho.user.controller;


import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.entity.AccountBase;
import com.abaya.picacho.user.model.SystemUser;
import com.abaya.picacho.user.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
public class AccountController {
    @Autowired
    private AccountService service = null;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "login")
    public Response<Account> login(@RequestBody AccountBase request) throws Exception {
        return Response.success(service.login(request.getUsername(), request.getPassword()));
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "user/query")
    public Response<List<SystemUser>> queryUserList() {
        return Response.success(service.queryAllUsers());
    }
}
