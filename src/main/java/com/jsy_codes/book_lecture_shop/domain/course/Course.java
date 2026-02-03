package com.jsy_codes.book_lecture_shop.domain.course;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEpisode> episodes = new ArrayList<>();

    @OneToMany
    private List<ItemCourse> itemCourses = new ArrayList<>();

    public static Course create(User author, String title, String subtitle, String description) {
        return Course.builder()
                .author(author)
                .title(title)
                .subtitle(subtitle)
                .description(description)
                .status(CourseStatus.DRAFT)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void publish() {
        this.status = CourseStatus.PUBLISHED;
    }
    public void addBook(Book book) {
        ItemCourse itemCourse = new ItemCourse(book,this);
        this.itemCourses.add(itemCourse);

    }

}
