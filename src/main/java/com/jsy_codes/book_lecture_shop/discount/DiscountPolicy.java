package com.jsy_codes.book_lecture_shop.discount;

import com.jsy_codes.book_lecture_shop.domain.User;


public interface DiscountPolicy  {
    /**
     *
     * @return 할인 대상 금액
     *  */
    int discount(User user, int price);
}
