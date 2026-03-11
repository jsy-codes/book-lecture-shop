package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.QnaPostDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.QnaPostService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaPostController {

    private final QnaPostService qnaPostService;
    private final UserService userService;

    @GetMapping
    public String qnaList(
            @RequestParam(required = false) QnaStatus status,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);

        Page<QnaPost> qnaPosts = (status == null)
                ? qnaPostService.findActiveQnaPosts(pageable)
                : qnaPostService.findByStatus(status, pageable);

        model.addAttribute("qnaPosts", qnaPosts);
        model.addAttribute("selectedStatus", status);

        return "post/qna/qna-list";
    }

    @GetMapping("/my")
    public String myQnaList(Model model) {
        List<QnaPost> qnaPosts = qnaPostService.findMyQnaPosts();
        model.addAttribute("qnaPosts", qnaPosts);
        return "post/qna/qna-my-list";
    }

    @GetMapping("/{id}")
    public String qnaDetail(@PathVariable Long id,
                            @AuthenticationPrincipal CustomUserDetails  userDetails,
                            Model model) {
        QnaPost qnaPost = qnaPostService.findById(id);

        User user = userDetails != null ? userDetails.getUser() : null;


        boolean isCanView = qnaPostService.canView(qnaPost, user);
        boolean isAdmin = user != null && userService.hasAnyRole(user, Role.ADMIN, Role.AUTHOR);

        model.addAttribute("isAdmin",isAdmin);
        model.addAttribute("qnaPost", qnaPost);
        model.addAttribute("isCanView", isCanView);
        return "post/qna/qna-detail";
    }

    @GetMapping("/write")
    public String qnaWriteForm(@AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        validateLoggedIn(userDetails);
        return "post/qna/qna-write-form";
    }

    @PostMapping("/write")
    public String writeQna(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @ModelAttribute QnaPostDto qnaPostDto,
                           Model model) {

        validateLoggedIn(userDetails);
        Long qnaId = qnaPostService.createQnaPost(qnaPostDto);
        return "redirect:/qna/" + qnaId;


    }

    @PostMapping("/{id}/answer")
    public String answerQna(@PathVariable Long id,
                            @AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestParam String answerContent,
                            Model model) {

            validateManager(userDetails);
            qnaPostService.answerQna(id, answerContent);
            return "redirect:/qna/" + id;

    }

    @PostMapping("/{id}/close")
    public String closeQna(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {

            validateManager(userDetails);
            qnaPostService.closeQna(id);
            return "redirect:/qna/" + id;


    }
    @PostMapping("/{id}/delete")
    public String deleteQna(@PathVariable Long id,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        validateManager(userDetails);
        qnaPostService.deleteNotice(userDetails.getUser(),id);

        return "redirect:/qna";
    }

    private void validateLoggedIn(CustomUserDetails userDetails)  {
        if (userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
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
