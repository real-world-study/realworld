package com.study.realworld.dao;

import com.study.realworld.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByName(String username) { //같은 닉네임의 유저가 있는지 확인
        final String query = "select id from USER where USERNAME=?";
        return jdbcTemplate.queryForObject(query, User.class, username);
    }

    public User getUserByEmail(String email) { //같은 이메일의 유저가 있는지 확인
        final String query = "select id from USER where email=?";
        return jdbcTemplate.queryForObject(query, User.class, email);
    }

    public int insertUser(String username, String email, String password) { //신규 유저 삽입
        final String query = "insert into USER(USERNAME, EMAIL, PASSWORD) values (?, ?, ?)";
        return jdbcTemplate.update(query, new PSSetDao.PSSForStrings(username, email, password));
    }
}
