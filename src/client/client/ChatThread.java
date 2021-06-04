package client.client;

import java.io.IOException;
import java.net.Socket;
import server.config.ServerInfo;

/**
 * 连接到服务器聊天端口
 */

public final class ChatThread implements Runnable {
	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 聊天数据流传输对象，主要通过它发送消息或者接收消息
	 */
	private static DataStream dataStream;


	public ChatThread(String userId) {
		this.userId = userId;
	}

	/**
	 * 创建与服务器聊天端口的通讯，并创建线程开始(接收/发送)数据
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

	public static DataStream getDataStream() {
		return dataStream;
	}
}
