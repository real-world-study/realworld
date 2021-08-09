package com.study.realworld.secure;

import java.util.Random;

public class GUIDGenerator implements IGenerator {
	
	protected final int length;
	protected final Random random;
	
	public GUIDGenerator(int lng) throws Exception {
		if( lng < 1 ) throw new IllegalArgumentException("length < 1: " + lng);
		length = lng;
		random = makeRand();
	}

	protected Random makeRand() {
		return new Random();
	}

	@Override
	public String generate() {
		StringBuilder guid = new StringBuilder(length);
		for( int i = 0 ; i < length ; i++ ) guid.append( UPPER.charAt( random.nextInt(UPPER.length()) ) );
		return guid.toString();
	}

	@Override
	public String generateHex() {
		StringBuilder guid = new StringBuilder(length);
		for( int i = 0 ; i < length ; i++ ) guid.append( UPPER_HEX.charAt( random.nextInt(UPPER_HEX.length()) ) );
		return guid.toString();
	}
}
