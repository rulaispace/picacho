package com.abaya.picacho.org.controller;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.util.ConversionUtils;
import com.abaya.picacho.org.entity.Organization;
import com.abaya.picacho.org.model.OrgNode;
import com.abaya.picacho.org.model.OrgType;
import com.abaya.picacho.org.service.OrganizationService;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.user.model.RuleType;
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

@Controller
@Slf4j
public class OrganizationController {
    @Autowired
    private OrganizationService service = null;

    @Data
    @NoArgsConstructor
    static class DeactivateRequest extends AuthorizedRequest {
        @NotNull(message = "用户名不能为空")
        private String username;
    }

    @Data
    @NoArgsConstructor
    static class ActivateRequest extends DeactivateRequest {
        private RuleType rule;
    }

    @Data
    @NoArgsConstructor
    static class OrgAddRequest extends AuthorizedRequest {
        @NotNull(message = "父组织机构编码不能为空！")
        private String parentCode;
        @NotNull(message = "组织机构编码不能为空！")
        private String code;
        @NotNull(message = "名称不能为空！")
        private String name;
        @NotNull(message = "未知的节点类型！")
        private OrgType type;
        private String description;
    }

    @Data
    @NoArgsConstructor
    static class OrgModifyRequest extends AuthorizedRequest {
        @NotNull(message = "组织机构编码不能为空！")
        private String code;
        private String parentCode;
        private String name;
        private String description;
    }

    @Data
    @NoArgsConstructor
    static class OrgDeleteRequest extends AuthorizedRequest {
        @NotNull(message = "组织机构编码不能为空！")
        private String code;
    }

    @Data
    @NoArgsConstructor
    static class AccountPayload {
        private String name;
        private String username;
        private String password;
        private RuleType rule;
        private CommonState state;
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
    @PostMapping(value = "org/activate")
    public Response<AccountPayload> activate(@Valid @RequestBody ActivateRequest request) throws Exception {
        return Response.success(service.activateUser(request.getUsername(), request.getRule(), request.getOperator()), AccountPayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/add")
    public Response<OrgNodePayload> addOrganization(@Valid @RequestBody OrgAddRequest request) throws ServiceException {
        Organization organization = ConversionUtils.convert(request, Organization.class);
        return Response.success(service.addOrganization(organization), OrgNodePayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/deactivate")
    public Response<AccountPayload> deactivate(@Valid @RequestBody DeactivateRequest request) throws Exception {
        return Response.success(service.deactivateUser(request.getUsername(), request.getOperator()), AccountPayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/delete")
    public Response deleteOrganization(@Valid @RequestBody OrgDeleteRequest request) throws ServiceException {
        service.deleteOrganization(request.getCode(), request.getOperator());
        return Response.success("节点(%s)删除成功", request.getCode());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/query")
    public Response<OrgNode> queryOrganization(@Valid @RequestBody AuthorizedRequest request) throws ServiceException {
        return Response.success(service.queryOrganizationAsTree());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "org/modify")
    public Response<OrgNodePayload> modifyOrganization(@Valid @RequestBody OrgModifyRequest request) throws ServiceException {
        Organization organization = ConversionUtils.convert(request, Organization.class);
        return Response.success(service.updateOrganization(organization), OrgNodePayload.class);
    }
}
