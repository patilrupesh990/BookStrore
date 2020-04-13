package com.bookstore.util;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtTokenUtil {
	private static final String SECRET = "9876543210";

	public String generateToken(long l) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		} catch (Exception e) {

		}
		return token;
	}

	public int parseToken(String token) {
		int id = 0;
		if (token != null) {
				id=JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token).getClaim("id").asInt();
		}
		return id;
	}
}
