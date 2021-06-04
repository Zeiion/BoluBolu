package server.server;

/**
 * 服务端入口
 */
public class Start {
	public static void main(String[] args) {
		new Thread(new ServerThread()).start();
		System.out.println("当前在线人数：0");
	}
}
