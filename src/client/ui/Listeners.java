package client.ui;

import client.client.ChatThread;
import client.client.ServerService;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * 退出程序的监听器
 */
class ExitListener implements ActionListener {

	@Override public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}

/**
 * 关闭窗口的监听器
 */
class CloseListener implements ActionListener {
	private JFrame tempWindow;
	private String friendID;
	private  Boolean isGroup;

	public CloseListener(JFrame tempWindow, String friendID, Boolean isGroup) {
		this.tempWindow = tempWindow;
		this.friendID = friendID;
		this.isGroup = isGroup;
	}

	@Override public void actionPerformed(ActionEvent e) {
		//不移除的话，关闭聊天框后，无法再打开
		if (!isGroup) {
			MainWindow.getWithFriend().remove(friendID);
		} else {
			MainWindow.getWithGroup().remove(friendID);
		}
		tempWindow.dispose();
	}
}

/**
 * 使得窗口可以移动
 */
class WindowMoveAdapter extends MouseAdapter {
	private int offsetX, offsetY;
	private boolean isCanMove;

	public WindowMoveAdapter() {
		isCanMove = true;
	}

	public void setCanMove(boolean isCanMove) {
		this.isCanMove = isCanMove;
	}

	@Override public void mouseDragged(MouseEvent e) {
		if (isCanMove) {
			SwingUtilities.getRoot((Component)e.getSource())
				.setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen() - offsetY);
		}
	}

	@Override public void mousePressed(MouseEvent e) {
		offsetX = e.getX();
		offsetY = e.getY();
	}
}

/**
 * 登录的监听器 可以获取登录信息并创建用户窗口
 */
class LoginListener implements ActionListener {

	JFrame tempWindow;
	JTextField userId;
	JPasswordField passwd;
	JCheckBox isRememberPassword;
	JCheckBox isAutoLogin;

	public void setTempWindow(JFrame tempWindow) {
		this.tempWindow = tempWindow;
	}

	public void setUserId(JTextField userId) {
		this.userId = userId;
	}

	public void setPasswd(JPasswordField passwd) {
		this.passwd = passwd;
	}

	public void setIsRememberPassword(JCheckBox isRememberPassword) {
		this.isRememberPassword = isRememberPassword;
	}

	public void setIsAutoLogin(JCheckBox isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}

	@Override public void actionPerformed(ActionEvent e) {

		new Thread(new Runnable() {
			@Override public void run() {
				// 获取文本框内容
				String userIdString = userId.getText().trim();
				String userPasswordString = String.valueOf(passwd.getPassword()).trim();
				// 获取登录设置
				boolean isAutoLog = isAutoLogin.isSelected();
				boolean isRemember = isRememberPassword.isSelected();
				if (isAutoLog) {
					isRemember = true;
				}

				// 验证用户或密码是否正确
				Object isLoginSuccess = ServerService.isLogin(userIdString, userPasswordString);

				System.out.println("当前登录状态：" + isLoginSuccess);
				if (isLoginSuccess != null) {
					String loginResult = isLoginSuccess.toString();
					if ("true".equals(loginResult)) {
						if (isRemember) {
							//增加记住密码操作
						}
						tempWindow.dispose();

						// 创建线程接入聊天端口
						new Thread(new ChatThread(userIdString)).start();

						new MainWindow(userIdString);
					} else if ("Repeat".equals(loginResult)) {
						JOptionPane.showMessageDialog(tempWindow, "重复登录");
					} else {
						JOptionPane.showMessageDialog(tempWindow, "您的登陆信息有误", "登陆失败", JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(tempWindow, "与服务器建立连接失败");
				}
			}
		}).start();
	}
}

/**
 * 向好友发送消息
 *
 * @version: 1.0
 */
class Send2FriendListener implements ActionListener {
	private JTextArea message;
	private String userName;
	private String friendID;
	private ChatWindow tempChat;
	private boolean isGroup;

	public Send2FriendListener(String userName, String friendID, boolean isGroup) {
		this.userName = userName;
		this.friendID = friendID;
		this.isGroup = isGroup;
	}

	@Override public void actionPerformed(ActionEvent e) {
		enter();
	}

	/**
	 * 发送消息
	 */
	public void enter() {
		if (this.message.getText().trim().length() != 0) {
			tempChat.addMessage(userName, new Date().toString(), this.message.getText(), false);
			ChatThread.getDataStream().send(this.message.getText(), friendID, isGroup);
			this.message.setText("");
		} else {
			JOptionPane.showMessageDialog(tempChat, "发送消息不能为空，请重新输入");
		}
	}

	public void setMessage(JTextArea message) {
		this.message = message;
	}

	public void setTempChat(ChatWindow tempChat) {
		this.tempChat = tempChat;
	}
}