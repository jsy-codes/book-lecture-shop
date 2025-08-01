package com.jsy_codes.book_lecture_shop.domain.post;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookPost extends Post {
    private String bookImage;
    private int price;
    private int stock;
}
