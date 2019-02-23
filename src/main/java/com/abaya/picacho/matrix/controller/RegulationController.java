package com.abaya.picacho.matrix.controller;

import com.abaya.picacho.matrix.entity.Regulation;
import com.abaya.picacho.matrix.service.RegulationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class RegulationController {
    @Autowired
    private RegulationService service = null;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "regulation/query")
    public List<Regulation> queryNotifications() throws Exception {
        return service.queryAll();
    }
}
