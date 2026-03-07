package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.NoticePostDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.NoticePostService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticePostController {

    private final NoticePostService noticePostService;
    private final UserService userService;

    @GetMapping
    public String noticeList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model) {
        List<NoticePost> notices;

        if(customUserDetails != null && userService.hasAnyRole(customUserDetails.getUser(), Role.ADMIN)) {
            notices = noticePostService.findAllNotDeleted();
        }else{
            notices = noticePostService.findActiveNoticePosts();
        }

        model.addAttribute("notices", notices);
        return "post/notices/notice-list.html";
    }


    @GetMapping("/write")
    public String noticeWriteForm(@AuthenticationPrincipal CustomUserDetails userDetails) {
        validateManager(userDetails);
        return "post/notices/notice-write-form";
    }

    @PostMapping("/write")
    public String writeNotice(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute NoticePostDto noticePostDto,
                              Model model) {

        validateManager(userDetails);
        Long noticeId = noticePostService.createNoticePost(noticePostDto);
        return "redirect:/notices/" + noticeId;

    }
    @GetMapping("/{id}")
    public String noticeDetail(@PathVariable Long id,
                               @AuthenticationPrincipal CustomUserDetails userDetails,
                               Model model) {//will 모든 컨트롤러에서 매번 addAttribute 하지 않기
        NoticePost noticePost = noticePostService.findById(id);
        boolean isAdmin = false;
        if (userService.hasAnyRole(userDetails.getUser(), Role.ADMIN))
            isAdmin = true;
        if (noticePost == null) {
            return "error/404";
        }
        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("notice", noticePost);
        return "post/notices/notice-detail.html";
    }
    @GetMapping("/{id}/edit")
    public String noticeEditForm(@PathVariable Long id,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 Model model) throws AccessDeniedException {
        validateManager(userDetails);
        NoticePost noticePost = noticePostService.findById(id);
        if (noticePost == null) {
            return "error/404";
        }

        model.addAttribute("notice", noticePost);
        return "post/notices/notice-edit-form.html";
    }

    @PostMapping("/{id}/edit")
    public String editNotice(@PathVariable Long id,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             @ModelAttribute NoticePostDto noticePostDto,
                             Model model) {

        validateManager(userDetails);
        noticePostDto.setId(id);
        noticePostService.updateNoticePost(noticePostDto);
        return "redirect:/notices/" + id;

    }
    @PostMapping("/{id}/delete")
    public String deleteNotice(@PathVariable Long id,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateManager(userDetails);
        noticePostService.deleteNotice(userDetails.getUser(),id);

        return "redirect:/notices";
    }

    private void validateManager(CustomUserDetails userDetails)  {
        if (userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        User user = userDetails.getUser();
        if (!userService.hasAnyRole(user, Role.ADMIN)) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}
