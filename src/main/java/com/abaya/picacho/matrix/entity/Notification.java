package com.abaya.picacho.matrix.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  protected Long key;

  private String title;
  private String type;
  private String announcer;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date announceDate;

  public Notification() {

  }

  public Notification(String title, String type, String announcer, Date announceDate) {
    this.title = title;
    this.type = type;
    this.announcer = announcer;
    this.announceDate = announceDate;
  }
}
