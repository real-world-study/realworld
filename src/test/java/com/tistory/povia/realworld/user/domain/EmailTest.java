package com.tistory.povia.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class EmailTest {

    public static Stream<String> blankStrings() {
        return Stream.of(null, "", " ", "    ");
    }

    @ParameterizedTest
    @MethodSource("blankStrings")
    @DisplayName("이메일은 빈 칸, null 일 수 없음")
    void blankTest(final String address) {
        assertThatThrownBy(() -> new Email(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email should be provided");
    }

    @Test
    @DisplayName("이메일은 xxx@xxx.xxx의 규격이어야 할 것 - 실패 테스트")
    void emailNotValidFormatTest() {
        assertThatThrownBy(() -> new Email("test@test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("email should be 'xxx@xxx.xxx'");
    }

    @Test
    @DisplayName("이메일은 xxx@xxx.xxx의 규격이어야 할 것 - 성공 테스트")
    void emailValidFormatTest() {
        String address = "test@test.com";
        Email email = new Email(address);

        assertThat(new Email(address).equals(email)).isTrue();
        assertThat(address).isEqualTo(email.address());
        assertThat(email.toString()).isNotBlank();
    }
}
