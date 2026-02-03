package com.jsy_codes.book_lecture_shop.domain.course;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CourseEpisode {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

    private String title;

    private int episodeOrder;

    private String youtubeUrl;

    // 필요하면 재생시간(초) 입력
    private Integer durationSeconds;

    public static CourseEpisode create(Course course, String title, int order, String youtubeUrl) {
        return CourseEpisode.builder()
                .course(course)
                .title(title)
                .episodeOrder(order)
                .youtubeUrl(youtubeUrl)
                .build();
    }
}
