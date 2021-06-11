/**
 * 与服务器验证端口的连接，主要用于请求数据或者验证登录信息
 */

package client.client;

import client.user.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import server.config.ServerInfo;
import server.config.Verify;

/**
 * 该类包括服务器交互的各种功能.
 *
 * @author BoluBolu
 */
public final class ServerService {

	/**
	 * 发送对象给服务端，并接受服务端返回的结果.
	 *
	 * @param obj 发送给服务段的对象
	 * @throws ClassNotFoundException 在与服务交互的过程中出现了异常
	 * @throws IOException            在与服务交互的过程中出现了异常
	 */
	private static Object postToServer(Object obj) {
		Object result = null;
		try {
			// 建立连接
			Socket sc = new Socket(ServerInfo.SERVER_IP, ServerInfo.VERIFY_PORT);

			// 创建对象输入输出流
			ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(sc.getInputStream());

			// 写对象到服务端
			out.writeObject(obj);

			// 获取返回信息
			result = in.readObject();

			// 关闭流
			sc.close();
			in.close();
			out.close();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("在与服务器验证交互中出现了异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 发送用户信息，服务器返回该用户是否登录
	 *
	 * @param userId       用户ID
	 * @param userPassword 用户密码
	 * @return 返回服务器处理的结果，即用户是否登录
	 */
	public static Object isLogin(String userId, String userPassword) {
		// 构造身份信息
		Verify userInfo = new Verify(userId, userPassword);

		// 返回服务器产生的结果
		return postToServer(userInfo);
	}

	/**
	 * 发送用户ID，从服务器获取用户相关信息
	 *
	 * @param userId 用户ID
	 * @return 返回从服务器端获得的用户信息
	 */
	public static User getUserInfo(String userId) {
		User userInfo = null;
		String fieldString = "getUserInfo" + userId;
		userInfo = (User)postToServer(fieldString);
		return userInfo;
	}

	/**
	 * 发送两个用户ID，从服务端获取用户聊天记录
	 *
	 * @param fromid  发送消息的用户的ID
	 * @param toId    接受消息的用户的ID
	 * @param isGroup 是否是群
	 * @return 聊天记录
	 */
	@SuppressWarnings("unchecked") public static Vector<String> getChatRecord(String fromid, String toId,
		boolean isGroup) {
		String sendString = "getChatRecord```" + fromid + "```" + toId + "```" + isGroup;
		return (Vector<String>)postToServer(sendString);
	}

	/**
	 * 发送群ID，获取区成员信息
	 *
	 * @param groupId 群ID
	 * @return 返回群成员信息
	 */

	@SuppressWarnings("unchecked") public static Vector<String> getGroupMembers(String groupId) {
		String send = "getGroupMembers" + groupId;
		return (Vector<String>)postToServer(send);
	}

	/**
	 * 发送用户id和更改的签名内容更改签名
	 *
	 * @param myId    用户ID
	 * @param content 更改的标签内容
	 */
	public static void setTag(String myId, String content) {
		postToServer("setTag```" + myId + "```" + content);
	}

	/**
	 * 发送注册请求
	 *
	 * @param username
	 * @param password
	 * @return 注册结果的登录用户名
	 */
	public static int sendRegister(String username, String password) {
		return (int)postToServer("sendRegister```" + username + "```" + password);
	}
}
