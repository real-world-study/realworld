package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @DisplayName("Name 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Name name = new Name();

        assertAll(
                () -> assertThat(name).isNotNull(),
                () -> assertThat(name).isExactlyInstanceOf(Name.class)
        );
    }

    @DisplayName("Name 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String nameString = "test";
        final Name name = new Name(nameString);

        assertAll(
                () -> assertThat(name).isNotNull(),
                () -> assertThat(name).isExactlyInstanceOf(Name.class)
        );
    }

    @DisplayName("Name 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String nameString = "test";
        final Name name = new Name(nameString);

        assertThat(name.name()).isEqualTo(nameString);
    }

    @DisplayName("Name 인스턴스 equals and hashcode 동등성 검증 테스트")
    @Test
    void equals_and_hashcode_test() {
        final String nameString = "test";
        final Name firstName = new Name(nameString);
        final Name secondName = new Name(nameString);

        assertAll(
                () -> assertThat(firstName).isEqualTo(secondName),
                () -> assertThat(firstName.hashCode()).isEqualTo(secondName.hashCode())
        );
    }

    @DisplayName("Name 인스턴스 toString 테스트")
    @Test
    void toString_test() {
        final String nameString = "test";
        final Name name = new Name(nameString);

        assertThat(name.toString()).isEqualTo(String.format("Name{name='%s'}", nameString));
    }

}