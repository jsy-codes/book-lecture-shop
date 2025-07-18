package com.jsy_codes.book_lecture_shop.repository;



import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Getter
public class OrderRepository {
//
//    private final EntityManager em;
//    private final DiscountPolicy discountPolicy;
//
//
//    public OrderRepository(EntityManager em,@MainDiscountPolicy DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//        this.em = em;
//    }
//
//    public void save(Order order) {
//        em.persist(order);
//    }
//
//    public Order findByUsername(Long id) {
//        return em.find(Order.class, id);
//    }
//
//    public int discount(Member member, int price) { return discountPolicy.discount(member, price); }
//
//    public List<Order> findAllByString(OrderSearch orderSearch) {
//
//            String jpql = "select o from Order o join o.member m";
//            boolean isFirstCondition = true;
//
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " o.status = :status";
//        }
//
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            if (isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " m.name like :name";
//        }
//
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000);
//
//        if (orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            query = query.setParameter("name", orderSearch.getMemberName());
//        }
//
//        return query.getResultList();
//    }
//
//    /**
//     * JPA Criteria
//     */
//    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//        Root<Order> o = cq.from(Order.class);
//        Join<Object, Object> m = o.join("member", JoinType.INNER);
//
//        List<Predicate> criteria = new ArrayList<>();
//
//        //주문 상태 검색
//        if (orderSearch.getOrderStatus() != null) {
//            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
//            criteria.add(status);
//        }
//        //회원 이름 검색
//        if (StringUtils.hasText(orderSearch.getMemberName())) {
//            Predicate name =
//                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
//            criteria.add(name);
//        }
//
//        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
//        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
//        return query.getResultList();
//    }

}

