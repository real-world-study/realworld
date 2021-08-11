package com.study.realworld.dao;

import com.study.realworld.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> getUserByName(String username) throws EmptyResultDataAccessException { //같은 닉네임의 유저가 있는지 확인
        final String query = "select id, email, username, password, bio, image from USER where USERNAME=?";
        User user = (User) jdbcTemplate.query(query, new PSSetDao.PSSForStrings(username), new ResultDao.RSEForUser());
        return ofNullable(user);
    }

    public Optional<User> getUserByEmail(String email) { //같은 이메일의 유저가 있는지 확인
        final String query = "select id, email, username, password, bio, image from USER where EMAIL=?";
        User user = (User) jdbcTemplate.query(query, new PSSetDao.PSSForStrings(email), new ResultDao.RSEForUser());
        return ofNullable(user);
    }

    public int insertUser(String username, String email, String password) { //신규 유저 삽입
        final String query = "insert into USER(USERNAME, EMAIL, PASSWORD) values (?, ?, ?)";
        return jdbcTemplate.update(query, new PSSetDao.PSSForStrings(username, email, password));
    }
}
