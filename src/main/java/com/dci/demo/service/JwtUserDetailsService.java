package com.dci.demo.service;

import com.dci.demo.dao.UserDao;
import com.dci.demo.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User daoUser;

        log.debug("Attempting to load user by username: '" + username + "'");

        try {
            daoUser = userDao.getUserByUsername(username);
            log.debug("Found user: '" + daoUser.toString() + "'");
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(daoUser.getUsername(), daoUser.getPassword(),
                new ArrayList<>());
    }
}