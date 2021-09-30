package com.dci.demo.service;

import com.dci.demo.model.security.JwtRequest;
import com.dci.demo.security.JwtTokenUtil;
import org.easymock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@ExtendWith(EasyMockExtension.class)
public class AuthServiceTest extends EasyMockSupport {

    @TestSubject
    AuthService authService = new AuthServiceImpl();

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    JwtUserDetailsService jwtUserDetailsService;

    @Mock
    JwtRequest jwtRequest;

    @Mock
    AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void createAuthenticationToken() throws Exception {

        String username = "username";
        String password = "password";
        String token = "token";
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        UserDetails userDetails = new User("username", "password",
                new ArrayList<>());

        jwtRequest.getUsername();
        EasyMock.expectLastCall().andReturn(username).times(2);

        jwtUserDetailsService.loadUserByUsername(username);
        EasyMock.expectLastCall().andReturn(userDetails).times(1);

        jwtRequest.getPassword();
        EasyMock.expectLastCall().andReturn(password);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        EasyMock.expectLastCall().andReturn(EasyMock.anyObject());

        jwtTokenUtil.generateToken(userDetails);
        EasyMock.expectLastCall().andReturn(token);

        replayAll();

        String actualResponse = authService.createAuthenticationToken(jwtRequest);
        Assertions.assertEquals(token, actualResponse);

        verifyAll();
    }
}