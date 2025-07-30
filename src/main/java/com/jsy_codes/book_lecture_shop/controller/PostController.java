package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.BookPostService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final BookPostService bookPostService;
    private final UserService userService;

    //book
    @GetMapping("/post/books")
    public String books(Model model,@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<BookPost> bookPosts = bookPostService.findAll();
        model.addAttribute("bookPosts", bookPosts);

        return "post/books";
    }
    @GetMapping("/post/books/wirte")
    public String showBooksWirteform(@AuthenticationPrincipal CustomUserDetails userDetails) throws AccessDeniedException {
        if(userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
        Role role = userDetails.getUser().getRole();
        if(!(role == Role.ADMIN || role == Role.AUTHOR)) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
        return "/post/book-write-form";

    }
    @PostMapping("/post/books/write")
    public String saveBookPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @ModelAttribute BookPostDto bookPostDto,
                               Model model) {
        User user = userDetails.getUser();

        if (!userService.hasAnyRole(user, Role.AUTHOR, Role.ADMIN)) {
            model.addAttribute("error", "글쓰기 권한이 없습니다.");
            return "error/403"; // 또는 경고 페이지로 이동
        }

        bookPostService.createBookPost(bookPostDto);
        return "redirect:/post/books";
    }


}
