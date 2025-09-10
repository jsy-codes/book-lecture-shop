package com.jsy_codes.book_lecture_shop.domain.item;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends Item{

    private String author;
    private String isbn;
}
