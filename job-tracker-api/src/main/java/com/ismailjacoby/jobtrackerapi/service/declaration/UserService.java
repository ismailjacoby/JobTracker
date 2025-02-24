package com.ismailjacoby.jobtrackerapi.service.declaration;

import com.ismailjacoby.jobtrackerapi.dto.AuthDTO;
import com.ismailjacoby.jobtrackerapi.form.LoginForm;
import com.ismailjacoby.jobtrackerapi.form.RegisterForm;

public interface UserService {
    AuthDTO login(LoginForm form);
    void register(RegisterForm form);
}
