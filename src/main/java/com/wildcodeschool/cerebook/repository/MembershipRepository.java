package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}