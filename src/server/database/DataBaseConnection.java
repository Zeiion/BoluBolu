package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.config.ServerInfo;

/**
 * 连接数据库
 */
public final class DataBaseConnection {
	/**
	 * 数据库连接对象
	 */
	private Connection conn = null;

	private PreparedStatement psql = null;

	/**
	 * 数据库结果
	 */
	private ResultSet resultSet = null;

	/**
	 * 连接数据库
	 */
	public DataBaseConnection() {
		// 数据库驱动名
		String dbDriver = "com.mysql.cj.jdbc.Driver";

		// 数据库所在域
		String dbUrl = "jdbc:mysql://" + ServerInfo.MYSQL_IP + ":" + ServerInfo.MYSQL_PORT + "/" + ServerInfo.DB_NAME
			+ "?useUnicode=true&characterEncoding=UTF-8";

		try {
			// 加载驱动
			Class.forName(dbDriver);
			// 获取连接对象
			conn = DriverManager.getConnection(dbUrl, ServerInfo.DB_USER_NAME, ServerInfo.DB_USER_PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("无法连接到数据库：" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据库
	 */
	public ResultSet getFromDatabase(String sql) {
		try {
			// 创建对象参数化 sql
			psql = conn.prepareStatement(sql);

			// 开始查询
			resultSet = psql.executeQuery();
		} catch (SQLException e) {
			System.out.println("数据库查询失败：" + e.getMessage());
		}
		return resultSet;
	}

	/**
	 * 更新数据库
	 */
	public void putToDatabase(String sql) {
		try {
			psql = conn.prepareStatement(sql);
			psql.executeUpdate();
		} catch (SQLException e) {
			System.out.println("更新数据库异常：" + e.getMessage());
		}
	}

	/**
	 * 关闭所有打开的连接
	 */
	public void close() {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
		} catch (SQLException e) {
			System.out.println("结果集关闭异常：" + e.getMessage());
		}
		try {
			if (psql != null && !psql.isClosed()) {
				psql.close();
			}
		} catch (SQLException e) {
			System.out.println("更新集关闭异常：" + e.getMessage());
		}
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("数据库连接关闭异常：" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new DataBaseConnection();
	}
}
