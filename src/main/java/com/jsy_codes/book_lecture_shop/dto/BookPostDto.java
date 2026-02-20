package com.jsy_codes.book_lecture_shop.dto;


import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.category.CategoryType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookPostDto {


    private int price;
    private int stockQuantity;

    private User author;
    private String isbn;

    private String title;
    private String content;
    private CategoryType category;
    private String bookImageUrl;
}