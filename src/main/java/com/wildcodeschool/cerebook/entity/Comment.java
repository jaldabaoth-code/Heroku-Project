package com.wildcodeschool.cerebook.entity;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT")
    private String content;

    @ManyToOne
    private CerebookUser author;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Post post;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CerebookUser getAuthor() {
        return author;
    }

    public void setAuthor(CerebookUser author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
