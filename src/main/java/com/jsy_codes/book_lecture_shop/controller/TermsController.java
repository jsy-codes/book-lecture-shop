package com.jsy_codes.book_lecture_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terms")
public class TermsController {
    @GetMapping
    public String termsPage() {
        return "terms/terms-of-service"; // terms.html
    }
}
