package com.wildcodeschool.cerebook.entity.ids;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import javax.persistence.JoinColumn;
import java.io.Serializable;
import java.util.Objects;

public class CerebookUserFriendsId implements Serializable {
    private CerebookUser originatedUser;
    @JoinColumn(name = "friends_id")
    private CerebookUser friend;

    public CerebookUserFriendsId() {}

    public CerebookUserFriendsId(CerebookUser originatedUser, CerebookUser friend) {
        this.originatedUser = originatedUser;
        this.friend = friend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CerebookUserFriendsId that = (CerebookUserFriendsId) o;
        return Objects.equals(originatedUser, that.originatedUser) && Objects.equals(friend, that.friend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originatedUser, friend);
    }
}
