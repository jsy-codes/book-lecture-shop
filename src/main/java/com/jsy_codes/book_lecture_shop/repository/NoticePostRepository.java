package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {
    List<NoticePost> findAllByOrderByPinnedDescPriorityDescCreatedAtDesc();

    List<NoticePost> findByPinnedTrueOrderByPriorityDescCreatedAtDesc();
}
