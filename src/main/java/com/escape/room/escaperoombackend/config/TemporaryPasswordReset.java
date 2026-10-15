package com.escape.room.escaperoombackend.config;

import com.escape.room.escaperoombackend.domain.user.User;
import com.escape.room.escaperoombackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "app.dev.reset-password",
        havingValue = "true"
)
public class TemporaryPasswordReset implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        User user = userRepository.findByEmail("test@test.com")
                .orElseThrow(() ->
                        new IllegalArgumentException("계정을 찾을 수 없습니다."));

        user.changePassword(passwordEncoder.encode("EscapeRoom123!"));
        userRepository.save(user);

        System.out.println("테스트 계정 비밀번호 재설정 완료");
    }
}