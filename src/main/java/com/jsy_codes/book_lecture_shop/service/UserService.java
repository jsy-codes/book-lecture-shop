package com.jsy_codes.book_lecture_shop.service;


import com.jsy_codes.book_lecture_shop.domain.User;
import com.jsy_codes.book_lecture_shop.dto.UserDto;
import com.jsy_codes.book_lecture_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    /**
     * 회원 가입
     */
    @Transactional
    public Long join(User User) {

        validateDuplicateUser(User); //중복회원 검증
        userRepository.passwordEncode(User);
        userRepository.save(User);
        return User.getId();
    }

    private void validateDuplicateUser(User User) {
        List<User> findUsers = userRepository.findByIdWithList(User.getId());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    //public List<User> findByEmail(String id) {return  userRepository.findByEmail(id);}

//    public void updateBalance(User user,int balance) {
//        if (user != null) {
//            user.setBalance(balance); // balance 업데이트
//            UserRepository.save(User); // DB 저장
//        }
//    }
}
