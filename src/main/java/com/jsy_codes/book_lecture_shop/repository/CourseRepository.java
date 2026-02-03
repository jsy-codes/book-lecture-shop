package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.course.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAuthorId(Long authorId);
    List<Course> findByStatus(CourseStatus status);
}
