package com.ismailjacoby.jobtrackerapi.service.declaration;

import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.SignupForm;

public interface UserService {
    AuthDTO login(LoginForm form);
    void signup(SignupForm form);
}
