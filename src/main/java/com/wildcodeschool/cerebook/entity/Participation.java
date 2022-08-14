package com.wildcodeschool.cerebook.entity;

import javax.persistence.*;

@Entity
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

    @ManyToOne
    private CerebookUser participant;

    @ManyToOne
    private Event event;

    public Participation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CerebookUser getParticipant() {
        return participant;
    }

    public void setParticipant(CerebookUser participant) {
        this.participant = participant;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
