package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.repository.PostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public BookPost createBookPost(BookPostDto dto, Long writerId){
        userRepository.findById()
    }
}
