package com.abaya.picacho.common.controller;

import com.abaya.picacho.common.entity.Resource;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.common.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class ResourceController {
    @Autowired
    private ResourceService service = null;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "resource/query")
    public Response<List<Resource>> queryNotifications() throws Exception {
        return Response.success(service.queryAll());
    }
}