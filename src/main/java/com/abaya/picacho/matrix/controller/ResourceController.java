package com.abaya.picacho.matrix.controller;

import com.abaya.picacho.matrix.entity.Resource;
import com.abaya.picacho.matrix.service.ResourceService;
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
    public List<Resource> queryNotifications() throws Exception {
        return service.queryAll();
    }
}
