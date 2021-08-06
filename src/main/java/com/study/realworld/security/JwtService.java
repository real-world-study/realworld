package com.study.realworld.security;

import com.study.realworld.user.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    String createToken(User user);

    Optional<User> getUser(String accessToken);

}
