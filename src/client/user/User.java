package client.user;

import java.util.Vector;
import server.config.UserInfo;

/**
 * 用户类
 *
 * @author BoluBolu
 */
public final class User extends UserInfo {

	/**
	 * 构造用户类
	 *
	 * @param userId     用户id
	 * @param userName   用户名
	 * @param userAvatar 用户头像
	 * @param userTag    用户个性签名
	 * @param friends    用户好友
	 * @param groups     用户群组
	 */
	public User(String userId, String userName, String userAvatar, String userTag, Vector<FriendsOrGroups> friends,
		Vector<FriendsOrGroups> groups) {
		this.userId = userId;
		this.userName = userName;
		this.userAvatar = userAvatar;
		this.userTag = userTag;
		this.friends = friends;
		this.groups = groups;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public String getUserTag() {
		return userTag;
	}

	public Vector<FriendsOrGroups> getFriends() {
		return friends;
	}

	public Vector<FriendsOrGroups> getGroups() {
		return groups;
	}
}
