package com.jsy_codes.book_lecture_shop.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private String countryCode;
    private String areaCode;
    private String subscriberNumber;

    public String fullNumber() {

        return countryCode + areaCode + subscriberNumber;

    }
}
