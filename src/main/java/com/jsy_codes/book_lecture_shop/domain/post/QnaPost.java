package com.jsy_codes.book_lecture_shop.domain.post;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class QnaPost extends Post {

    /**
     * 비밀글 여부 (true면 작성자/관리자만 조회 가능)
     */
    private boolean secret;

    /**
     * 질문 처리 상태
     */
    @Enumerated(EnumType.STRING)
    private QnaStatus status = QnaStatus.OPEN;

    /**
     * 답변 본문
     */
    @Lob
    private String answerContent;

    /**
     * 답변자 (관리자/강사 등)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answerer_id")
    private User answerer;

    /**
     * 답변 완료 시각
     */
    private LocalDateTime answeredAt;
}
