package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.domain.post.status.QnaStatus;
import com.jsy_codes.book_lecture_shop.dto.QnaPostDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.QnaPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaPostController {

    private final QnaPostService qnaPostService;

    @GetMapping
    public String qnaList(@RequestParam(required = false) QnaStatus status, Model model) {
        List<QnaPost> qnaPosts = (status == null)
                ? qnaPostService.findAll()
                : qnaPostService.findByStatus(status);

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
    public String qnaDetail(@PathVariable Long id, Model model) {
        QnaPost qnaPost = qnaPostService.findById(id);
        if (qnaPost == null) {
            return "error/404";
        }

        model.addAttribute("qnaPost", qnaPost);
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
        try {
            validateLoggedIn(userDetails);
            Long qnaId = qnaPostService.createQnaPost(qnaPostDto);
            return "redirect:/qna/" + qnaId;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "error/403";
        }
    }

    @PostMapping("/{id}/answer")
    public String answerQna(@PathVariable Long id,
                            @AuthenticationPrincipal CustomUserDetails userDetails,
                            @RequestParam String answerContent,
                            Model model) {
        try {
            validateLoggedIn(userDetails);
            qnaPostService.answerQna(id, answerContent);
            return "redirect:/qna/" + id;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "답변 권한이 없습니다.");
            return "error/403";
        }
    }

    @PostMapping("/{id}/close")
    public String closeQna(@PathVariable Long id,
                           @AuthenticationPrincipal CustomUserDetails userDetails,
                           Model model) {
        try {
            validateLoggedIn(userDetails);
            qnaPostService.closeQna(id);
            return "redirect:/qna/" + id;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "종료 권한이 없습니다.");
            return "error/403";
        }
    }

    private void validateLoggedIn(CustomUserDetails userDetails) throws AccessDeniedException {
        if (userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
    }
}
