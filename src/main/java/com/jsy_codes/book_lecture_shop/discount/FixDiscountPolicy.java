package com.jsy_codes.book_lecture_shop.discount;



import com.jsy_codes.book_lecture_shop.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("FixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy{
    private int discountFixAmount = 1000;
    @Override
    public int discount(User user, int price){
            return discountFixAmount;
    }
}
