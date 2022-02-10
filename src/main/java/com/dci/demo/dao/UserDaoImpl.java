package com.dci.demo.dao;

import com.dci.demo.dao.rowmapper.UserRowMapper;
import com.dci.demo.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initialize() { setDataSource(dataSource);}

    public User getUserByUsername(String username) {

        final String query = "SELECT id, username, password FROM users WHERE username = :username";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("username", username);

        return jdbcTemplate.queryForObject(query, mapSqlParameterSource, new UserRowMapper());
    }
}