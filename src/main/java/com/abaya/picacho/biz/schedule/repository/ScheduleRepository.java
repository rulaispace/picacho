package com.abaya.picacho.biz.schedule.repository;

import com.abaya.picacho.biz.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();

    @Query("select s from Schedule s inner join s.participants p where p.username = :username " +
            "and (:finishTime > s.startTime and :startTime < s.finishTime) ")
    List<Schedule> findAllByUsernameAndStartTimeAndEndTime(@Param("username") String username,
                                                           @Param("startTime") LocalDateTime startTime,
                                                           @Param("finishTime") LocalDateTime finishTime);


    @Query("select distinct s from Schedule s inner join s.participants p inner join s.resources inner join s.attachments where p.username = :username order by s.startTime desc")
    List<Schedule> findAllByUsernameOrderByStartTimeDesc(@Param("username") String username);

    @Query("select distinct s from Schedule s inner join s.participants inner join s.attachments inner join s.resources r where r.resourceId = :resourceId " +
            "and (:finishTime > s.startTime and :startTime < s.finishTime) ")
    List<Schedule> findAllByResourceIdAndStartTimeAndEndTime(@Param("resourceId") Long resourceId,
                                                             @Param("startTime") LocalDateTime startTime,
                                                             @Param("finishTime") LocalDateTime finishTime);
}
