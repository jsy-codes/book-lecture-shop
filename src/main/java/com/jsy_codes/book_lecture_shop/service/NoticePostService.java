package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.NoticePostDto;
import com.jsy_codes.book_lecture_shop.repository.NoticePostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jsy_codes.book_lecture_shop.domain.User;

import java.time.LocalDateTime;
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
    public Long createNoticePost(NoticePostDto noticePostDto) throws AccessDeniedException {
        User currentUser = getCurrentUser();
        if(!userService.hasAnyRole(currentUser, Role.ADMIN,Role.AUTHOR)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        NoticePost noticePost = new NoticePost();
        noticePost.setWriter(currentUser);
        noticePost.setTitle(noticePostDto.getTitle());
        noticePost.setContent(noticePostDto.getContent());
        noticePost.setVisibleFrom(noticePostDto.getVisibleFrom());
        noticePost.setVisibleTo(noticePostDto.getVisibleTo());
        noticePost.setPriority(noticePostDto.getPriority());
        noticePostRepository.save(noticePost);
        return noticePost.getId();
    }
    @Transactional
    public void updateNoticePost(NoticePostDto noticePostDto) throws AccessDeniedException {
        User currentUser = getCurrentUser();
        if (!userService.hasAnyRole(currentUser, Role.ADMIN, Role.AUTHOR)) {
            throw new AccessDeniedException("공지 수정 권한 없음");
        }

        Optional<NoticePost> optionalNotice = noticePostRepository.findById(noticePostDto.getId());
        if (optionalNotice.isEmpty()) {
            throw new IllegalArgumentException("공지글 없음");
        }

        NoticePost findNotice = optionalNotice.get();
        findNotice.setTitle(noticePostDto.getTitle());
        findNotice.setContent(noticePostDto.getContent());
        findNotice.setPinned(noticePostDto.isPinned());
        findNotice.setPriority(noticePostDto.getPriority());
        findNotice.setVisibleFrom(noticePostDto.getVisibleFrom());
        findNotice.setVisibleTo(noticePostDto.getVisibleTo());
    }

    public List<NoticePost> findAllNotDeleted() {
        return noticePostRepository.findAllNotDeleted();
    }
    public List<NoticePost> findActiveNoticePosts() {
        LocalDateTime now = LocalDateTime.now();
        return noticePostRepository.findActiveNoticePosts(now);
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

    public void deleteNotice(User user, Long id) {
        NoticePost post = noticePostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("삭제 권한 없음");
        }

        post.delete();
    }
}
