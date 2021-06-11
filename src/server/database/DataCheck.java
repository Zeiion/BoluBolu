package server.database;

import client.user.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import server.config.UserInfo;
import server.config.UserInfo.FriendsOrGroups;
import server.server.ChatServer;

/**
 * 核对数据.
 * <p>
 * 本类根据项目所需实现的功能,进行对应的sql语句生成,并通过DataHBaseConnection类实现对数据库的操作.
 *
 * @author BoluBolu
 */
public final class DataCheck {

	/**
	 * 查询用户是否存在.
	 * <p>
	 * 通过给定的用户账号,查询数据库中是否存在账号对应的用户.
	 *
	 * @param userId 用户账号
	 * @return 如果对应用户存在, 返回true, 查询失败或对应用户不存在则返回false
	 */
	public boolean checkRegister(String userId) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_user where user_id = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, userId);
			return dataCon.psql.executeQuery().next();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 查询对应用户名是否存在
	 * <p>
	 * 本方法通过给定的用户名,查询数据库中是否存在用户名对应的用户.
	 *
	 * @param name 用户名
	 * @return 存在对应的用户返回true, 不存在对应的用户或查询失败返回false.
	 */
	public boolean checkName(String name) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_user where user_name = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, name);
			return dataCon.psql.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 查询并返回用户.
	 * <p>
	 * 本方法通过给定的用户id,查询数据库中是否存在id对应的用户,并以FriendsOrGroups的形势返回.
	 *
	 * @param userId 用户id
	 * @return 如果用户不存在或查询失败返回null, 否则返回用户对应的FriendsOrGroups对象.
	 */
	public FriendsOrGroups checkUser(String userId) {
		try {
			String id;
			String name;
			String avatar;
			String tag;
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_user where user_id = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, userId);
			ResultSet resultSet = dataCon.psql.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			id = resultSet.getString("user_id");
			name = resultSet.getString("user_name");
			if (resultSet.getString("user_avatar") == null) {
				avatar = "";
			} else {
				avatar = resultSet.getString("user_avatar");
			}
			if (resultSet.getString("user_tag") == null) {
				tag = "";
			} else {
				tag = resultSet.getString("user_tag");
			}
			FriendsOrGroups friendsOrGroups = new FriendsOrGroups(id, name, avatar, tag, null);
			return friendsOrGroups;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 验证用户身份信息.
	 * <p>
	 * 根据给定的用户id和密码,查询是否存在相符的用户.
	 *
	 * @param userId       用户id
	 * @param userPassword 用户密码
	 * @return 存在相符的用户返回true, 不存在或查询失败返回false
	 */
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

	/**
	 * 用户注册.
	 * <p>
	 * 根据给出的用户名与密码,在数据库中插入新用户信息,自动为用户分配主键自增的id,设置初始个性签名为"".
	 *
	 * @param username     用户名
	 * @param userPassword 密码
	 * @return 返回当前总注册用户数量.
	 */
	public int register(String username, String userPassword) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "insert into dw_user(user_name,user_password,user_tag) values (?,?,?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, username);
			dataCon.psql.setString(2, userPassword);
			dataCon.psql.setString(3, "");
			dataCon.psql.executeUpdate();
			String sql1 = "select count(user_id) as count from dw_user";
			dataCon.psql = dataCon.conn.prepareStatement(sql1);
			ResultSet resultSet = dataCon.psql.executeQuery();
			if (!resultSet.next()) {
				return 1;
			}
			return Integer.parseInt(resultSet.getString("count"));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 查询好友关系是否存在.
	 * <p>
	 * 根据所给的用户id及好友id,查询对应的好友关系是否存在.
	 *
	 * @param userId   用户id
	 * @param friendId 好友id
	 * @return 存在返回true, 不存在或查询失败返回false.
	 */
	public boolean checkFriend(String userId, String friendId) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select * from dw_useruser where myself = (?) and myfriend = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, userId);
			dataCon.psql.setString(2, friendId);
			return dataCon.psql.executeQuery().next();
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * 加好友操作.
	 * <p>
	 * 根据所给的用户id及好友id,双向添加好友,即插入一条(我的id,好友id),再插入一条(好友id,我的id).
	 *
	 * @param userId   我的id
	 * @param friendId 好友id
	 * @return 插入成功返回true, 插入失败或异常返回false.
	 */
	public boolean addFriend(String userId, String friendId) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "insert into dw_useruser values(?,?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, userId);
			dataCon.psql.setString(2, friendId);
			dataCon.psql.executeUpdate();
			String sql1 = "insert into dw_useruser values(?,?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql1);
			dataCon.psql.setString(1, friendId);
			dataCon.psql.setString(2, userId);
			dataCon.psql.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 找回密码.
	 * <p>
	 * 根据给出的用户id,返回对应的用户密码.
	 *
	 * @param userId 用户id
	 * @return 查询成功返回对应密码, 用户不存在或查询异常返回false.
	 */
	public String getPassword(String userId) {
		try {
			DataBaseConnection dataCon = new DataBaseConnection();
			String sql = "select user_password from dw_user where user_id = (?) or user_name = (?)";
			dataCon.psql = dataCon.conn.prepareStatement(sql);
			dataCon.psql.setString(1, userId);
			dataCon.psql.setString(2, userId);
			ResultSet resultSet = dataCon.psql.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			return resultSet.getString(1);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询成员id列表
	 * <p>
	 * 根据所给sql语句与所需属性返回对应属性列表.
	 *
	 * @param sql sql语句
	 * @param row 所需属性
	 * @return 返回查询到的属性列表.
	 */
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
	 * 获取好友成员.
	 * <p>
	 * 根据所给用户id,通过所给数据库连接查找数据库中该用户的好友账号列表.
	 *
	 * @param selfID 用户id.
	 * @return 返回查询到的好友账号列表.
	 */
	public static Vector<String> getFriendMember(String selfID) {
		String sqlString = "select myfriend from dw_useruser where myself = " + selfID;
		return getMemberFromId(sqlString, "myfriend");
	}

	/**
	 * 获取群聊成员.
	 * <p>
	 * 根据所给群聊id,通过所给数据库连接查找数据库中该群的群成员.
	 *
	 * @param groupId 群聊id.
	 * @return 群聊成员账号列表.
	 */
	public static Vector<String> getGroupMember(String groupId) {
		String sqlString = "select user_id from view_usergroup where group_id = " + groupId;
		return getMemberFromId(sqlString, "user_id");
	}

	/**
	 * 获取用户好友信息.
	 * <p>
	 * 根据所给用户id,通过所给数据库连接查找数据库中该用户的好友信息.
	 *
	 * @param userId  用户id
	 * @param dataCon 数据库连接
	 * @return 返回对应用户的好友信息.
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
	 * 获取用户所参加的群.
	 * <p>
	 * 根据所给用户id,通过所给数据库连接查找数据库中该用户加入的群.
	 *
	 * @param userId  用户id
	 * @param dataCon 数据库连接
	 * @return 查询到的群列表.
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
	 * 获取用户信息.
	 * <p>
	 * 根据所给userid信息,返回对应用户的群聊,好友等信息.
	 *
	 * @param userId 所给用户id.
	 * @return 返回userid对应的用户对象.
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
	 * 获取聊天记录.
	 * <p>
	 * 查询对应两个id之间的聊天记录或群聊的聊天记录并返回.
	 *
	 * @param fromId  消息发出者
	 * @param toId    消息接收者
	 * @param isGroup 是否是群聊
	 * @return 查询到的消息记录列表.
	 */
	public Vector<String> getChatRecord(String fromId, String toId, String isGroup) {
		Vector<String> all = new Vector<String>();

		// 创建数据库连接对象
		DataBaseConnection dataCon = new DataBaseConnection();
		String sqlString = "";
		if ("true".equals(isGroup)) {
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
