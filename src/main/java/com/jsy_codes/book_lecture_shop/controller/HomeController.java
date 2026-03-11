package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.post.NoticePost;
import com.jsy_codes.book_lecture_shop.domain.post.QnaPost;
import com.jsy_codes.book_lecture_shop.service.NoticePostService;
import com.jsy_codes.book_lecture_shop.service.QnaPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final NoticePostService noticePostService;
    private final QnaPostService qnaPostService;
    @GetMapping
    public String home(Model model) {
        Page<NoticePost> notices = noticePostService.findActiveNoticePosts(PageRequest.of(0,3));
        Page<QnaPost> qnas = qnaPostService.findActiveQnaPosts(PageRequest.of(0,3));

        model.addAttribute("notices", notices);
        model.addAttribute("qnas", qnas);

        return "home";
    }

}
