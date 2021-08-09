package com.study.realworld.secure;

import java.security.SecureRandom;

public class SecureGUIDGenerator extends GUIDGenerator {
    public SecureGUIDGenerator(int length) throws Exception {
        super(length);
    }

    protected SecureRandom makeRand() {
        return new SecureRandom();
    }

    public static String gen(int length) {
        try {
            return new SecureGUIDGenerator(length).generate();
        } catch (Exception e) {
            return null;
        }
    }

    public static String genHex(int length) {
        try {
            return new SecureGUIDGenerator(length).generateHex();
        } catch (Exception e) {
            return null;
        }
    }
}
