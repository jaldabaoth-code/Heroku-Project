package com.wildcodeschool.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Objects;
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "friends"})
public class CerebookUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profilImage;
    private String background;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String superPowers;
    private String genre;
    @Column(columnDefinition = "TEXT")
    private String bio;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @JsonBackReference
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JsonManagedReference
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Membership membership;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cerebook_user_friends")
    private final Set<CerebookUser> friends = new TreeSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL)
    private List<Event> events;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public CerebookUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(String profilImage) {
        this.profilImage = profilImage;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getSuperPowers() {
        return superPowers;
    }

    public void setSuperPowers(String superPowers) {
        this.superPowers = superPowers;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<CerebookUser> getFriends() {
        return friends;
    }

    public void addFriend(CerebookUser friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return user.getUsername();
    }

    /*Interpretating all objects with the same Cerebook ID as the same object */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CerebookUser that = (CerebookUser) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getDefaultBackgroundPath() {

        if (Objects.equals(background, "")) {
            return "/images/graybg.jpg";
        } else {
            return "/images/Profiles/" + id + "/background/" + background;
        }
    }

    public String getDefaultProfilImagePath() {
        if (Objects.equals(profilImage, "")) {
            return "/images/default-avatar.png";
        } else {
            return "/images/Profiles/" + id + "/profile/" + profilImage;
        }
    }
}
