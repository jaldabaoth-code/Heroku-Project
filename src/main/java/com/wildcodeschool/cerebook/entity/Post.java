package com.wildcodeschool.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Column(columnDefinition="TEXT")
    private String content;

    private String video;

    @JsonManagedReference
    @ManyToOne
    private CerebookUser author;

    private Boolean isTweetos;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Event event;

    private String picture;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public CerebookUser getAuthor() {
        return author;
    }

    public void setAuthor(CerebookUser author) {
        this.author = author;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event eventId) {
        this.event = eventId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Boolean getTweetos() {
        return isTweetos;
    }

    public void setTweetos(Boolean tweetos) {
        isTweetos = tweetos;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
