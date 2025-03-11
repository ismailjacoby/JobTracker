package com.ismailjacoby.jobtrackerapi.service.implementation;

import com.ismailjacoby.jobtrackerapi.config.security.JWTProvider;
import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.entity.UserEntity;
import com.ismailjacoby.jobtrackerapi.enums.UserRole;
import com.ismailjacoby.jobtrackerapi.exception.AccountNotActiveException;
import com.ismailjacoby.jobtrackerapi.exception.NotFoundException;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.SignupForm;
import com.ismailjacoby.jobtrackerapi.repository.UserRepository;
import com.ismailjacoby.jobtrackerapi.service.declaration.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    /**
     * Handles user login by authenticating credentials and generating a JWT token for valid users.
     *
     * @param form The login credentials containing the username and password.
     * @return AuthDTO containing the authenticated user's information and generated JWT token.
     * @throws IllegalArgumentException if the login credentials are invalid.
     * @throws RuntimeException if authentication fails.
     * @throws NotFoundException if the user is not found in the database.
     * @throws AccountNotActiveException if the user's account is inactive.
     */
    @Override
    public AuthDTO login(LoginForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Login form cannot be null");
        }

        String username = form.username().toLowerCase(); // Ensure case-insensitivity

        // Attempt to authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, form.password())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }

        // Retrieve user from the database
        UserEntity user = userRepository.findByUsername(form.username())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Checks if the user’s account is active
        if(!user.isActive()){
            throw new AccountNotActiveException("Your account is not active. Please contact support for help.");
        }

        // Generates a JWT token for the authenticated user
        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

        // Returns the AuthDTO with the user’s information and generated token
        return new AuthDTO(user.getUsername(), token, user.getRole());
    }


    /**
     * Handles user signup by validating the form and saving the new user into the database.
     *
     * @param form The signup details containing the user’s personal and account information.
     * @throws IllegalArgumentException if the form is invalid or the email/username already exists.
     */
    @Override
    public void signup(SignupForm form) {
        if(form == null) {
            throw new IllegalArgumentException("Form cannot be null");
        }

        String username = form.username().toLowerCase();
        String email = form.email().toLowerCase();

        // Checks if the email already exists in the database
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email address already in use");
        }

        // Checks if the username already exists in the database
        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already in use");
        }

        // Creates a new User from the signup form data
        UserEntity user = new UserEntity();
        user.setFirstName(form.firstName());
        user.setLastName(form.lastName());
        user.setEmail(form.email());
        user.setUsername(form.username().toLowerCase());
        user.setPassword(passwordEncoder.encode(form.password()));
        user.setActive(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);
    }
}
