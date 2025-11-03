package com.jsy_codes.book_lecture_shop.repository;


import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookPostRepository {

    private EntityManager em;

    //저장(등록) 메서드
    public void save(BookPost bookPost) {
        if(bookPost.getBookImageUrl() != null) {
            em.persist(bookPost);
        }
        else {
            em.merge(bookPost);
        }
    }
    public List<BookPost> findAll() {
        return em.createQuery("select b from BookPost b", BookPost.class).getResultList();
    }

    public BookPost getBookPostById(Long id) {
        try {
            return em.createQuery("select b from BookPost b where b.id = :id", BookPost.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long createBookPostInit(BookPostDto dto, User writer) {
        // 1.Book 엔티티 생성
        Book book = new Book();
        book.setName(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPrice(dto.getPrice());
        book.setStockQuantity(dto.getStockQuantity());
        book.setIsbn(dto.getIsbn());

        em.persist(book);

        // 2.BookPost 생성
        BookPost post = new BookPost();
        post.setBook(book);
        post.setBookImageUrl(dto.getBookImageUrl());

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setCategory(dto.getCategory());
        post.setWriter(writer); // Post 필드 writer

        em.persist(post);

        return post.getId();
    }
    /*
    public BookPost findById(Long id) {
        return em.find(BookPost.class, id);
    }*/

}
