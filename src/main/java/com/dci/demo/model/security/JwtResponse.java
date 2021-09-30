package com.dci.demo.model.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -3994854938729545356L;
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }
}