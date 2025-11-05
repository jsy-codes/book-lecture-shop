package com.jsy_codes.book_lecture_shop.repository;


import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.domain.post.Category.CategoryType;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookPostRepository {

    private EntityManager em;
    private final ItemRepository itemRepository;

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
    public List<BookPost> findByCategory(CategoryType category) {
        return em.createQuery("SELECT b from BookPost b where b.category = :category",BookPost.class)
                .setParameter("category",category)
                .getResultList();

    }

    public Long createBookPostInit(BookPostDto dto, User writer) {
        Book book = new Book();
        book.setName(dto.getTitle());
        book.setPrice(dto.getPrice());
        book.setStockQuantity(dto.getStockQuantity());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());

        itemRepository.save(book);

        BookPost post = new BookPost();

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setBookImageUrl(dto.getBookImageUrl());
        post.setBook(book);
        post.setCategory(dto.getCategory());
        post.setWriter(writer);
        post.setCreatedAt(LocalDateTime.now());

        this.save(post);

        return post.getId();
    }
    /*
    public BookPost findById(Long id) {
        return em.find(BookPost.class, id);
    }*/

}
