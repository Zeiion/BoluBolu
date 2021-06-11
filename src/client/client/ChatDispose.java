package client.client;

import client.ui.ChatWindow;
import client.ui.FriendBlock;
import client.ui.MainWindow;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 * 该类主要是用于处理从客户端发送的消息.
 * <p>
 * 包括群消息、好友消息、好友登录、退出状态等
 *
 * @author BoluBolu
 */
public final class ChatDispose {
	/**
	 * 接收到的消息内容
	 */
	private static String message;

	/**
	 * 发送该消息的用户ID，用作显示对方信息
	 */
	private static String fromId;

	/**
	 * 发送目标方ID，主要在群聊天中有效，用作消息应该展示在哪个群里面
	 */
	private static String toId;

	/**
	 * 接收消息内容的标识信息
	 */
	private static String type;

	/**
	 * 开始处理服务端发送已封装的消息内容.
	 *
	 * @param:scMessage 服务端发送的消息内容
	 */
	public static void execute(String scMessage) {
		// 对接收到的消息内容进行解码
		String[] res = scMessage.split("```", 5);

		// 从服务端发送的内容解码之后长度为5，代表该消息为聊天内容
		if (res.length == 5) {
			type = res[1];
			fromId = res[2];
			toId = res[3];
			message = res[4];

			// 以ID为键，对应聊天面板为值的哈希映射
			HashMap<String, ChatWindow> model;

			// 接收到的消息是从好友发送来的
			if (type.equals("toFriend")) {

				model = MainWindow.getFriendChat();

				// 展示在对应好友聊天面板中
				if (model.containsKey(fromId)) {
					model.get(fromId)
						.addMessage(MainWindow.getFriend().get(fromId).getFriendName(), res[0], message, false, false);
				}
			}
			// 接收到的消息是从某个群发送来的
			else if (type.equals("toGroup")) {
				model = MainWindow.getGroupChat();
				if (model.containsKey(toId)) {
					// 聊天面板显示用户昵称
					String fromString =
						MainWindow.getFriend().containsKey(fromId) ? MainWindow.getFriend().get(fromId).getFriendName()
							: ("陌生人:" + fromId);
					model.get(toId).addMessage(fromString, res[0], message, false, false);
				}
			}
		} // 接收的内容是为了改变用户状态（在线/离线）
		else if (res.length == 3) {
			/** res[0]:验证标识、res[1]:状态信息、res[2]:好友ID */
			if (res[0].equals("OnlineSituation")) {
				if (MainWindow.getFriend().containsKey(res[2])) {
					FriendBlock f = MainWindow.getFriend().get(res[2]);
					f.setFriendStatus(res[1]);
					if (res[1].equals("在线")) {
						f.getFriendStatusLabel().setFont(new Font("黑体", Font.BOLD, 13));
						for (int i = 0; i < MainWindow.getFriendPanel().getComponentCount(); i++) {
							FriendBlock fb = (FriendBlock)MainWindow.getFriendPanel().getComponent(i);
							if (fb.getFriendStatus().equals("离线")) {
								Rectangle rfb = fb.getBounds();
								Rectangle rf = f.getBounds();
								f.setBounds(rfb);
								fb.setBounds(rf);
								MainWindow.getFriendPanel().updateUI();
								break;
							}
						}
					} else if (res[1].equals("离线")) {
						f.getFriendStatusLabel().setFont(new Font("黑体", Font.PLAIN, 13));
						for (int i = MainWindow.getFriendPanel().getComponentCount() - 1; i >= 0; i--) {
							FriendBlock fb = (FriendBlock)MainWindow.getFriendPanel().getComponent(i);
							if (fb.getFriendStatus().equals("在线")) {
								Rectangle rfb = fb.getBounds();
								Rectangle rf = f.getBounds();
								f.setBounds(rfb);
								fb.setBounds(rf);
								MainWindow.getFriendPanel().updateUI();
								break;
							}
						}
					}
				}
			}
		}
	}
}
