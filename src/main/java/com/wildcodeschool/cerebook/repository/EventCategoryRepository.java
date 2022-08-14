package com.wildcodeschool.cerebook.repository;

import com.wildcodeschool.cerebook.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Long> {
    @Query("SELECT e FROM EventCategory e")
    List<EventCategory> getAllEventCategories();
}
