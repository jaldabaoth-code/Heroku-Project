package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventByName(String name);

    @Query("SELECT e FROM Event e ORDER BY e.date ASC")
    List<Event> findAllEvents();

    Event getEventById(Long id);

    @Query("SELECT e FROM Event e WHERE e.creator = :creator AND e.id = :eventId")
    Event getEventByIdAndByCreator(@Param("creator") User creator, @Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e WHERE e.creator = :creator")
    List<Event> getAllEventsByCreator(@Param("creator") User creator);
}
