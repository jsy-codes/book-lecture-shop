package com.jsy_codes.book_lecture_shop.controller;

import com.jsy_codes.book_lecture_shop.domain.Address;
import com.jsy_codes.book_lecture_shop.domain.PhoneNumber;
import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.dto.UserDto;
import com.jsy_codes.book_lecture_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    // 회원가입 폼 보여주기
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterDto", new UserDto());
        return "auth/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@Valid UserDto userRegisterDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        // User 엔티티 생성
        User user = User.builder()
                .email(userRegisterDto.getEmail())
                .password(userRegisterDto.getPassword())
                .name(userRegisterDto.getName())
                .address(new Address(
                        userRegisterDto.getCity(),
                        userRegisterDto.getStreet(),
                        userRegisterDto.getZipcode()
                ))
                .phoneNumber(new PhoneNumber(
                        userRegisterDto.getCountryCode(),
                        userRegisterDto.getAreaCode(),
                        userRegisterDto.getSubscriberNumber()
                ))
                .build();

        try {
            userService.join(user);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }

        return "redirect:/login";
    }
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "auth/login";
    }


    public User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null) {
            return userService.findByEmail(auth.getName());
        }
        return null;
    }
}
