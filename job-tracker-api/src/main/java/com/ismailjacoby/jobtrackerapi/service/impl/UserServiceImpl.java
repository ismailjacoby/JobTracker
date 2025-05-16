package com.ismailjacoby.jobtrackerapi.service.impl;

import com.ismailjacoby.jobtrackerapi.exception.DuplicateException;
import com.ismailjacoby.jobtrackerapi.models.entity.User;
import com.ismailjacoby.jobtrackerapi.models.form.SignupForm;
import com.ismailjacoby.jobtrackerapi.repository.UserRepository;
import com.ismailjacoby.jobtrackerapi.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signup(SignupForm form) {
        String email = form.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateException("Email already exists");
        }

        if (!form.password().equals(form.confirmPassword())){
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setFirstName(form.firstName());
        user.setLastName(form.lastName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(form.password()));
        userRepository.save(user);
    }
}
