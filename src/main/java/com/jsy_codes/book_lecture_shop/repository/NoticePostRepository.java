package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoticePostRepository extends JpaRepository<NoticePost, Long> {


    List<NoticePost> findTop3ByOrderByCreatedAtDesc();
    //List<NoticePost> findAllByOrderByPinnedDescPriorityDescCreatedAtDesc();

    @Query("""
    SELECT np FROM NoticePost np
    WHERE np.deletedAt IS NULL
    ORDER BY np.pinned DESC, np.priority DESC, np.createdAt DESC
    """)
    Page<NoticePost> findAllNotDeleted(Pageable pageable);

    List<NoticePost> findByPinnedTrueOrderByPriorityDescCreatedAtDesc();

    @Query("""
        SELECT np FROM NoticePost np
        WHERE np.visibleFrom <= :now
                AND np.visibleTo >= :now
                AND np.deletedAt IS NULL
        ORDER BY np.pinned DESC, np.priority DESC, np.createdAt DESC
        """)
    Page<NoticePost> findActiveNoticePosts(@Param("now") LocalDateTime now, Pageable pageable);


}
