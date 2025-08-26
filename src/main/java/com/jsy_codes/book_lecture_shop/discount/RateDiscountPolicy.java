package com.jsy_codes.book_lecture_shop.discount;


import com.cornCar.jpaShop.domain.Member;
import com.cornCar.jpaShop.annotation.MainDiscountPolicy;
import com.cornCar.jpaShop.domain.member.Grade;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade()== Grade.VIP){
            return price*discountPercent/100;
        }else {
            return 0;
        }
    }
}
