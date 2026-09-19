package com.escape.room.escaperoombackend.service.user;

import com.escape.room.escaperoombackend.domain.user.User;
import com.escape.room.escaperoombackend.dto.user.request.CreateUserRequest;
import com.escape.room.escaperoombackend.dto.user.request.LoginRequest;
import com.escape.room.escaperoombackend.dto.user.response.LoginResponse;
import com.escape.room.escaperoombackend.repository.user.UserRepository;
import com.escape.room.escaperoombackend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getEmail(),
                encodedPassword,
                request.getNickname()
        );
        userRepository.save(user);

    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(
                user.getEmail(),
                user.getNickname(),
                token
        );
    }

}
