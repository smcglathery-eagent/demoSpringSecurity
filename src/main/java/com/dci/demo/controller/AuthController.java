package com.dci.demo.controller;

import com.dci.demo.model.security.JwtRequest;
import com.dci.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Validated
public class AuthController {

    @Autowired
    AuthService authService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest) throws Exception {

        return authService.createAuthenticationToken(authenticationRequest);
    }
}