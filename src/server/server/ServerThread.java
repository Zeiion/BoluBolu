package server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.config.ServerInfo;

/**
 * 用于建立服务器的ServerSocket
 */
public final class ServerThread implements Runnable {

	private static ServerSocket serverSocket = new ChatServer(ServerInfo.VERIFY_PORT).getServerSocket();

	/**
	 * 等待用户连接，如果连接成功为其单独分配线程处理
	 */
	@Override public void run() {
		try {
			while (true) {
				// 等待用户连接
				Socket userSocket = serverSocket.accept();

				System.out.println(userSocket.getInetAddress() + " 尝试连接");

				// 为用户接入创建一个连接线程，并验证登录是否合法
				new Thread(new ConnectThread(userSocket)).start();
			}
		} catch (IOException e) {
			System.out.println("连接服务器异常：" + e.getMessage());
		}
	}
}
