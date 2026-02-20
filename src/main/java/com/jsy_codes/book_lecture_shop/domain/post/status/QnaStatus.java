package com.jsy_codes.book_lecture_shop.domain.post.status;


public enum QnaStatus {
    OPEN,       // 질문 등록됨(답변 대기)
    ANSWERED,   // 답변 완료
    CLOSED      // 작성자/관리자가 종료 처리
}
