package com.abaya.picacho.user.controller;

import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.user.entity.Account;
import com.abaya.picacho.user.model.AccountState;
import com.abaya.picacho.user.model.Rule;
import com.abaya.picacho.user.service.AccountService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class AccountController {
    @Autowired
    private AccountService service = null;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "login")
    public Response<LoginPayload> login(@RequestBody LoginRequest request) throws Exception {
        Account account = service.login(request.getUsername(), request.getPassword());
        return Response.success(account, LoginPayload.class);
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    static class LoginPayload {
        private String username;
        private Rule rule;
        private String token;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "user/query")
    public Response<List<SystemUser>> queryUserList() {
        return Response.success(SystemUser.class, service.queryAllAccount());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class SystemUser {
        private Long id;
        private String name;
        private String userName;
        private String department;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date createdDate;

        private AccountState state;
    }
}

