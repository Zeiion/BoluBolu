package server.config;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 登录核对类
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
	 * 创建一个存储用户ID、密码经过MD5加密后的对象
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
	 * 加密密码
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
