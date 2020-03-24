package com.zjmzxfzhl.common.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zjmzxfzhl.common.exception.SysException;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class JwtUtil {
	/**
	 * 过期时间，单位秒s
	 */
	public static final long EXPIRE_TIME = 15 * 60;

	/**
	 * 校验token是否正确
	 *
	 * @param token
	 *            密钥
	 * @param secret
	 *            用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String userId, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("userId", userId).build();
			// 效验TOKEN
			verifier.verify(token);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUserId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("userId").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名
	 *
	 * @param userId
	 *            用户名
	 * @param secret
	 *            用户的密码
	 * @param date
	 *            过期时间
	 * @return 加密的token
	 */
	public static String sign(String userId, String secret, Date date) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// 如果有传过期时间，则加入过期时间
		if (date != null) {
			return JWT.create().withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
		}
		// 不设置过期时间，可以使用redis等缓存来管理过期时间
		else {
			return JWT.create().withClaim("userId", userId).withClaim("date", DateUtil.getNow()).sign(algorithm);
		}
	}

	/**
	 * 生成签名
	 *
	 * @param userId
	 *            用户名
	 * @param secret
	 *            用户的密码
	 * @return 加密的token
	 */
	public static String sign(String userId, String secret) {
		return sign(userId, secret, null);
	}

	/**
	 * 根据request中的token获取用户账号
	 * 
	 * @param request
	 * @param name
	 * @return
	 * @throws SysException
	 */
	public static String getUserIdByToken(HttpServletRequest request, String name) throws SysException {
		String accessToken = request.getHeader(name);
		String userId = getUserId(accessToken);
		if (CommonUtil.isEmptyStr(userId)) {
			return null;
		}
		return userId;
	}
}
