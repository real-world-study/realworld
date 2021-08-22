package com.study.realworld;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RealWorldApplicationTest {
    @Test
    void contextLoads() {
        RealWorldApplication.main(new String[] {});
    }
}
