package com.study.realworld.secure;

public interface IGenerator {
	static final String UPPER = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String UPPER_HEX = "0123456789ABCDEF";

    String generate();    
    String generateHex();
}
