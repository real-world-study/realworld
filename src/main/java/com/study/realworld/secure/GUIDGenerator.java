package com.study.realworld.secure;

import java.util.Random;

public class GUIDGenerator implements IGenerator {
	
	protected final int length;
	protected final Random random;
	
	public GUIDGenerator(int length) throws Exception {
		if( length < 1 ) throw new IllegalArgumentException("length < 1: " + length);
		_length = length;
		_random = makeRand();
	}
	
	protected Random makeRand() {
		return new Random();
	}

	@Override
	public String generate() {
		StringBuilder guid = new StringBuilder(_length);
		for( int i = 0 ; i < _length ; i++ ) guid.append( _upper.charAt( _random.nextInt(_upper.length()) ) );
		return guid.toString();
	}

	@Override
	public String generateHex() {
		StringBuilder guid = new StringBuilder(_length);
		for( int i = 0 ; i < _length ; i++ ) guid.append( _upperHex.charAt( _random.nextInt(_upperHex.length()) ) );
		return guid.toString();
	}
}
