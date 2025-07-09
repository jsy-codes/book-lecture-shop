package com.jsy_codes.book_lecture_shop.domain;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private Integer price;

    private Integer stock;

    @Lob
    private String description;

    private String imageUrl;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
