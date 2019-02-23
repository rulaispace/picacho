package com.abaya.picacho.org.controller;

import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.org.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class OrganizationController {
  @Autowired
  private OrganizationService service = null;

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = "org/query")
  public NestedListNode queryNotifications() throws Exception {
    return service.queryOrganizationAsTree();
  }
}
