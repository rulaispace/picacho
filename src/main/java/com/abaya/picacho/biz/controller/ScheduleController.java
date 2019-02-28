package com.abaya.picacho.biz.controller;

import com.abaya.picacho.biz.entity.Schedule;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.biz.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class ScheduleController {
    @Autowired
    private ScheduleService service = null;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "schedule/query")
    public Response<List<Schedule>> querySchedule() throws Exception {
       return Response.success(service.queryAll());
    }
}
