package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.NoticePostService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticePostController {

    private final NoticePostService noticePostService;
    private final UserService userService;

    @GetMapping
    public String noticeList(Model model) {
        List<NoticePost> notices = noticePostService.findAll();
        model.addAttribute("notices", notices);
        return "post/notices/notice-list";
    }

    @GetMapping("/{id}")
    public String noticeDetail(@PathVariable Long id, Model model) {
        NoticePost noticePost = noticePostService.findById(id);
        if (noticePost == null) {
            return "error/404";
        }

        model.addAttribute("notice", noticePost);
        return "post/notices/notice-detail";
    }

    @GetMapping("/write")
    public String noticeWriteForm(@AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateManager(userDetails);
        return "post/notices/notice-write-form";
    }

    @PostMapping("/write")
    public String writeNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute NoticePost noticePost,
                              Model model) {// will made it noticePostDto
        try {
            validateManager(userDetails);
            Long noticeId = noticePostService.createNoticePost(noticePost);
            return "redirect:/notices/" + noticeId;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "접근 권한이 없습니다.");
            return "error/403";
        }
    }

    @PostMapping("/{id}/edit")
    public String editNotice(@PathVariable Long id,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             @ModelAttribute NoticePost noticePost,
                             Model model) {
        try {
            validateManager(userDetails);
            noticePost.setId(id);
            noticePostService.updateNoticePost(noticePost);
            return "redirect:/notices/" + id;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "접근 권한이 없습니다.");
            return "error/403";
        }
    }

    private void validateManager(CustomUserDetails userDetails) throws AccessDeniedException {
        if (userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        User user = userDetails.getUser();
        if (!userService.hasAnyRole(user, Role.ADMIN, Role.AUTHOR)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}
