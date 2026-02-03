package com.jsy_codes.book_lecture_shop.service;


import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    public final BookRepository bookRepository;
    public List<Book> findByAuthorId(Long userId) {
        return bookRepository.findByAuthorId(userId);
    }
}
