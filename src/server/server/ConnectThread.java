package server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import server.config.Verify;
import server.database.DataBaseConnection;
import server.database.DataCheck;

/**
 * 用于连接客户端和服务端 可以验证是否为合法连接
 */
public final class ConnectThread implements Runnable {

	private Socket userSocket;

	public ConnectThread(Socket userSocket) {
		this.userSocket = userSocket;
	}

	/**
	 * 选择当前处理的事件类型并执行不同操作
	 */
	public Object switchCon(Object obj) {

		// 创建 Data_Check 来完成这些工作
		DataCheck check = new DataCheck();

		// 获取对象类型
		String objName = obj.getClass().getSimpleName();

		// 返回结果对象
		Object result = null;
		System.out.println("当前处理对象类型为 " + objName);
		switch (objName) {
			// 登录验证
			case "Verify":
				Verify loginVerify = (Verify)obj;

				if (ChatServer.getClientUser().containsKey(loginVerify.getUserId())) // 该用户已登录
				{
					result = "Repeat";
				} else {
					result = check.isLoginSuccess(loginVerify.getUserId(), loginVerify.getUserPassword());

					// 为登录成功的用户创建聊天线程
					if (result != null && (Boolean)result == true) {
						System.out.println("登录成功，为该用户创建一个聊天线程");
						new Thread(new ChatThread(loginVerify.getUserId())).start();
					}
				}
				break;
			case "String":
				// 获取字符串内容
				String field = obj.toString();
				// 如果为获取信息
				if (field.startsWith("getUserInfo")) {
					field = field.replace("getUserInfo", "");
					result = check.getUserInfo(field);
				} else if (field.startsWith("getChatRecord")) {

					// 获取聊天记录
					String res[] = field.split("```", 4);
					if (res.length == 4) {
						/**
						 * res[0]：getChatRecord、res[1]：fromId、res[2]：toId、
						 * res[3]：isGroup
						 */
						result = check.getChatRecord(res[1], res[2], res[3]);
					}
				} else if (field.startsWith("getGroupMembers")) {
					field = field.replace("getGroupMembers", "");
					result = DataCheck.getGroupMember(field);
				} else if (field.startsWith("setTag")) {
					// 替换前缀
					String res[] = field.split("```", 3);
					if (res.length == 3) {
						/**
						 * res[0]：setTag、res[1]：myId、res[2]：newTag
						 */
						DataBaseConnection con = new DataBaseConnection();
						String sql = "UPDATE dw_user SET user_tag = '" + res[2] + "' WHERE user_id = " + res[1];
						con.putToDatabase(sql);
						con.close();
					}
				}
				break;
			default:
				break;
		}
		return result;
	}

	@Override public void run() {
		try {
			// 对象输入输出流

			while (true) {
				ObjectInputStream in = new ObjectInputStream(userSocket.getInputStream());
				ObjectOutputStream out = new ObjectOutputStream(userSocket.getOutputStream());
				// 接收一个对象流
				Object obj = in.readObject();

				// 获取处理结果
				Object result = switchCon(obj);

				// 返回给客户端这个处理结果
				out.writeObject(result);

				// 关闭所有流
				in.close();
				userSocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("与服务器失去联系 ：" + e.getMessage());
		}
	}
}
