package com.jsy_codes.book_lecture_shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookPostDto {
    private String title;
    private String content;
    private int price;
    private String bookImageUrl;
    private int stock;
}