package com.jsy_codes.book_lecture_shop.dto;

import com.jsy_codes.book_lecture_shop.domain.post.category.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CourseDto {
    private String title;
    private String subtitle;
    private String description;
    private List<Long> bookIds;
    private CategoryType category;
}