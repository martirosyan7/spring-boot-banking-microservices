package com.banking.user_service.model.api;

import lombok.Setter;

@Setter
public class LoginBody {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
