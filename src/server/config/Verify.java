package server.config;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 登录核对类.
 * <p>
 * 用户注册或登录时暂时保存用户信息,并对用户密码进行md5加密.
 *
 * @author BoluBolu
 */
public final class Verify implements Serializable {

	/**
	 * 用户名
	 */
	private String userId;

	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 创建一个存储用户ID、密码经过MD5加密后的对象.
	 *
	 * @param userId       用户id
	 * @param userPassword 用户密码
	 */
	public Verify(String userId, String userPassword) {
		this.userId = userId;
		this.userPassword = getMd5(userPassword);
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * 加密密码. 对用户输入的密码进行md5加密,提高用户密码安全性.
	 *
	 * @param str 用户密码
	 * @return 被加密了的用户密码
	 * @throws NoSuchAlgorithmException 加密失败
	 */
	public static String getMd5(String str) {
		String mdPassword = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 计算md5函数
			md.update(str.getBytes());

			// 保留16位
			mdPassword = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD5加密失败：" + e.getMessage());
		}
		return mdPassword;
	}
}
