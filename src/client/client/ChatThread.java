package client.client;

import java.io.IOException;
import java.net.Socket;
import server.config.ServerInfo;

/**
 * 该类用于用户登录时创建线程连接到服务器端口.
 *
 * @author BoluBolu
 */

public final class ChatThread implements Runnable {
	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 聊天数据流传输对象，主要通过它向服务端发送消息或者接收消息
	 */
	private static DataStream dataStream;

	/**
	 * 类的构造方法
	 *
	 * @param userId
	 */
	public ChatThread(String userId) {
		this.userId = userId;
	}

	/**
	 * 创建与服务器聊天端口的通讯，并创建线程开始(接收/发送)数据
	 *
	 * @Throws: IOException 在创建与服务端的连接时出错
	 */
	@Override public void run() {
		Socket myHost = null;
		try {
			// 创建与服务端的连接
			myHost = new Socket(ServerInfo.SERVER_IP, ServerInfo.CHAT_PORT);

			// 聊天数据信息流，用来接收或者发送信息
			dataStream = new DataStream(myHost, userId);
			new Thread(dataStream).start();
		} catch (IOException e) {
			System.out.println("创建与服务端的连接出错：" + e.getMessage());
		}
	}

	/**
	 * 返回该对象的属性: dataStream 聊天数据传输对象
	 *
	 * @return dataStream 对象的属性，聊天数据传输对象
	 */
	public static DataStream getDataStream() {
		return dataStream;
	}
}
