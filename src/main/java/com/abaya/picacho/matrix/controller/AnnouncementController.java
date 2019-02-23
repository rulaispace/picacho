package com.abaya.picacho.matrix.controller;

import com.abaya.picacho.matrix.entity.Announcement;
import com.abaya.picacho.matrix.service.AnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class AnnouncementController {
    @Autowired
    private AnnouncementService service = null;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "announcement/query")
    public List<Announcement> queryAnnouncementList() throws Exception {
        return service.queryAll();
    }
}
