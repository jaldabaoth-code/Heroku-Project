package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.CerebookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CerebookUserRepository extends JpaRepository<CerebookUser, Long> {
    CerebookUser findCerebookUserById(Long user);

    @Query("SELECT c1, count(c1) as cc1 " +
            "FROM CerebookUser c1 " +
            "JOIN CerebookUser c2 ON c1 IN (SELECT c2f FROM c2.friends c2f) " +
            "JOIN CerebookUser c3 ON c2 IN (SELECT c3f FROM c3.friends c3f) " +
            "WHERE c3 = :user " +
            "AND c1 <> :user " +
            "AND c1 NOT IN (SELECT c3f FROM c3.friends c3f) " +
            "GROUP BY c1 " +
            "ORDER BY cc1 DESC "
    )

    List<CerebookUser> findFriendsSuggestions(
            @Param("user") CerebookUser cerebookUser
    );
}
