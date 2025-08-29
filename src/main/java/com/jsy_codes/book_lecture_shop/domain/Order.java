package com.jsy_codes.book_lecture_shop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Integer totalPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [PENDING, PAID, CANCELLED]

    private LocalDateTime orderDate;




//    @PrePersist
//    public void prePersist() {
//        orderDate = LocalDateTime.now();
//        if (status == null) status = OrderStatus.PENDING;
//    }
        //==생성 메서드==//
    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        int totalPrice = order.getTotalPrice();
       // int discountPrice = orderService.getDiscount(user, totalPrice);
        order.setStatus(OrderStatus.ORDER);
       // user.updatedBalance(totalPrice-discountPrice);
        user.setOrderCount(user.getOrderCount()+1);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }



    //==연관관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {

        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

//    //==생성 메서드==//
//    public static Order createOrder(user user, Delivery delivery, OrderItem... orderItems) {
//        Order order = new Order();
//        order.setuser(user);
//        order.setDelivery(delivery);
//        for (OrderItem orderItem : orderItems) {
//            order.addOrderItem(orderItem);
//        }
//        int totalPrice = order.getTotalPrice();
//        int discountPrice = orderService.getDiscount(user, totalPrice);
//        order.setStatus(OrderStatus.ORDER);
//        user.updatedBalance(totalPrice-discountPrice);
//        user.setOrderCount(user.getOrderCount()+1);
//        order.setOrderDate(LocalDateTime.now());
//        return order;
//    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCELLED);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
