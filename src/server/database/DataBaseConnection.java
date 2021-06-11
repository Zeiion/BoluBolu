package server.database;

import static server.config.ServerInfo.DB_NAME;
import static server.config.ServerInfo.DB_USER_NAME;
import static server.config.ServerInfo.DB_USER_PASSWORD;
import static server.config.ServerInfo.SQL_PORT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 连接数据库.
 * <p>
 * 这个类定义并实现了一系列与数据库有关的操作,包括数据库连接,查询,打开与关闭.
 *
 * @author BoluBolu
 */
public final class DataBaseConnection {
	/**
	 * 数据库连接对象
	 */
	Connection conn = null;

	PreparedStatement psql = null;

	/**
	 * 数据库结果
	 */
	private ResultSet resultSet = null;

	/**
	 * 连接数据库.
	 * <p>
	 * 本方法根据数据库驱动名与数据库所在域对指定数据库进行连接.
	 *
	 * @throws SQLException           连接数据库失败时,抛出SQLException或ClassNotFoundException错误
	 * @throws ClassNotFoundException 连接数据库失败时,抛出SQLException或ClassNotFoundException错误
	 */
	public DataBaseConnection() {
		// 数据库驱动名
		String dbDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

		// 数据库所在域
		String dbUrl = "jdbc:sqlserver://localhost:" + SQL_PORT + ";databaseName=" + DB_NAME;

		try {
			// 加载驱动
			Class.forName(dbDriver);
			// 获取连接对象
			conn = DriverManager.getConnection(dbUrl, DB_USER_NAME, DB_USER_PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("无法连接到数据库：" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 查询数据库.
	 * <p>
	 * 本方法根据所给的sql语句,随数据库进行查询操作,返回查询到的结果.
	 *
	 * @param sql 进行查询操作所需的sql语句.
	 * @return 以Resultset的形式返回查询结果
	 * @throws SQLException 当数据库查询失败时,抛出SQLException异常
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
	 * 更新数据库.
	 * <p>
	 * 本方法根据所给的sql语句,随数据库进行更新操作.
	 *
	 * @param sql 进行查询操作所需的sql语句.
	 * @throws SQLException 当数据库查询失败时,抛出SQLException异常
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
	 * 关闭所有打开的连接.
	 * <p>
	 * 本方法关闭所有的数据库连接,包括结果集,更新集的关闭与数据库连接的关闭
	 *
	 * @throws SQLException 当结果集,更新集或数据库连接关闭异常时,抛出SQLException异常
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
}
