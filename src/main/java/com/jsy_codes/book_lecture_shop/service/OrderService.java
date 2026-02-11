package com.jsy_codes.book_lecture_shop.service;

import com.jsy_codes.book_lecture_shop.domain.*;

import com.jsy_codes.book_lecture_shop.domain.item.Item;
import com.jsy_codes.book_lecture_shop.repository.ItemRepository;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import com.jsy_codes.book_lecture_shop.repository.OrderRepository;
import com.jsy_codes.book_lecture_shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public int getDiscount(User User,int price){ return orderRepository.discount(User,price); }

    /**
     * 주문
     */
    @Transactional
    public Long order(Long userId, Long itemId, int count) {

        //엔티티 조회
        User user = userRepository.findById(userId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("item not found"));

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(user.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = createOrder(user, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    //==생성 메서드==//
    public Order createOrder(User user, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        int totalPrice = order.getTotalPrice();
        int discountPrice = getDiscount(user, totalPrice);
        order.setStatus(OrderStatus.ORDER);

       // user.updatedBalance(totalPrice-discountPrice);
       // user.setOrderCount(User.getOrderCount()+1);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId);
        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
