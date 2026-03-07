package com.jsy_codes.book_lecture_shop.domain.post;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NoticePost extends Post {

    /**
     * 최상단 고정 여부 (true면 공지 목록 상단에 우선 노출)
     */
    private boolean pinned;

    /**
     * 공지 노출 시작 시각
     */
    private LocalDateTime visibleFrom;

    /**
     * 공지 노출 종료 시각
     */
    private LocalDateTime visibleTo;

    /**
     * 중요도(숫자가 클수록 중요)
     */
    private int priority;


}
