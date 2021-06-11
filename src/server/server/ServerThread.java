package server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import server.config.ServerInfo;

/**
 * 建立服务器的 server socket.
 * <p>
 * 定义服务器类实现需要的socket等信息,并动态接收用户连接.
 *
 * @author BoluBolu
 */
public final class ServerThread implements Runnable {

	private static ServerSocket serverSocket = new ChatServer(ServerInfo.VERIFY_PORT).getServerSocket();

	/**
	 * 接受用户连接.
	 * <p>
	 * 等待用户连接，如果连接成功为其单独分配线程处理.
	 */
	@Override public void run() {
		try {
			while (true) {
				// 等待用户连接
				Socket userSocket = serverSocket.accept();

				System.out.println("尝试连接 " + userSocket.getInetAddress());

				// 为用户接入创建一个连接线程，并验证登录是否合法
				new Thread(new ConnectThread(userSocket)).start();
			}
		} catch (IOException e) {
			System.out.println("连接服务器异常：" + e.getMessage());
		}
	}
}
