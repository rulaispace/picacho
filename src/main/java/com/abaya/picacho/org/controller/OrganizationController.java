package com.abaya.picacho.org.controller;

import com.abaya.picacho.matrix.model.NestedListNode;
import com.abaya.picacho.matrix.model.Response;
import com.abaya.picacho.org.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Response<NestedListNode> queryOrganization() throws Exception {
    return Response.success(service.queryOrganizationAsTree());
  }

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = "org/add")
  public Response<NestedListNode> addOrganization() throws Exception {
    return Response.success(service.queryOrganizationAsTree());
  }

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = "org/delete")
  public Response<NestedListNode> deleteOrganization() throws Exception {
    return Response.success(service.queryOrganizationAsTree());
  }

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = "org/modify")
  public Response<NestedListNode> modifyOrganization(@RequestBody NestedListNode node) throws Exception {
    NestedListNode payload = service.updateOrganization(node);
    if (payload == null) return Response.fail("更新失败");

    return Response.success(payload);
  }
}
