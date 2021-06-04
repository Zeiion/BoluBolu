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
import server.config.Verify;
import server.config.ServerInfo;

/**
 * 服务器服务
 *
 * @author: Zeiion
 * @version: 1.0
 */
public final class ServerService {

	/**
	 * 发送给服务端
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
	 * 是否登录
	 */
	public static Object isLogin(String userId, String userPassword) {
		// 构造身份信息
		Verify userInfo = new Verify(userId, userPassword);

		// 返回服务器产生的结果
		return postToServer(userInfo);
	}

	/**
	 * 获取用户信息
	 */
	public static User getUserInfo(String userId) {
		User userInfo = null;
		String fieldString = "getUserInfo" + userId;
		userInfo = (User)postToServer(fieldString);
		return userInfo;
	}

	/**
	 * 获取聊天记录
	 */
	@SuppressWarnings("unchecked") public static Vector<String> getChatRecord(String fromid, String toId,
		boolean isGroup) {
		String sendString = "getChatRecord```" + fromid + "```" + toId + "```" + isGroup;
		return (Vector<String>)postToServer(sendString);
	}

	/**
	 * 获取群聊成员
	 */
	@SuppressWarnings("unchecked") public static Vector<String> getGroupMembers(String groupId) {
		String send = "getGroupMembers" + groupId;
		return (Vector<String>)postToServer(send);
	}

	/**
	 * 设置个性签名
	 */
	public static void setTag(String myId, String content) {
		postToServer("setTag```" + myId + "```" + content);
	}
}
