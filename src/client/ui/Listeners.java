package client.ui;

import client.client.ChatThread;
import client.client.ServerService;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
 *
 * @author BoluBolu
 */
class ExitListener implements ActionListener {
	@Override public void actionPerformed(ActionEvent e) {
		Object option[] = {"退出", "取消"};
		int n = JOptionPane
			.showOptionDialog(null, "确定要退出吗?", "警告", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				option, option[0]);
		if (n == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

}

/**
 * 关闭窗口的监听器
 *
 * @author BoluBolu
 */
class CloseListener implements ActionListener {
	/**
	 * 窗口对象，窗口对应的好友或者群ID，是否是群
	 */
	private JFrame tempWindow;
	private String friendID;
	private Boolean isGroup;

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
 *
 * @athor BoluBolu
 */
class WindowMoveAdapter extends MouseAdapter {
	/**
	 * 移动的坐标以及是否可以移动
	 */
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
 *
 * @authpr BoluBolu
 */
class LoginListener implements ActionListener {
	/**
	 * 窗口对象 用户ID 用户密码 是否记住了密码 是否自动登录
	 */
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

	/**
	 * @param e
	 */
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

				try {
					System.out.println("当前尝试登录的用户账号：" + userIdString);
					System.out.println(
						"当前登录状态：" + (isLoginSuccess.toString().equals("true") ? "成功─=≡Σ(((つ•̀ω•́)つ" : "失败o(ﾟДﾟ)っ！"));
					if (isLoginSuccess != null) {
						String loginResult = isLoginSuccess.toString();
						if ("true".equals(loginResult)) {
							if (isRemember) {
								//加密
								try {
									FileOutputStream out = new FileOutputStream("./res/save/saveInfo.txt");
									//写入文件
									for (int i = 0; i < userIdString.length(); i++) {
										char t = userIdString.charAt(i);
										t ^= 'Z';
										t -= 1;
										out.write(t);
									}
									out.write('\n');
									for (int i = 0; i < userPasswordString.length(); i++) {
										char t = userPasswordString.charAt(i);
										t ^= 'Y';
										t += 1;
										out.write(t);
									}
									out.write('\n');
									//设置是否自动登录
									if (isAutoLog) {
										out.write('1');
									} else {
										out.write('0');
									}
									out.close();
								} catch (Exception e) {
									System.out.println("登录监听器错误：" + e);
								}

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
				} catch (HeadlessException headlessException) {
					System.out.println("HeadlessException，与服务器建立连接失败");
				} catch (NullPointerException e) {
					System.out.println("NullPointerException，与服务器建立连接失败！");
				}
			}
		}).start();
	}
}

/**
 * 向好友发送消息
 *
 * @author BoluBolu
 */
class Send2FriendListener implements ActionListener {
	/**
	 * 发送的内容 用户名 好友的ID 聊天窗口对象 是否是群
	 */
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
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tempChat.addMessage(userName, sDateFormat.format(new Date()), this.message.getText(), false, true);
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