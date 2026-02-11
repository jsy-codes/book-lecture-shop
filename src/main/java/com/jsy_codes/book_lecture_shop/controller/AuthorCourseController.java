package com.jsy_codes.book_lecture_shop.controller;



import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.dto.CourseDto;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("author/courses")
public class AuthorCourseController {

    private final CourseService courseService;
    private final CourseEpisodeService episodeService;
    private final ItemCourseService itemCourseService;
    private final ItemService itemService;
    private final BookService bookService;

    @GetMapping
    public String myCourses(Model model, @AuthenticationPrincipal CustomUserDetails user) {


        model.addAttribute("courses", courseService.getByAuthorId(user.getUserId()));
        return "course/author-course-list";
    }

    @GetMapping("/new")
    public String createForm(Model model, @AuthenticationPrincipal CustomUserDetails user) {

        List<Book> books = bookService.findByAuthorId(user.getUserId());
        System.out.println("/new user_id: "+user.getUserId());
        for (Book book : books) {
            System.out.println("book = " + book.getAuthor().getName());
        }
        model.addAttribute("books", books);
        return "course/author-course-form";
    }

    @PostMapping("/new")
    public String createCourse(@AuthenticationPrincipal CustomUserDetails user,
                               @ModelAttribute CourseDto dto
                               ) {
        Course course = courseService.createCourse(
                user.getUser(),
                dto.getTitle(),
                dto.getSubtitle(),
                dto.getDescription(),
                dto.getCategory()
        );
        itemCourseService.assignBooksToCourse(course.getId(),dto.getBookIds());
        return "redirect:/author/courses/" + course.getId();
    }

    @GetMapping("/{courseId}")
    public String editCourse(@PathVariable Long courseId, Model model) {
        model.addAttribute("course", courseService.getCourse(courseId));
        model.addAttribute("episodes", episodeService.getEpisodes(courseId));
        return "course/author-course-edit";
    }

    @PostMapping("/{courseId}/episodes")
    public String addEpisode(@PathVariable Long courseId,
                             @RequestParam String title,
                             @RequestParam int order,
                             @RequestParam String youtubeUrl) {

        episodeService.addEpisode(courseId, title, order, youtubeUrl);
        return "redirect:/author/courses/" + courseId;
    }

    @PostMapping("/{courseId}/publish")
    public String publishCourse(@PathVariable Long courseId) {
        courseService.publish(courseId);
        return "redirect:/author/courses/" + courseId;
    }
}
