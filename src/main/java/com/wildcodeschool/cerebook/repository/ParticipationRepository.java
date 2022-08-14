package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import com.wildcodeschool.cerebook.entity.Event;
import com.wildcodeschool.cerebook.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("SELECT p FROM Participation p WHERE p.event = :event")
    List<Participation> getAllByEvent(@Param("event") Event event);

    @Query("SELECT p FROM Participation p WHERE p.event = :event AND p.participant =:participant")
    Participation getParticipationByEventAndByCerebookUser(@Param("event") Event event, @Param("participant") CerebookUser participant);
}
