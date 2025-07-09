package com.jsy_codes.book_lecture_shop.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lectures")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String instructor;

    private Integer price;

    @Lob
    private String description;

    private String videoUrl;

    private String thumbnailUrl;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
