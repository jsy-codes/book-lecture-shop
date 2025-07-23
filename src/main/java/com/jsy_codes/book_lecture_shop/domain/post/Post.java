package com.jsy_codes.book_lecture_shop.domain.post;

import com.jsy_codes.book_lecture_shop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Post {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    @Entity
    @Inheritance(strategy = InheritanceType.JOINED)  // 또는 SINGLE_TABLE, TABLE_PER_CLASS
    public abstract class Post {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        protected String title;

        protected String content;

        @ManyToOne
        protected User writer;

        protected LocalDateTime createdAt;
    }





}
