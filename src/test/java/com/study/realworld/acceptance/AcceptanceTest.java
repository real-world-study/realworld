package com.study.realworld.acceptance;

import com.study.realworld.domain.auth.dto.Login;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import com.study.realworld.domain.user.dto.UserJoin;
import com.study.realworld.domain.user.error.UserErrorResponse;
import com.study.realworld.util.DatabaseCleanup;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static com.study.realworld.domain.user.util.UserFixture.USER_PASSWORD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AcceptanceTest {

    protected static final int FIRST = 0;
    protected static final String AUTHORIZATION = "Authorization";
    protected static final String BEARER = "bearer ";
    protected static final String AT = "@";

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    public void setUp() {
        if (RestAssured.port == RestAssured.UNDEFINED_PORT) {
            RestAssured.port = port;
            databaseCleanup.afterPropertiesSet();
        }
        databaseCleanup.execute();
    }

    protected UserJoin.Response 회원_가입_되어있음(final String email) {
        final ExtractableResponse<Response> response = 회원_가입_요청(email);
        return response.as(UserJoin.Response.class);
    }

    protected Login.Response 로그인_되어있음(final String email) {
        final ExtractableResponse<Response> response = 로그인_요청(email);
        return response.as(Login.Response.class);
    }

    protected UserErrorResponse 유저_예외_결과(final ExtractableResponse<Response> response) {
        return response.as(UserErrorResponse.class);
    }

    protected ExtractableResponse<Response> 회원_가입_요청(final String email) {
        final UserJoin.Request request = 정상적인_회원가입_정보(email);
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users")
                .then()
                .extract();
    }

    protected ExtractableResponse<Response> 로그인_요청(final String userEmail) {
        final Login.Request request = 정상적인_로그인_정보(userEmail);
        return RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when()
                .post("/api/users/login")
                .then()
                .extract();
    }

    protected UserJoin.Request 정상적인_회원가입_정보(final String email) {
        final String userName = email.split(AT)[FIRST];
        return UserJoin.Request.builder()
                .userEmail(UserEmail.from(email))
                .userName(UserName.from(userName))
                .userPassword(USER_PASSWORD)
                .build();
    }

    protected Login.Request 정상적인_로그인_정보(final String userEmail) {
        return Login.Request.builder()
                .userEmail(UserEmail.from(userEmail))
                .userPassword(USER_PASSWORD)
                .build();
    }
}
