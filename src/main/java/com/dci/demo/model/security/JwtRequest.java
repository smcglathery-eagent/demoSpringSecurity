package com.dci.demo.model.security;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = -1821666278015043130L;

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    public JwtRequest() {}

    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}