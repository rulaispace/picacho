package com.abaya.picacho.biz.account.controller;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.ExtensibleEntity;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.biz.account.entity.Account;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.biz.account.model.RuleType;
import com.abaya.picacho.biz.account.service.AccountService;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class AccountController {
    @Autowired
    private AccountService service = null;

    @Data
    static class LoginRequest extends ExtensibleEntity {
        @NotNull(message = "用户名不能为空")
        private String username;
        @NotNull(message = "登录密码不能为空")
        private String password;
    }

    @Data
    @NoArgsConstructor
    static class LoginPayload {
        private String username;
        private RuleType rule;
        private String token;
        private boolean shouldChangePassword;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserPayload {
        private Long id;
        private String name;
        private String username;
        private String department;
        private RuleType rule;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDateTime createDateTime;

        private CommonState state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ResetPasswordRequest extends AuthorizedRequest {
        @NotNull(message = "用户名不能为空")
        private String username;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ChangePasswordRequest extends ResetPasswordRequest {
        @NotNull(message = "原密码不能为空")
        private String oldPassword;

        @NotNull(message = "新密码不能为空")
        private String newPassword;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class PasswordModifyPayload {
        private String name;
        private String username;
        private String password;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "login")
    public Response<LoginPayload> login(@RequestBody LoginRequest request) throws Exception {
        Account account = service.login(request.getUsername(), request.getPassword());
        return Response.success(account, LoginPayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "user/query")
    public Response<List<UserPayload>> queryUserList(@Valid @RequestBody AuthorizedRequest request) {
        return Response.success(service.queryAllAccount(), UserPayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "user/resetPassword")
    public Response<PasswordModifyPayload> resetPassword(@Valid @RequestBody ResetPasswordRequest request) throws ServiceException {
        Account account = service.resetPassword(request.getUsername(), request.getOperator());
        return Response.success(account, PasswordModifyPayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "user/changePassword")
    public Response<PasswordModifyPayload> changePassword(@Valid @RequestBody ChangePasswordRequest request) throws ServiceException {
        Account account = service.changePassword(request.getUsername(), request.getOldPassword(), request.getNewPassword(), request.getOperator());
        return Response.success(account, PasswordModifyPayload.class);
    }
}

