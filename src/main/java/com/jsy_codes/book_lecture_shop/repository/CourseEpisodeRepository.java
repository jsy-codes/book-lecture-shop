package com.jsy_codes.book_lecture_shop.repository;


import com.jsy_codes.book_lecture_shop.domain.course.CourseEpisode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseEpisodeRepository extends JpaRepository<CourseEpisode, Long> {
    List<CourseEpisode> findByCourseIdOrderByEpisodeOrderAsc(Long courseId);
    Optional<CourseEpisode> findByIdAndCourseId(Long episodeId, Long courseId);

}
