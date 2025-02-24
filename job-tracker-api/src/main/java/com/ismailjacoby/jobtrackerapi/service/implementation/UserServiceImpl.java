package com.ismailjacoby.jobtrackerapi.service.implementation;

import com.ismailjacoby.jobtrackerapi.config.security.JWTProvider;
import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.entity.UserEntity;
import com.ismailjacoby.jobtrackerapi.enums.UserRole;
import com.ismailjacoby.jobtrackerapi.exception.AccountNotActiveException;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.RegisterForm;
import com.ismailjacoby.jobtrackerapi.repository.UserRepository;
import com.ismailjacoby.jobtrackerapi.service.declaration.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JWTProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthDTO login(LoginForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Login form cannot be null");
        }

        // Attempt to authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.username(), form.password())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid username or password");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

        // Retrieve user from the database
        UserEntity user = userRepository.findByUsername(form.username())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Check if the user account is active
        if(!user.isActive()){
            throw new AccountNotActiveException("Your account is not active. Please contact support for help.");
        }

        // Generate JWT token
        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

        // Return AuthDTO
        return new AuthDTO(user.getUsername(), token, user.getRole());
    }

    @Override
    public void register(RegisterForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        if(userRepository.existsByEmail(form.email())){
            throw new IllegalArgumentException("Email address already in use");
        }

        if(userRepository.existsByUsername(form.username())){
            throw new IllegalArgumentException("Username already in use");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(form.firstName());
        user.setLastName(form.lastName());
        user.setEmail(form.email());
        user.setUsername(form.username());
        user.setPassword(passwordEncoder.encode(form.password()));
        user.setActive(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }
}
