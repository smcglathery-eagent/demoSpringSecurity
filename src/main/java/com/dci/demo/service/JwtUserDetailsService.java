package com.dci.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Attempting to load user by username: '" + username + "'");

        if ("testuser".equals(username)) {

            User user = new User("testuser", "$2a$10$ixlPY3AAd4ty1l6E2IsQ9OFZi2ba9ZQE0bP7RFcGIWNhyFrrT3YUi",
                    new ArrayList<>());
            log.debug("Found user: '" + username + "'");
            return user;
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}