package com.jsy_codes.book_lecture_shop.dto;

import com.jsy_codes.book_lecture_shop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticePostDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    protected String title;
    protected String content;

    protected User writer;

    private boolean pinned;


    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime visibleFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime visibleTo;

    private int priority;
}
