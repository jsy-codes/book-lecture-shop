package com.jsy_codes.book_lecture_shop.repository;


import com.jsy_codes.book_lecture_shop.domain.course.ItemCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCourseRepository extends JpaRepository<ItemCourse, Long> {

    @Query("select ic.item.id" +
            " from ItemCourse ic" +
            " where ic.course.id = :courseId")
    List<Long> findItemIdsByCourseId(Long courseId);
}
