package com.jsy_codes.book_lecture_shop.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private Long productId;

    private Integer quantity;

    private Integer unitPrice;

    public enum ProductType {
        BOOK, LECTURE
    }
}
