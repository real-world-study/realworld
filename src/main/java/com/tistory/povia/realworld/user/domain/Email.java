package com.tistory.povia.realworld.user.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.regex.Pattern.matches;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


@Embeddable
public class Email {

    @Column(name = "email", length = 50, updatable = false, nullable = false)
    private String address;

    public Email(String address) {
        checkArgument(isNotBlank(address), "email should be provided");
        checkArgument(checkAddress(address), "email should be 'xxx@xxx.xxx'");
        this.address = address;
    }

    private static boolean checkAddress(String address) {
        return matches("[\\w~\\-.+]+@[\\w~\\-]+(\\.[\\w~\\-]+)+", address);
    }

    public String address(){
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Email{" +
          "address='" + address + '\'' +
          '}';
    }
}
