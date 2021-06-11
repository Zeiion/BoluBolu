package server.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashMap;

/**
 * 聊天服务类.
 * <p>
 * 定义实现聊天功能所需的必要属性,如服务器socket对象,本地连接信息,端口等.
 *
 * @author BoluBolu.
 */
public final class ChatServer {

	/**
	 * 服务器Socket对象
	 */
	private ServerSocket serverSocket;

	/**
	 * 本地信息
	 */
	private InetAddress localHostAddress;

	/**
	 * 监听端口
	 */
	private int serverPort;

	/**
	 * 用户ID与其连接对象数据流之间的哈希映射
	 */
	private static HashMap<String, DataStream> clientUser = new HashMap<String, DataStream>();

	/**
	 * 构造方法.
	 * <p>
	 * 根据给定端口定义聊天服务类的属性.
	 *
	 * @param port 端口
	 */
	public ChatServer(int port) {
		try {
			// 监听端口
			serverPort = port;

			// 创建监听端口的ServerSocket对象
			serverSocket = new ServerSocket(serverPort);

			// 获取本机地址
			localHostAddress = serverSocket.getInetAddress();

		} catch (IOException e) {
			System.out.println("错误信息 ：" + e.getMessage());
		}
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getLocalHostName() {
		return localHostAddress.getHostName();
	}

	public String getLocalHostAddress() {
		return localHostAddress.getHostAddress();
	}

	public int getClientNum() {
		return clientUser.size();
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public static HashMap<String, DataStream> getClientUser() {
		return clientUser;
	}
}