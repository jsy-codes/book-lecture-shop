package com.jsy_codes.book_lecture_shop.domain;
import lombok.Getter;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

//    protected Address() {
//    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
