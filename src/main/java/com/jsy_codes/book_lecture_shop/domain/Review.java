package com.jsy_codes.book_lecture_shop.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private Long productId;

    private Integer rating;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public enum ProductType {
        BOOK, LECTURE
    }
}
