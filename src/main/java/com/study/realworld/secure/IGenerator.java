package com.study.realworld.secure;

public interface IGenerator {
	static final String _upper = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static final String _upperHex = "0123456789ABCDEF";

    String generate();    
    String generateHex();
}