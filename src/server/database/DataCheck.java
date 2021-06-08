package server.database;

import client.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import server.config.UserInfo;
import server.config.UserInfo.FriendsOrGroups;
import server.server.ChatServer;

/**
 * 核对数据
 */
public final class DataCheck {

	public boolean checkRegister(String userId){
		try{
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_user where user_id = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,userId);
			return dataCon.psql.executeQuery().next();
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public FriendsOrGroups checkUser(String userId){
		try{
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_user where user_id = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,userId);
			ResultSet resultSet = dataCon.psql.executeQuery();
			if (!resultSet.next()){
				return null;
			}
			return (UserInfo.FriendsOrGroups) resultSet.getObject(1);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public Boolean isLoginSuccess(String userId, String userPassword) {
		Boolean isSuccess = new Boolean(false);
		try {
			// 查询该用户是否存在
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql =
				"select * from dw_user where user_id = " + userId + " and user_password = '" + userPassword + "'";

			// 如果存在该用户，返回true
			isSuccess = dataCon.getFromDatabase(sql).next();

			// 关闭与服务器连接对象
			dataCon.close();
		} catch (SQLException e) {
			System.out.println("身份验证信息查询失败:" + e.getMessage());
		}
		return isSuccess;
	}

	public boolean register(String username, String userPassword){
		try{

			DataBaseConnection dataCon = new DataBaseConnection();
			String sql ="insert into dw_user(user_name,user_password) values (?,?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,username);
			dataCon.psql.setString(2,userPassword);
			dataCon.psql.executeUpdate();
			//dataCon.putToDatabase(sql);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean checkFriend(String userId,String friendId){
		try{
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_useruser where myself = (?) and myfriend = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,userId);
			dataCon.psql.setString(2,friendId);
			return dataCon.psql.executeQuery().next();
		}catch (Exception e){
			return true;
		}
	}
	public boolean addFriend(String userId,String friendId){
		try{
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "insert into dw_useruser values(?,?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,userId);
			dataCon.psql.setString(2,friendId);
			dataCon.psql.executeUpdate();
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	public String getPassword(String userId){
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select user_password from dw_user where user_id = (?) or user_name = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1,userId);
			dataCon.psql.setString(2,userId);
			ResultSet resultSet = dataCon.psql.executeQuery();
			if (!resultSet.next()){
				return null;
			}
			return resultSet.getString(1);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}



	private static Vector<String> getMemberFromId(String sql, String row) {
		// 与数据库创建连接
		DataBaseConnection dataCon = new DataBaseConnection();

		// 最终结果Vector数组
		Vector<String> member = new Vector<>();

		// 利用该sql语句查询，返回ResultSet结果集
		ResultSet resultSet = dataCon.getFromDatabase(sql);
		try {
			while (resultSet.next()) {
				member.add(resultSet.getString(row));
			}
			// 关闭连接
			dataCon.close();
		} catch (SQLException e) {
			System.out.println("查询成员ID列表失败：" + e.getMessage());
		}
		return member;
	}

	/**
	 * 获取好友成员
	 */
	public static Vector<String> getFriendMember(String selfID) {
		String sqlString = "select myfriend from dw_useruser where myself = " + selfID;
		return getMemberFromId(sqlString, "myfriend");
	}

	/**
	 * 获取群成员
	 */
	public static Vector<String> getGroupMember(String groupId) {
		String sqlString = "select user_id from dw_usergroup where group_id = " + groupId;
		return getMemberFromId(sqlString, "user_id");
	}

	/**
	 * 获取好友信息
	 */
	public Vector<FriendsOrGroups> getUserFriends(String userId, DataBaseConnection dataCon) {
		Vector<FriendsOrGroups> friends = new Vector<>();

		// 查询好友信息
		String sqlString = "select * from view_useruser where myself = " + userId;
		ResultSet resultSet = dataCon.getFromDatabase(sqlString);
		try {
			while (resultSet.next()) {
				String friendID = resultSet.getString("myfriend");
				String friendName = resultSet.getString("user_name");
				String friendAvatar = resultSet.getString("user_avatar");
				String friendTag = resultSet.getString("user_tag");
				String friendStatus = ChatServer.getClientUser().containsKey(friendID) ? "在线" : "离线";
				friends.add(new FriendsOrGroups(friendID, friendName, friendAvatar, friendTag, friendStatus));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("获取好友信息失败 " + e.getMessage());
		}
		return friends;
	}

	/**
	 * 获取群信息
	 */
	public Vector<FriendsOrGroups> getUserGroups(String userId, DataBaseConnection dataCon) {
		Vector<FriendsOrGroups> groups = new Vector<FriendsOrGroups>();

		// 查询群信息
		String sqlString = "select * from view_usergroup where user_id = " + userId;
		ResultSet resultSet = dataCon.getFromDatabase(sqlString);
		try {
			while (resultSet.next()) {
				String gId = resultSet.getString("group_id");
				String gName = resultSet.getString("group_name");
				String gTag = resultSet.getString("group_tag");
				String gAvatar = resultSet.getString("group_avatar");
				String gStatus = resultSet.getString("user_id");
				groups.add(new FriendsOrGroups(gId, gName, gAvatar, gTag, gStatus));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("DataCheck 获取好友群失败 " + e.getMessage());
		}
		return groups;
	}

	/**
	 * 获取用户信息(个人资料 群 好友)
	 */
	public UserInfo getUserInfo(String userId) {
		// 用户个人信息
		String userName = "";
		String userAvatar = "";
		String userTag = "";
		Vector<FriendsOrGroups> friends;
		Vector<FriendsOrGroups> groups;
		UserInfo userInfo = null;

		try {
			// 创建数据库连接
			DataBaseConnection dataCon = new DataBaseConnection();

			// 查询个人信息
			String sqlString = "select * from dw_user where user_id = " + userId;
			ResultSet resultSet = dataCon.getFromDatabase(sqlString);
			while (resultSet.next()) {
				userName = resultSet.getString("user_name");
				userAvatar = resultSet.getString("user_avatar");
				userTag = resultSet.getString("user_tag");
			}
			resultSet.close();

			// 查询好友列表信息与群列表信息
			friends = getUserFriends(userId, dataCon);
			groups = getUserGroups(userId, dataCon);

			// 关闭数据库连接
			dataCon.close();
			// 创建对象
			userInfo = new User(userId, userName, userAvatar, userTag, friends, groups);
		} catch (SQLException e) {
			System.out.println("获取用户信息失败：" + e.getMessage());
		}
		return userInfo;
	}

	/**
	 * 获取聊天记录
	 */
	public Vector<String> getChatRecord(String fromId, String toId, String isGroup) {
		Vector<String> all = new Vector<String>();

		// 创建数据库连接对象
		DataBaseConnection dataCon = new DataBaseConnection();
		String sqlString = "";
		if (isGroup.equals("true")) {
			sqlString =
				"select gchat_uid fromid,gchat_gid toid,gchat_message message,gchat_datetime timer from dw_groupchat where gchat_gid = "
					+ toId;
		} else {
			sqlString =
				"select uchat_fromid fromid,uchat_toid toid,uchat_message message,uchat_datetime timer from dw_userchat where (uchat_fromid ="
					+ fromId + " and uchat_toid = " + toId + ") or (uchat_fromid = " + toId + " and uchat_toid = "
					+ fromId + ")";
		}
		ResultSet resultSet = dataCon.getFromDatabase(sqlString);
		try {
			String tmp = "";
			while (resultSet.next()) {
				tmp = resultSet.getString("timer") + "```";
				tmp += resultSet.getString("fromid") + "```";
				tmp += resultSet.getString("toid") + "```";
				tmp += resultSet.getString("message");
				all.add(tmp);
			}

			// 关闭连接
			dataCon.close();
		} catch (SQLException e) {
			System.out.println("获取聊天记录信息失败：" + e.getMessage());
		}
		return all;
	}
}
