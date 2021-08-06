package com.study.realworld.bean;

import com.study.realworld.dao.PSSetDao;
import com.study.realworld.dao.ResultDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

@Component
public class UserBean {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserBean(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkSameName(String username) { //같은 닉네임의 유저가 있는지 확인
        final String query = "select EXISTS(select id from USER where USERNAME=?)";
        return (boolean) jdbcTemplate.query(query, new PSSetDao.PSSForString(username), new ResultDao.RSEForBoolean());
    }

    public boolean checkSameEmail(String email) { //같은 이메일의 유저가 있는지 확인
        final String query = "select EXISTS(select id from USER where email=?)";
        return (boolean) jdbcTemplate.query(query, new PSSetDao.PSSForString(email), new ResultDao.RSEForBoolean());
    }

    public int registUser(String username, String email, String password) { //신규 유저 삽입
        final String query = "insert into USER(USERNAME, EMAIL, PASSWORD) values (?, ?, ?)";
        return jdbcTemplate.update(query, new PSSetDao.PSSForTripleString(username, email, password));
    }

    public Map<String, Object> getUsers(String email) {
        final String query = "select email,username,bio,image from USER where email=?";
        return (Map<String, Object>) jdbcTemplate.query(query, new PSSetDao.PSSForString(email), new ResultDao.RSEForResult());
    }
}
