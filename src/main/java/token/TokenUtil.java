package token;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {

	public static Logger log = Logger.getLogger(TokenUtil.class);

	/**
	 * 创建JWT
	 */
	// withIssuer 该JWT的签发者
	// withSubject 该JWT所面向的用户
	// withExpiresAt 什么时候过期，这里是一个Unix时间戳
	// withIssuedAt 在什么时候签发的
	// withClaim 存储的数据
	public static String createJwt() throws IllegalArgumentException, UnsupportedEncodingException {
		// 指定创建token的方式
		Algorithm al = Algorithm.HMAC256("secretkey");
		// 创建token
		String token = JWT.create().withIssuer("签发者").withSubject("用户")
				.withExpiresAt(new Date(System.currentTimeMillis() + 60000))
				.withIssuedAt(new Date(System.currentTimeMillis())).withClaim("userid", "MYID").sign(al);
		return token;
	}

	/** 验证jwt */
	public static boolean verifyJwt(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256("secretkey");
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			if (!"签发者".equals(jwt.getIssuer())) {
				return false;
			}
			if (!"用户".equals(jwt.getSubject())) {
				return false;
			}
			// 判断是否过期
			if (new Date().after(jwt.getExpiresAt())) {
				return false;
			}
			// 判断是否比签发日小
			if (new Date().before(jwt.getIssuedAt())) {
				return false;
			}
		} catch (TokenExpiredException e) {
			// Token 过期了
			System.out.println("Token 过期了");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Token验证报错:" + e);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			String token = TokenUtil.createJwt();
			//token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLnlKjmiLciLCJpc3MiOiLnrb7lj5HogIUiLCJleHAiOjE1MzY4MDI4NDgsImlhdCI6MTUzNjgwMjc4OCwidXNlcmlkIjoiTVlJRCJ9.hx9ZdO6PAagoMYtoramEkqrdKPrs7jiD9WbzLGTDEZ4";
			System.out.println("token:" + token);
			boolean bol = TokenUtil.verifyJwt(token);
			System.out.println("验证结果:" + bol);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
