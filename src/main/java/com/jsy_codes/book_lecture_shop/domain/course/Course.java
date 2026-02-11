package com.jsy_codes.book_lecture_shop.domain.course;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.domain.post.Category.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Course {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    private String title;
    private String subtitle;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;   // DRAFT, PUBLISHED, ARCHIVED

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEpisode> episodes = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemCourse> itemCourses = new ArrayList<>();

    public static Course create(User author,
                                String title,
                                String subtitle,
                                String description,
                                CategoryType category) {

        Course course = new Course();
        course.author = author;
        course.title = title;
        course.subtitle = subtitle;
        course.description = description;
        course.status = CourseStatus.DRAFT;
        course.categoryType = category;
        course.createdAt = LocalDateTime.now();

        return course;
    }

    public void publish() {
        this.status = CourseStatus.PUBLISHED;
    }


}
