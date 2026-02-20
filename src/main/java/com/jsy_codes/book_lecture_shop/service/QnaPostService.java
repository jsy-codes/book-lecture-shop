package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.repository.QnaPostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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
    public Long createQnaPost(QnaPost qnaPost) {
        User currentUser = getCurrentUser();
        qnaPost.setWriter(currentUser);
        if (qnaPost.getStatus() == null) {
            qnaPost.setStatus(QnaStatus.OPEN);
        }
        QnaPost savedQna = qnaPostRepository.save(qnaPost);
        return savedQna.getId();
    }

    @Transactional
    public void answerQna(Long qnaId, String answerContent) throws AccessDeniedException {
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

    @Transactional
    public void closeQna(Long qnaId) throws AccessDeniedException {
        User currentUser = getCurrentUser();
        Optional<QnaPost> optionalQnaPost = qnaPostRepository.findById(qnaId);
        if (optionalQnaPost.isEmpty()) {
            throw new IllegalArgumentException("질문글 없음");
        }

        QnaPost qnaPost = optionalQnaPost.get();

        boolean isWriter = qnaPost.getWriter() != null && qnaPost.getWriter().getId().equals(currentUser.getId());
        boolean isManager = userService.hasAnyRole(currentUser, Role.ADMIN, Role.AUTHOR);

        if (!isWriter && !isManager) {
            throw new AccessDeniedException("질문 종료 권한 없음");
        }

        qnaPost.setStatus(QnaStatus.CLOSED);
    }

    public QnaPost findById(Long id) {
        return qnaPostRepository.findById(id).orElse(null);
    }

    public List<QnaPost> findAll() {
        return qnaPostRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<QnaPost> findMyQnaPosts() {
        User currentUser = getCurrentUser();
        return qnaPostRepository.findByWriterOrderByCreatedAtDesc(currentUser);
    }

    public List<QnaPost> findByStatus(QnaStatus status) {
        return qnaPostRepository.findByStatusOrderByCreatedAtDesc(status);
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
}
