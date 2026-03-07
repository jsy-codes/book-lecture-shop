package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.QnaPostDto;
import com.jsy_codes.book_lecture_shop.repository.QnaPostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaPostService {

    private final QnaPostRepository qnaPostRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public Long createQnaPost(QnaPostDto qnaPostDto) {
        User currentUser = getCurrentUser();
        QnaPost qnaPost = new QnaPost();
        qnaPost.setTitle(qnaPostDto.getTitle());
        qnaPost.setContent(qnaPostDto.getContent());
        qnaPost.setStatus(qnaPostDto.getStatus());
        qnaPost.setSecret(qnaPostDto.isSecret());
        qnaPost.setCreatedAt(LocalDateTime.now());
        qnaPost.setWriter(currentUser);
        qnaPostRepository.save(qnaPost);
        return qnaPost.getId();
    }

    @Transactional
    public void answerQna(Long qnaId, String answerContent)  {
        User currentUser = getCurrentUser();
        if (!userService.hasAnyRole(currentUser, Role.ADMIN, Role.AUTHOR)) {
            throw new AccessDeniedException("답변 권한 없음");
        }

        Optional<QnaPost> optionalQnaPost = qnaPostRepository.findById(qnaId);
        if (optionalQnaPost.isEmpty()) {
            throw new IllegalArgumentException("질문글 없음");
        }

        QnaPost qnaPost = optionalQnaPost.get();
        qnaPost.setAnswerContent(answerContent);
        qnaPost.setAnswerer(currentUser);
        qnaPost.setAnsweredAt(LocalDateTime.now());
        qnaPost.setStatus(QnaStatus.ANSWERED);
    }
    public List<QnaPost> findActiveQnaPosts() {
        return qnaPostRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    @Transactional
    public void closeQna(Long qnaId) {

        User currentUser = getCurrentUser();

        QnaPost qnaPost = qnaPostRepository.findById(qnaId)
                .orElseThrow(() -> new IllegalArgumentException("질문글 없음"));

        boolean isWriter = qnaPost.getWriter() != null
                && qnaPost.getWriter().getId().equals(currentUser.getId());

        boolean isManager = userService.hasAnyRole(currentUser, Role.ADMIN, Role.AUTHOR);

        if (!isWriter && !isManager) {
            throw new AccessDeniedException("질문 종료 권한 없음");
        }

        qnaPost.setStatus(QnaStatus.CLOSED);
    }

    public QnaPost findById(Long id) {
        return qnaPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("QnaPost not found"));

    }

    public List<QnaPost> findAll() {
        return qnaPostRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<QnaPost> findMyQnaPosts() {
        User currentUser = getCurrentUser();
        return qnaPostRepository.findByWriterOrderByCreatedAtDesc(currentUser);
    }

    public List<QnaPost> findByStatus(QnaStatus status) {
        return qnaPostRepository.findByDeletedAtIsNullAndStatusOrderByCreatedAtDesc(status);
    }

    private User getCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new IllegalArgumentException("로그인 사용자 없음");
        }

        User currentUser = userRepository.findById(userId);
        if (currentUser == null) {
            throw new IllegalArgumentException("사용자 없음");
        }
        return currentUser;
    }
    @Transactional
    public void deleteNotice(User user, Long id) {
        QnaPost post = qnaPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("삭제 권한 없음");
        }


        post.delete();
    }

    public boolean canView(QnaPost qnaPost, User user) {

        // 비밀글 아니면 누구나 가능
        if (!qnaPost.isSecret()) {
            return true;
        }

        if (user == null) {
            return false;
        }

        // 관리자
        if (userService.hasAnyRole(user,Role.ADMIN, Role.AUTHOR)) {
            return true;
        }

        // 작성자
        return qnaPost.getWriter().getId().equals(user.getId());
    }
}
