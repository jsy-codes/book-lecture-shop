package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    /**
     * 로그인 페이지 반환
     */
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login"; // login.html 템플릿 반환
    }

    /**
     * 현재 로그인한 사용자 정보 가져오기
     */
    public User getLoggedInMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName() != null) {
            return userService.findByEmail(authentication.getName()); // ID 기반으로 DB에서 조회
        }
        return null;
    }
}
