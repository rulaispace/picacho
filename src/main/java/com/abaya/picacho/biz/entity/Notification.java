package com.abaya.picacho.biz.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification extends EntityBase {
  private String title;
  private String type;
  private String announcer;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDateTime announceDate;

  public Notification() {

  }

  public Notification(String title, String type, String announcer, LocalDateTime announceDate) {
    this.title = title;
    this.type = type;
    this.announcer = announcer;
    this.announceDate = announceDate;
  }
}
