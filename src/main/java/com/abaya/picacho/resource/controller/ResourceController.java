package com.abaya.picacho.resource.controller;

import com.abaya.picacho.common.exception.ServiceException;
import com.abaya.picacho.common.model.AuthorizedRequest;
import com.abaya.picacho.common.model.CommonState;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.util.ConversionUtils;
import com.abaya.picacho.resource.entity.Resource;
import com.abaya.picacho.resource.service.ResourceService;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ResourceController {
    @Autowired
    private ResourceService service = null;

    @Data
    @NoArgsConstructor
    static class ResourceAddRequest extends AuthorizedRequest {
        @NotNull(message = "资源编号不能为空")
        private String code;
        @NotNull(message = "资源名称不能为空")
        private String name;
    }

    @Data
    @NoArgsConstructor
    static class ResourceCommonRequest extends AuthorizedRequest {
        @NotNull(message = "资源编号不能为空")
        private String code;
    }

    @Data
    @NoArgsConstructor
    static class ResourceModifyRequest extends AuthorizedRequest {
        @NotNull(message = "资源编号不能为空")
        private String code;
        private String name;
    }

    @Data
    @NoArgsConstructor
    static class ResourcePayload {
        private Long id;
        private String name;
        private String code;
        private CommonState state;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        protected LocalDateTime createDateTime;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/add")
    public Response<ResourcePayload> addResource(@Valid @RequestBody ResourceAddRequest request) throws Exception {
        request.setCode(request.getCode().toUpperCase());
        Resource resource = ConversionUtils.convert(request, Resource.class);
        return Response.success(service.addResource(resource), ResourcePayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/delete")
    public Response deleteResource(@Valid @RequestBody ResourceController.ResourceCommonRequest request) throws ServiceException {
        service.deleteResource(request.getCode(), request.getOperator());
        return Response.success("资源(%s)删除成功", request.getCode());
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/modify")
    public Response<ResourcePayload> modifyResource(@Valid @RequestBody ResourceModifyRequest request) throws Exception {
        Resource resource = ConversionUtils.convert(request, Resource.class);
        return Response.success(service.updateResource(resource), ResourcePayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/query")
    public Response<List<ResourcePayload>> queryResource(@Valid @RequestBody AuthorizedRequest request) throws Exception {
        return Response.success(service.queryAll(), ResourcePayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/activate")
    public Response<ResourcePayload> activateResource(@Valid @RequestBody ResourceCommonRequest request) throws Exception {
        return Response.success(service.activateResource(request.getCode(), request.getOperator()), ResourcePayload.class);
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "resource/deactivate")
    public Response<ResourcePayload> deactivateResource(@Valid @RequestBody ResourceCommonRequest request) throws Exception {
        return Response.success(service.deactivateResource(request.getCode(), request.getOperator()), ResourcePayload.class);
    }
}
