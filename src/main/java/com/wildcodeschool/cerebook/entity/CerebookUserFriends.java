package com.wildcodeschool.cerebook.entity;

import com.wildcodeschool.cerebook.entity.ids.CerebookUserFriendsId;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cerebook_user_friends")
@IdClass(CerebookUserFriendsId.class)
public class CerebookUserFriends implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "cerebook_user_id")
    private CerebookUser originatedUser;

    @Id
    @ManyToOne
    @JoinColumn(name = "friends_id")
    private CerebookUser friend;

    /* Property date served to list recent friend requests */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAccepted;

    public CerebookUserFriends(CerebookUser currentCerebookUser, CerebookUser friend, boolean accepted) {
        this.originatedUser = currentCerebookUser;
        this.friend = friend;
        this.isAccepted = accepted;
    }

    public CerebookUserFriends() {}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    public CerebookUser getOriginatedUser() {
        return originatedUser;
    }

    public void setOriginatedUser(CerebookUser originatedUser) {
        this.originatedUser = originatedUser;
    }

    public CerebookUser getFriend() {
        return friend;
    }

    public void setFriend(CerebookUser friend) {
        this.friend = friend;
    }
}
