package com.jsy_codes.book_lecture_shop.repository;


import com.jsy_codes.book_lecture_shop.domain.post.BookPost;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import jakarta.persistence.EntityManager;
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
        return em.createQuery("select b from BookPost b where b.id = :id", BookPost.class)
                .setParameter("id", id)
                .getSingleResult();
    }
    /*
    public BookPost findById(Long id) {
        return em.find(BookPost.class, id);
    }*/

}
