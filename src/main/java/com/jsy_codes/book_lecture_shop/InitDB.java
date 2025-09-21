package com.jsy_codes.book_lecture_shop;

import com.jsy_codes.book_lecture_shop.domain.*;
import com.jsy_codes.book_lecture_shop.domain.item.Book;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.service.OrderService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 초기 DB 세팅
 * * userA
 *   * JPA1 BOOK, JPA2 BOOK
 * * userB
 *   * SPRING1 BOOK, SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit0();
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final OrderService orderService;
        public void dbInit0() {
            System.out.println("Init0 " + this.getClass());

            User user = createUser(
                    "admin@bookify.com", "password0",
                    "회원2", Role.ADMIN,
                    new Address("서울", "관악로", "789-007"),
                    new PhoneNumber("010", "7777", "7777")
            );
            em.persist(user);


        }

        public void dbInit1() {
            System.out.println("Init1 " + this.getClass());

            User user = createUser(
                    "user2@bookify.com", "password1",
                    "회원2", Role.AUTHOR,
                    new Address("부산", "해운대", "789-012"),
                    new PhoneNumber("010", "1234", "5678")
            );
            em.persist(user);


        }

        public void dbInit2() {
            User user = createUser(
                    "user1@bokify.com", "password2",
                    "회원1", Role.USER,
                    new Address("서울", "강남", "123-456"),
                    new PhoneNumber("010", "9876", "5432")
            );
            em.persist(user);


        }

        private User createUser(String email, String rawPassword, String name,
                                Role role, Address address, PhoneNumber phoneNumber) {
            return User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(rawPassword)) // 비밀번호 암호화
                    .name(name)
                    .role(role)
                    .address(address)
                    .phoneNumber(phoneNumber)
                    .build();
        }

        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Delivery createDelivery(User user) {
            Delivery delivery = new Delivery();
            delivery.setAddress(user.getAddress());
            return delivery;
        }
    }
}
