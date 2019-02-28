package com.abaya.picacho.org.controller;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.ExtensibleEntity;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.model.TreeNode;
import com.abaya.picacho.common.util.ConversionUtils;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.org.model.OrgType;
import com.abaya.picacho.org.service.OrganizationService;
import com.abaya.picacho.user.model.AccountState;
import com.abaya.picacho.user.model.Rule;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller
@Slf4j
public class OrganizationController {
    @Autowired
    private OrganizationService service = null;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/query")
    public Response<OrgNode> queryOrganization() throws ServiceException {
        return Response.success(service.queryOrganizationAsTree());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/add")
    public Response<OrgNodePayload> addOrganization(@Valid @RequestBody OrgAddRequest request) throws ServiceException {
        Organization organization = ConversionUtils.convert(request, Organization.class);
        return Response.success(service.addOrganization(organization), OrgNodePayload.class);
    }

    @Data
    @NoArgsConstructor
    static class OrgAddRequest {
        @NotNull(message = "父组织机构编码不能为空！")
        private String parentCode;
        @NotNull(message = "组织机构编码不能为空！")
        private String code;
        @NotNull(message = "名称不能为空！")
        private String name;
        @NotNull(message = "未知的节点类型！")
        private OrgType type;
        private String description;
        @NotNull(message = "传入的操作员token不能为空！")
        private String token;
    }

    @Data
    @NoArgsConstructor
    static class OrgNodePayload {
        private String parentCode;
        private String code;
        private String name;
        private OrgType type;
        private String description;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/delete")
    public Response<TreeNode> deleteOrganization() throws ServiceException {
        return Response.success(service.queryOrganizationAsTree());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/modify")
    public Response<OrgNode> modifyOrganization(@RequestBody OrgNode node) throws ServiceException {
        OrgNode payload = service.updateOrganization(node);
        if (payload == null) return Response.fail("更新失败");

        return Response.success(payload);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/activate")
    public Response<AccountPayload> activate(@Valid @RequestBody ActivateRequest request) throws Exception {
        return Response.success(service.activateUser(request.getUsername(), request.getRule()), AccountPayload.class);
    }

    @Data
    @NoArgsConstructor
    static class ActivateRequest {
        @NotNull(message = "用户名不能为空")
        private String username;
        @NotNull(message = "角色不能为空")
        private Rule rule;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/deactivate")
    public Response<AccountPayload> deactivate(@RequestBody ExtensibleEntity request) throws Exception {
        String username = request.get("username", String.class);
        Assert.notNull(username, "用户名不能为空");

        return Response.success(service.deactivateUser(username), AccountPayload.class);
    }

    @Data
    @NoArgsConstructor
    static class AccountPayload {
        private String name;
        private String username;
        private String password;
        private Rule rule;
        private AccountState state;
    }
}
