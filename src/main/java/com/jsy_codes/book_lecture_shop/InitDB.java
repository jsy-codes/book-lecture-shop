package com.jsy_codes.book_lecture_shop;

import com.jsy_codes.book_lecture_shop.domain.*;
import com.jsy_codes.book_lecture_shop.domain.item.Book;

import com.jsy_codes.book_lecture_shop.domain.post.category.CategoryType;
import com.jsy_codes.book_lecture_shop.domain.user.Role;
import com.jsy_codes.book_lecture_shop.dto.BookPostDto;
import com.jsy_codes.book_lecture_shop.service.BookPostService;
import com.jsy_codes.book_lecture_shop.service.OrderService;
import com.jsy_codes.book_lecture_shop.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.access.AccessDeniedException;

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
        initService.dbInit3();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final OrderService orderService;
        private final BookPostService bookPostService;
        private final UserService userService;
        public void dbInit0() {
            System.out.println("Init0 " + this.getClass());

            User user = createUser(
                    "admin@bookify.com", "password0",
                    "회원0", Role.ADMIN,
                    new Address("서울", "관악로", "789-007"),
                    new PhoneNumber("010", "7777", "7777")
            );
            em.persist(user);


        }

        public void dbInit1() {
            System.out.println("Init1 " + this.getClass());

            User user = createUser(
                    "user1@bookify.com", "password1",
                    "회원1", Role.AUTHOR,
                    new Address("부산", "해운대", "789-012"),
                    new PhoneNumber("010", "1234", "5678")
            );
            em.persist(user);


        }

        public void dbInit2() {
            User user = createUser(
                    "user2@bookify.com", "password2",
                    "회원2", Role.USER,
                    new Address("서울", "강남", "123-456"),
                    new PhoneNumber("010", "9876", "5432")
            );
            em.persist(user);


        }
        @Transactional
        public void dbInit3() {
            System.out.println("Init3 " + this.getClass());

            // 1admin 계정 가져오기
            User admin = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", "admin@bookify.com")
                    .getSingleResult();

            // 2BookPostDto 생성
            BookPostDto bookPostDto = new BookPostDto();
            bookPostDto.setTitle("초기 테스트 도서");
            bookPostDto.setContent("DB 초기화용 도서입니다.");
            bookPostDto.setPrice(15000);
            bookPostDto.setStockQuantity(50);
            bookPostDto.setAuthor(admin);
            bookPostDto.setIsbn("978-89-000-0000-1");
            bookPostDto.setCategory(CategoryType.SQLD);
            bookPostDto.setBookImageUrl("/images/sample.jpg");

            // 3게시글 생성 (admin 계정)
            Long postId;
            try {
                postId = bookPostService.createBookPostInit(bookPostDto, admin); // user 매개변수 포함
                System.out.println("Created BookPost id = " + postId);
            } catch (AccessDeniedException e) {
                System.out.println("게시글 생성 실패: " + e.getMessage());
                return;
            }

//            // 4주문 생성 (admin 계정)
//            try {
//                orderService.order(admin.getId(), postId, 10);
//                System.out.println("주문 생성 완료: admin 주문 10권");
//            } catch (Exception e) {
//                System.out.println("주문 생성 실패: " + e.getMessage());
//            }

            System.out.println("InitDB3 완료!");
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
