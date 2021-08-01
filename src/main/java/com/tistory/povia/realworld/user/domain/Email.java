package com.tistory.povia.realworld.user.domain;


import javax.persistence.Column;
import javax.persistence.Embeddable;

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

}
