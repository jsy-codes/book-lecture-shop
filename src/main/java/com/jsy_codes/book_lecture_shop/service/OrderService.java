//package com.jsy_codes.book_lecture_shop.service;
//
//import com.jsy_codes.book_lecture_shop.domain.*;
////import com.jsy_codes.book_lecture_shop.domain.item.Item;
////import com.jsy_codes.book_lecture_shop.repository.ItemRepository;
//import com.jsy_codes.book_lecture_shop.repository.UserRepository;
//import com.jsy_codes.book_lecture_shop.repository.OrderRepository;
//import com.jsy_codes.book_lecture_shop.repository.OrderSearch;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//    private final UserRepository UserRepository;
//    private final ItemRepository itemRepository;
//    public int getDiscount(User User,int price){ return orderRepository.discount(User,price); }
//
//    /**
//     * 주문
//     */
//    @Transactional
//    public Long order(String UserId, Long itemId, int count) {
//
//        //엔티티 조회
//        User User = UserRepository.findByUsername(UserId);
//        Item item = itemRepository.findByUsername(itemId);
//
//        //배송정보 생성
//        Delivery delivery = new Delivery();
//        delivery.setAddress(User.getAddress());
//        delivery.setStatus(DeliveryStatus.READY);
//
//        //주문상품 생성
//        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
//
//        //주문 생성
//        Order order = createOrder(User, delivery, orderItem);
//
//        //주문 저장
//        orderRepository.save(order);
//
//        return order.getId();
//    }
//    //==생성 메서드==//
//    public Order createOrder(User User, Delivery delivery, OrderItem... orderItems) {
//        Order order = new Order();
//        order.setUser(User);
//        order.setDelivery(delivery);
//        for (OrderItem orderItem : orderItems) {
//            order.addOrderItem(orderItem);
//        }
//        int totalPrice = order.getTotalPrice();
//        int discountPrice = getDiscount(User, totalPrice);
//        order.setStatus(OrderStatus.ORDER);
//        User.updatedBalance(totalPrice-discountPrice);
//        User.setOrderCount(User.getOrderCount()+1);
//        order.setOrderDate(LocalDateTime.now());
//        return order;
//    }
//
//    /**
//     * 주문 취소
//     */
//    @Transactional
//    public void cancelOrder(Long orderId) {
//        //주문 엔티티 조회
//        Order order = orderRepository.findByUsername(orderId);
//        //주문 취소
//        order.cancel();
//    }
//
//    //검색
//    public List<Order> findOrders(OrderSearch orderSearch) {
//        return orderRepository.findAllByString(orderSearch);
//    }
//}
