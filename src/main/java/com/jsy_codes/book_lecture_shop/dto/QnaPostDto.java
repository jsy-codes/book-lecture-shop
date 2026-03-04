package com.jsy_codes.book_lecture_shop.dto;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnaPostDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected String title;
    protected String content;
    protected User writer;
    private boolean secret;
    private QnaStatus status = QnaStatus.OPEN;


}
