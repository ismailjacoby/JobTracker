package com.ismailjacoby.jobtrackerapi.controller;

import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.SignupForm;
import com.ismailjacoby.jobtrackerapi.service.declaration.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Authenticates a user and returns an authentication token.
     *
     * @param form The login credentials.
     * @return Authentication details including the token.
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid LoginForm form) {
        return userService.login(form);
    }

    /**
     * Registers a new user account.
     *
     * @param form The user registration details.
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping("/signup")
    public void signup(@RequestBody @Valid SignupForm form) {
        userService.signup(form);
    }
}
