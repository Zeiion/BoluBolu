package server.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Vector;
import server.database.DataBaseConnection;
import server.database.DataCheck;

/**
 * 客户成功登录之后会接入聊天端口，在这里主要处理客户端发送的消息，并将其转发给目标用户或群
 */
public final class DataStream implements Runnable {
	/**
	 * 数据输入流
	 */
	private DataInputStream in;

	/**
	 * 数据输出流
	 */
	private DataOutputStream out;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 当前连接用户好友列表
	 */
	private Vector<String> friends;

	/**
	 * 为当前连接用户所创建的数据库连接对象
	 */
	private DataBaseConnection con;

	/**
	 * 利用与客户端连接的Socket对象创建数据输入输出流
	 */
	public DataStream(Socket clientSocket, String userId) {
		this.userId = userId;
		con = new DataBaseConnection();
		friends = DataCheck.getFriendMember(userId);
		try {
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("创建聊天数据传输流失败：" + e.getMessage());
		}
		sendToAllFriends("OnlineSituation```在线```" + userId);
	}

	@Override public void run() {
		String scMessage;
		try {
			while (true) {
				scMessage = in.readUTF();
				execute(scMessage);
			}
		} catch (IOException e) {
			// 离线后删除用户在线信息
			ChatServer.getClientUser().remove(userId);

			// 通知所有好友离线情况
			sendToAllFriends("OnlineSituation```离线```" + userId);
			System.out.println("删除了key为" + userId + "的hashmap值，剩余在线人数 ：" + ChatServer.getClientUser().size());

			// 关闭为该用户创建的数据库连接
			con.close();
		}
	}

	/**
	 * 处理客户端所发送来的信息，将它转发给其他用户或群
	 */
	private void execute(String message) {
		String[] res = message.split("```", 4);
		if (res.length == 4) {
			/*
			 * res[0]：发送标识 、res[1]：fromId 、res[2]：toId 、res[3]：消息内容
			 */
			String type = res[0];
			String toId = res[2];
			message = new Date() + "```" + message;
			if (type.equals("toFriend")) {
				if (ChatServer.getClientUser().containsKey(toId)) {
					ChatServer.getClientUser().get(toId).send(message);
				} else {
					// 好友不在线的情况
				}
				printToDatabase(res[1], res[2], res[3], false);
			} else if (type.equals("toGroup")) {
				Vector<String> groups = DataCheck.getGroupMember(toId);
				for (int i = 0; i < groups.size(); i++) {
					if (groups.get(i).equals(userId) == false && ChatServer.getClientUser()
						.containsKey(groups.get(i))) {
						ChatServer.getClientUser().get(groups.get(i)).send(message);
					} else {
						// 群成员不在线情况
					}
				}
				printToDatabase(res[1], res[2], res[3], true);
			}
		} else {
			System.out.println("收到的信息不规范：" + message);
		}
	}

	/**
	 * 通知该用户的所有好友(一般为上线信息或离线信息)
	 */
	private void sendToAllFriends(String message) {
		for (int i = 0; i < friends.size(); i++) {
			if (ChatServer.getClientUser().containsKey(friends.get(i))) {
				ChatServer.getClientUser().get(friends.get(i)).send(message);
			}
		}
	}

	/**
	 * 发送消息到连接的客户端
	 */
	private void send(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			System.out.println(userId + "发送到客户端失败：" + e.getMessage());
		}
	}

	/**
	 * 聊天记录写入数据库
	 */
	private void printToDatabase(String fromId, String toId, String message, boolean isGroup) {
		String sql;
		if (isGroup == false) {
			sql = "insert into dw_userchat (uchat_fromid,uchat_toid,uchat_message) values(" + fromId + "," + toId + ",'"
				+ message + "')";
		} else {
			sql = "insert into dw_groupchat (gchat_uid,gchat_gid,gchat_message) VALUES (" + fromId + "," + toId + ",'"
				+ message + "')";
		}
		con.putToDatabase(sql);
	}
}
