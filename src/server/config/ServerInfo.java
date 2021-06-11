package server.config;

/**
 * 服务器信息.
 * <p>
 * 定义了服务器类的一些属性,例如端口号,服务器地址以及数据库连接所需的一些属性等.
 *
 * @author BoluBolu
 */
public final class ServerInfo {

	/**
	 * 服务器地址
	 */
	public static String SERVER_IP = "127.0.0.1";

	/**
	 * 聊天端口监听
	 */
	public final static int CHAT_PORT = 6666;

	/**
	 * 验证端口监听
	 */
	public final static int VERIFY_PORT = 7777;

	/**
	 * 数据库端口号
	 */
	public final static int SQL_PORT = 1433;

	/**
	 * 数据库名称
	 */
	public final static String DB_NAME = "bolubolu";

	/**
	 * 数据库连接用户名
	 */
	public static String DB_USER_NAME = "sa";

	/**
	 * 数据库连接密码
	 */
	public static String DB_USER_PASSWORD = "sa123";

	/**
	 * 局域网连接，更改服务器地址
	 *
	 * @param serverIp 新地址
	 */
	public static void setServerIp(String serverIp) {
		SERVER_IP = serverIp;
	}

	/**
	 * 更改数据库用户名
	 *
	 * @param dbUserName 数据库用户名
	 */
	public static void setDbUserName(String dbUserName) {
		DB_USER_NAME = dbUserName;
	}

	/**
	 * 更改数据库密码
	 *
	 * @param dbUserPassword 数据库密码
	 */
	public static void setDbUserPassword(String dbUserPassword) {
		DB_USER_PASSWORD = dbUserPassword;
	}
}
