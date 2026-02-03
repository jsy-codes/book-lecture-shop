package com.jsy_codes.book_lecture_shop.repository;

import com.jsy_codes.book_lecture_shop.domain.item.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    public List<Book> findByAuthorId(Long userId);

}
