package com.escape.room.escaperoombackend.controller.user;

import com.escape.room.escaperoombackend.dto.user.request.CreateUserRequest;
import com.escape.room.escaperoombackend.dto.user.request.LoginRequest;
import com.escape.room.escaperoombackend.dto.user.response.LoginResponse;
import com.escape.room.escaperoombackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public void createUser(@Valid @RequestBody CreateUserRequest request) {
        userService.createUser(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}