package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.course.Course;
import com.jsy_codes.book_lecture_shop.domain.course.CourseEpisode;
import com.jsy_codes.book_lecture_shop.repository.CourseEpisodeRepository;
import com.jsy_codes.book_lecture_shop.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseEpisodeService {

    private final CourseRepository courseRepository;
    private final CourseEpisodeRepository episodeRepository;

    public CourseEpisode addEpisode(Long courseId, String title, int order, String youtubeUrl) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        CourseEpisode episode = CourseEpisode.create(course, title, order, youtubeUrl);
        return episodeRepository.save(episode);
    }

    public List<CourseEpisode> getEpisodes(Long courseId) {
        return episodeRepository.findByCourseIdOrderByEpisodeOrderAsc(courseId);
    }
    public CourseEpisode getEpisode(Long courseId) {
        return episodeRepository.findByCourseId(courseId);
    }

}
