package com.jsy_codes.book_lecture_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {
    //book
    @GetMapping("/post/books")
    public String books(Model model) {

    }
}
