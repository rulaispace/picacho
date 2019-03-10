package com.abaya.picacho.announcement.entity;

import com.abaya.picacho.announcement.model.AnnouncementState;
import com.abaya.picacho.announcement.model.AnnouncementType;
import com.abaya.picacho.common.entity.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Announcement extends EntityBase {
    private String title;
    private String content;
    private AnnouncementType type;
    private AnnouncementState state = AnnouncementState.inEdit;

    @OneToMany(mappedBy = "announcement")
    private Set<AnnouncementAttachment> attachments;

    private LocalDateTime releaseDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                type == that.type &&
                state == that.state &&
                Objects.equals(releaseDateTime, that.releaseDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, content, type, state, releaseDateTime);
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", attachments=" + attachments +
                ", releaseDateTime=" + releaseDateTime +
                '}';
    }
}
