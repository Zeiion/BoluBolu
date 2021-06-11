package server.config;

import java.io.Serializable;
import java.util.Vector;

/**
 * 用户信息可序列化类.
 * <p>
 * 用户类,定义了用户的一些信息,例如用户id,用户名,密码,以及群聊和好友列表.
 *
 * @author BoluBolu
 */
public class UserInfo implements Serializable {

	/**
	 * 用户 ID
	 */
	protected String userId;

	/**
	 * 用户昵称
	 */
	protected String userName;

	/**
	 * 用户头像
	 */
	protected String userAvatar;

	/**
	 * 用户个性签名
	 */
	protected String userTag;

	/**
	 * 用户好友列表
	 */
	protected Vector<FriendsOrGroups> friends = new Vector<>();

	/**
	 * 用户群列表
	 */
	protected Vector<FriendsOrGroups> groups = new Vector<>();

	/**
	 * 群聊或好友对象.
	 * <p>
	 * 对群或者一个好友所记录的信息是一个FriendsOrGroups对象，对象中包含它的id,name,avatar,tag,status.
	 *
	 * @author BoluBolu
	 */
	public static class FriendsOrGroups implements Serializable {

		private String id;
		private String name;
		private String avatar;
		private String tag;
		private String status;

		/**
		 * 创建一个群信息对象或好友信息对象.
		 * <p>
		 * 本方法是类的构造方法,根据所给参数定义类的属性
		 */
		public FriendsOrGroups(String id, String name, String avatar, String tag, String status) {
			this.id = id;
			this.name = name;
			this.avatar = avatar;
			this.tag = tag;
			this.status = status;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getAvatar() {
			return avatar;
		}

		public String getTag() {
			return tag;
		}

		public String getStatus() {
			return status;
		}
	}
}
