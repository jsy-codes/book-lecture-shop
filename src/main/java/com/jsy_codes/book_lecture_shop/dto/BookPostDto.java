package com.jsy_codes.book_lecture_shop.dto;


import com.jsy_codes.book_lecture_shop.domain.post.Category.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookPostDto {
    private String title;
    private String content;
    private CategoryType category;
    private String bookImageUrl;
    private int price;
    private int stock;
}