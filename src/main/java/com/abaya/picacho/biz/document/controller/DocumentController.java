package com.abaya.picacho.biz.document.controller;

import com.abaya.picacho.biz.document.entity.Document;
import com.abaya.picacho.common.model.Response;
import com.abaya.picacho.biz.document.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class DocumentController {

    @Autowired
    private DocumentService service = null;

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "document/query")
    public Response<List<Document>> queryNotifications() throws Exception {
        return Response.success(service.queryAll());
    }
}
