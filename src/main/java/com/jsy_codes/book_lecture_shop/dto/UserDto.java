package com.jsy_codes.book_lecture_shop.dto;


import com.jsy_codes.book_lecture_shop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String email;
    private String password;
    private String name;
    private Address address;
}


