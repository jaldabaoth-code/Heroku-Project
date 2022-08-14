package com.wildcodeschool.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.List;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String photo;

    @JsonBackReference
    @OneToMany(mappedBy = "membership", cascade = CascadeType.REFRESH)
    private List<CerebookUser> members;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "membership_event",
            joinColumns = @JoinColumn(name = "membership_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    public Membership() {
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CerebookUser> getMembers() {
        return members;
    }

    public void setMembers(List<CerebookUser> members) {
        this.members = members;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
