package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.item.Item;
import com.jsy_codes.book_lecture_shop.domain.post.Category.CategoryType;
import com.jsy_codes.book_lecture_shop.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    public Course createCourse(User author, String title, String subtitle, String description, CategoryType category) {
        Course course = Course.create(author, title, subtitle, description,category);
        return courseRepository.save(course);
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public void publish(Long id) {
        Course course = getCourse(id);
        course.publish();
    }

    public List<Course> getByAuthorId(Long id) {
        return courseRepository.findByAuthorId(id);
    }

    public List<Course> findCourses() {
        return courseRepository.findAll();
    }


}
