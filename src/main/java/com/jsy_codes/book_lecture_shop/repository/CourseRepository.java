package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.course.CourseStatus;
import com.jsy_codes.book_lecture_shop.domain.post.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByAuthorId(Long authorId);
    List<Course> findByStatus(CourseStatus status);

    List<Course> findByCategoryType(CategoryType categoryType);
}
