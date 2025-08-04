package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.repository.BookPostRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.security.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPostService {

    private final BookPostRepository bookPostRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    @Transactional
    public Long createBookPost(BookPostDto dto) throws AccessDeniedException {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("사용자 없음");
        }

        if (!userService.hasAnyRole(user, Role.ADMIN,Role.AUTHOR)) {
            throw new AccessDeniedException("작성 권한 없음");
        }

        BookPost post = new BookPost();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setBookImageUrl(dto.getBookImageUrl());
        post.setPrice(dto.getPrice());
        post.setStock(dto.getStock());
        post.setWriter(user);
        post.setCreatedAt(LocalDateTime.now());

        bookPostRepository.save(post);
        return post.getId();
    }

    public List<BookPost> findAll() {
        return bookPostRepository.findAll();
    }

    public BookPost getBookPostById(Long id) {
        return bookPostRepository.getBookPostById(id);
    }
}
