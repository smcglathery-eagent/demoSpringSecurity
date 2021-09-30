package com.dci.demo.service;

import com.dci.demo.model.security.JwtRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception;
}