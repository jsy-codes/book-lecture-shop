package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemCourseService {
    private final ItemCourseRepository itemCourseRepository;
    private final OrderRepository orderRepository;
    private final CourseRepository courseRepository;
    private final BookRepository bookRepository;

    public boolean hasAccess(Long userId, Long courseId) {
        List<Long> itemIds = itemCourseRepository.findItemIdsByCourseId(courseId);
        return orderRepository.existsByUserIdAndCourseId(userId, itemIds);
    }

    public void assignBooksToCourse(Long courseId, List<Long> bookIds) {
        Optional<Course> course = courseRepository.findById(courseId);
        for (Long bookId : bookIds) {
            Optional<Book> book = bookRepository.findById(bookId);
            course.addBook(book); // 도메인 행위
        }
    }
}
