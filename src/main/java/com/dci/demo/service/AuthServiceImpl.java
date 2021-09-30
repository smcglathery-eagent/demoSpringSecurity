package com.dci.demo.service;

import com.dci.demo.model.security.JwtRequest;
import com.dci.demo.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthServiceImpl implements AuthService {

    private final static Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    public String createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        String username = authenticationRequest.getUsername();

        log.debug("Creating authentication token for user: '" + username + "'");

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(username);

        final String token = jwtTokenUtil.generateToken(userDetails);
        log.debug("Token created: " + token);

        return token;
    }

    private void authenticate(String username, String password) throws Exception {

        log.debug("Attempting to authenticate user '" + username + "' with password '" + password + "'");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}