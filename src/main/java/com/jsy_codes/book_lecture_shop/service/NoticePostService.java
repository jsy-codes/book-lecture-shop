package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.repository.NoticePostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jsy_codes.book_lecture_shop.domain.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticePostService {
    private final NoticePostRepository noticePostRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @Transactional
    public Long createNoticePost(NoticePost noticePost) throws AccessDeniedException {
        User currentUser = getCurrentUser();
        if(!userService.hasAnyRole(currentUser, Role.ADMIN,Role.AUTHOR)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        noticePost.setWriter(currentUser);
        NoticePost savedNoticePost = noticePostRepository.save(noticePost);
        return savedNoticePost.getId();
    }
    @Transactional
    public void updateNoticePost(NoticePost noticePost) throws AccessDeniedException {
        User currentUser = getCurrentUser();
        if (!userService.hasAnyRole(currentUser, Role.ADMIN, Role.AUTHOR)) {
            throw new AccessDeniedException("공지 수정 권한 없음");
        }

        Optional<NoticePost> optionalNotice = noticePostRepository.findById(noticePost.getId());
        if (optionalNotice.isEmpty()) {
            throw new IllegalArgumentException("공지글 없음");
        }

        NoticePost findNotice = optionalNotice.get();
        findNotice.setTitle(noticePost.getTitle());
        findNotice.setContent(noticePost.getContent());
        findNotice.setPinned(noticePost.isPinned());
        findNotice.setPriority(noticePost.getPriority());
        findNotice.setVisibleFrom(noticePost.getVisibleFrom());
        findNotice.setVisibleTo(noticePost.getVisibleTo());
    }

    public List<NoticePost> findAll() {
        return noticePostRepository.findAllByOrderByPinnedDescPriorityDescCreatedAtDesc();
    }

    public NoticePost findById(Long id) {
        return noticePostRepository.findById(id).orElse(null);
    }

    public List<NoticePost> findPinnedNotices() {
        return noticePostRepository.findByPinnedTrueOrderByPriorityDescCreatedAtDesc();
    }


    private User getCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            throw new IllegalArgumentException("No current user 1");
        }
        User currentUser = userRepository.findById(userId);
        if (currentUser == null) {
            throw new IllegalArgumentException("No current user 2");
        }
        return currentUser;
    }
}
