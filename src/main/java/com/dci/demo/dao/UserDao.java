package com.dci.demo.dao;

import com.dci.demo.model.user.User;

public interface UserDao {

    User getUserByUsername(String username);
}