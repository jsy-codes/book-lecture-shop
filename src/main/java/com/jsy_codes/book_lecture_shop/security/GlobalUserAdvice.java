package com.jsy_codes.book_lecture_shop.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalUserAdvice {
    @ModelAttribute("loginUser")
    public CustomUserDetails addLoginUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails;
    }
}
