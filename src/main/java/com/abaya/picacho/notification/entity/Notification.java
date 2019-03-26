package com.abaya.picacho.notification.entity;

import com.abaya.picacho.common.entity.EntityBase;
import com.abaya.picacho.notification.model.NotificationState;
import com.abaya.picacho.notification.model.NotificationType;
import com.abaya.picacho.notification.model.SourceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Entity
public class Notification extends EntityBase {
  private String title;

  private Long sourceId;

  @Enumerated(EnumType.STRING)
  private SourceType sourceType;

  @Enumerated(EnumType.STRING)
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  private NotificationState state;

  private String announcer;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDateTime announceDate;

  @Override
  public String toString() {
    return "Notification{" +
            "title='" + title + '\'' +
            ", sourceId=" + sourceId +
            ", sourceType=" + sourceType +
            ", type=" + type +
            ", state=" + state +
            ", announcer='" + announcer + '\'' +
            ", announceDate=" + announceDate +
            ", id=" + id +
            ", creator='" + creator + '\'' +
            ", modifier='" + modifier + '\'' +
            ", createDateTime=" + createDateTime +
            ", updateDateTime=" + updateDateTime +
            '}';
  }
}
