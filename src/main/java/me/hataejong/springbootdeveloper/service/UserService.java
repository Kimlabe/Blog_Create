package me.hataejong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hataejong.springbootdeveloper.domain.User;
import me.hataejong.springbootdeveloper.dto.AddUserRequest;
import me.hataejong.springbootdeveloper.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // 토큰 서비스 메서드 추가
    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email){
            // findByEmail() : 이메일을 입력 받아 users 테이블에서 유저를 찾고, 없으면 예외를 발생
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
