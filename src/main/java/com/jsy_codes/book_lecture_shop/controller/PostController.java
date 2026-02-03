package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.domain.post.Category.CategoryType;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.BookPostService;
import com.jsy_codes.book_lecture_shop.service.CourseService;
import com.jsy_codes.book_lecture_shop.service.ItemService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
@AllArgsConstructor
public class PostController {

    private final BookPostService bookPostService;
    private final UserService userService;
    private final CourseService courseService;
    //book
    @GetMapping("/books")
    public String books(@RequestParam(value = "category",required = false)String category, Model model,
                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println("category = " + category);

        List<BookPost> bookPosts;
        if(category == null||category.isEmpty()) {
            bookPosts = bookPostService.findAll();
        }else{
            CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
            bookPosts = bookPostService.findByCategory(categoryType);
        }
        model.addAttribute("bookPosts", bookPosts);
        model.addAttribute("loginUser", userDetails.getUser());
        return "post/books/book-list";
    }

    @GetMapping("/books/write")
    public String showBooksWirteform(@AuthenticationPrincipal CustomUserDetails userDetails,Model model) throws AccessDeniedException {
        if(userDetails == null) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }
       User user = userDetails.getUser();
        if(!userService.hasAnyRole(user, Role.ADMIN,Role.AUTHOR)) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        return "post/books/book-write-form";

    }
    @PostMapping("/books/write")
    public String saveBookPost(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @ModelAttribute BookPostDto bookPostDto,
                               Model model) {
       User user = userDetails.getUser();
        try {

            Long postId = bookPostService.createBookPost(bookPostDto);



            return "redirect:/books/book-list/" + postId;
        } catch (AccessDeniedException e) {
            model.addAttribute("error", "접근 권한이 없습니다.");
            return "error/403";
        }
    }
    @GetMapping("books/book-list/{id}")
    public String getBookPost(@PathVariable Long id, Model model) {
        BookPost bookPost = bookPostService.getBookPostById(id);
        if (bookPost == null) {
            return "error/404"; // 해당 ID의 게시물이 없을 경우 처리
        }

        model.addAttribute("bookPost", bookPost);
        return "post/books/book-detail"; // 보여줄 뷰 이름
    }




}
