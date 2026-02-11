package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.course.ItemCourse;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.domain.item.Item;
import com.jsy_codes.book_lecture_shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCourseService {
    private final ItemCourseRepository itemCourseRepository;
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final ItemRepository itemRepository;

    public boolean hasAccess(Long userId, Long courseId) {
        List<Long> itemIds = itemCourseRepository.findItemIdsByCourseId(courseId);
        return orderRepository.existsByUserIdAndCourseId(userId, itemIds);
    }
    @Transactional
    public void assignBooksToCourse(Long courseId, List<Long> bookIds) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        int order = 1;
        for (Long bookId : bookIds) {
            Item item = itemRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Book not found"));
            ItemCourse itemCourse = ItemCourse.create(course, item, order++);
            itemCourseRepository.save(itemCourse);

        }
    }
}
