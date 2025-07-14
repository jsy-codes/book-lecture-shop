package com.jsy_codes.book_lecture_shop.domain;

import com.jsy_codes.book_lecture_shop.domain.user.Grade;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private PhoneNumber phoneNumber;

    private int orderCount = 0;  // 주문 횟수 저장

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (grade == null) grade = grade.BASIC;
    }


}
