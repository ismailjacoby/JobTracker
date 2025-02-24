package com.ismailjacoby.jobtrackerapi.controller;

import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.RegisterForm;
import com.ismailjacoby.jobtrackerapi.service.declaration.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid LoginForm form) {
        return userService.login(form);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterForm form) {
        userService.register(form);
    }
}
