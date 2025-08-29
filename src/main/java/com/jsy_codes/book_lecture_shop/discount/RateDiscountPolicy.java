package com.jsy_codes.book_lecture_shop.discount;


import com.jsy_codes.book_lecture_shop.annotation.MainDiscountPolicy;
import com.jsy_codes.book_lecture_shop.domain.User;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
    @Override
    public int discount(User user, int price) {
        return price*discountPercent/100;
    }
}
