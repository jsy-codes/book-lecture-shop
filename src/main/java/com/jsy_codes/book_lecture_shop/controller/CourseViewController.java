package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.course.CourseEpisode;
import com.jsy_codes.book_lecture_shop.security.CustomUserDetails;
import com.jsy_codes.book_lecture_shop.service.CourseEpisodeService;
import com.jsy_codes.book_lecture_shop.service.CourseService;
import com.jsy_codes.book_lecture_shop.service.ItemCourseService;
import com.jsy_codes.book_lecture_shop.util.YoutubeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseViewController {

    private final CourseService courseService;
    private final CourseEpisodeService episodeService;
    private final ItemCourseService itemCourseService;

    @GetMapping("/{courseId}")
    public String courseDetail(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourse(courseId);
        model.addAttribute("course", course);
        model.addAttribute("episodes", episodeService.getEpisodes(courseId));
        return "course/course-detail";
    }

    @GetMapping("/{courseId}/play/{episodeId}")
    public String playEpisode(@AuthenticationPrincipal CustomUserDetails user,
                              @PathVariable Long courseId,
                              @PathVariable Long episodeId,
                              Model model) {

        if (!itemCourseService.hasAccess(user.getUserId(), courseId)) {
            throw new AccessDeniedException("No access to this course");
        }

        CourseEpisode episode = episodeService.getEpisode(episodeId);
        model.addAttribute("episode", episode);

        // 재생용 YouTube URL
        model.addAttribute("youtubeId", YoutubeUtil.extractId(episode.getYoutubeUrl()));

        return "course/course-player";
    }
}
