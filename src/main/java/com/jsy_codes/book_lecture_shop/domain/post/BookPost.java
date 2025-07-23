package com.jsy_codes.book_lecture_shop.domain.post;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class BookPost extends Post {
    private String bookImage;
    private int price;
    private int stock;
}
