//package com.jsy_codes.book_lecture_shop.repository;
//
//
<<<<<<< HEAD
<<<<<<< HEAD
//import com.jsy_codes.book_lecture_shop.domain.item.Item;
=======
>>>>>>> main
=======
>>>>>>> v0.9.5
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Repository;
//
//import jakarta.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//@RequiredArgsConstructor
//public class ItemRepository {
//
//    private final EntityManager em;
//
//    public void save(Item item) {
//        if (item.getId() == null) {
//            em.persist(item);
//        } else {
//            em.merge(item);
//        }
//    }
//
//    public Item findByUsername(Long id) {
//        return em.find(Item.class, id);
//    }
//
//    public List<Item> findAll() {
//        return em.createQuery("select i from Item i", Item.class)
//                .getResultList();
//    }
//}
