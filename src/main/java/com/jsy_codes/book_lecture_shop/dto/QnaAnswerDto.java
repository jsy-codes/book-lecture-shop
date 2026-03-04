package com.jsy_codes.book_lecture_shop.dto;

import com.jsy_codes.book_lecture_shop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaAnswerDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String title;
    protected String content;
    protected User writer;
    private String answerContent;
}
