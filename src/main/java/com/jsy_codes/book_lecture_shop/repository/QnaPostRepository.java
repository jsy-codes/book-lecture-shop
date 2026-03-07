package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QnaPostRepository extends JpaRepository<QnaPost, Long> {
    List<QnaPost> findAllByOrderByCreatedAtDesc();
    List<QnaPost> findByWriterOrderByCreatedAtDesc(User user);
    List<QnaPost> findByDeletedAtIsNullAndStatusOrderByCreatedAtDesc(QnaStatus status);

    List<QnaPost> findByDeletedAtIsNullOrderByCreatedAtDesc();
}
