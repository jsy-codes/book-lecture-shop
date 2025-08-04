package com.jsy_codes.book_lecture_shop.domain.post;


import com.jsy_codes.book_lecture_shop.domain.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookPost extends Post {
    private String bookImageUrl;
    private int price;
    private int stock;
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book; // 어떤 책을 파는지 연결

}
