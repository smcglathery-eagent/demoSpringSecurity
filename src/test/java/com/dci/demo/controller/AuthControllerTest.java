package com.dci.demo.controller;

import com.dci.demo.model.security.JwtRequest;
import com.dci.demo.service.AuthService;
import org.easymock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class AuthControllerTest extends EasyMockSupport {

    @TestSubject
    AuthController authController = new AuthController();

    @Mock
    AuthService authService;

    @Mock
    JwtRequest jwtRequest;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void createAuthenticationToken() throws Exception {

        String token = "token";

        authService.createAuthenticationToken(jwtRequest);
        EasyMock.expectLastCall().andReturn(token);

        replayAll();

        String actualResponse = authController.createAuthenticationToken(jwtRequest);
        Assertions.assertEquals(token, actualResponse);

        verifyAll();
    }
}