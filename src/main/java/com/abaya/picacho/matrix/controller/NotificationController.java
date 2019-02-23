package com.abaya.picacho.matrix.controller;

import com.abaya.picacho.matrix.entity.Notification;
import com.abaya.picacho.matrix.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class NotificationController {
  @Autowired
  private NotificationService service = null;

  @CrossOrigin
  @ResponseBody
  @RequestMapping(value = "notification/query")
  public List<Notification> queryNotifications() throws Exception {
    return service.queryAll();
  }
}
