package client.ui;

import client.client.ServerService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

/**
 * 聊天窗口
 *
 * @author BoluBoLu
 */
public final class ChatWindow extends JFrame {
	/**
	 * 聊天窗口的最大化，最小化，关闭和发送消息按钮
	 */
	private JButton minBtn, closeBtn, maxBtn, sendButton;
	/**
	 * 聊天窗口位置移动
	 */
	private WindowMoveAdapter adapter;
	/**
	 * 聊天窗口的顶部、输入框、群成员列表
	 */
	private JPanel headPanel, inputPanel, groupListPanel;
	/**
	 * 好友的头像，用户名和签名
	 */
	private JLabel friendAvatar, friendName, friendTag;
	/**
	 * 好友头像的文件名，用户名，个性签名，ID，自己的ID和姓名
	 */
	private String friendAvatarString, friendNameString, friendTagString, friendID, selfID, selfName;
	/**
	 * 主区域和群区域
	 */
	private Box mainBox, groupBox;
	/**
	 * 聊天框、输入框、群的滑动条
	 */
	private JScrollPane chatScroll, inputScroll, groupScroll;
	/**
	 * 文本输入域
	 */
	private JTextArea input;
	/**
	 * 头像
	 */
	private Image avatar;
	/**
	 * 消息数量
	 */
	private int messageNum = 0;
	/**
	 * 是否是群
	 */
	private boolean isGroup;

	/**
	 * 聊天框的构造方法
	 *
	 * @param selfID             自己的ID
	 * @param selfName           自己的用户名
	 * @param friendID           好友的ID
	 * @param friendAvatarString 好友的头像文件名
	 * @param friendNameString   好友的用户名
	 * @param friendTagString    好友的签名内容
	 * @param isGroup            是否是群
	 */
	public ChatWindow(String selfID, String selfName, String friendID, String friendAvatarString,
		String friendNameString, String friendTagString, boolean isGroup) {
		this.selfID = selfID;
		this.selfName = selfName;
		this.friendID = friendID;
		this.friendAvatarString = friendAvatarString;
		this.friendNameString = friendNameString;
		this.friendTagString = friendTagString;
		this.isGroup = isGroup;

		setTitle("波噜波噜- " + selfName + " 与 " + friendNameString + " 的会话");

		setLayout(null);

		/**
		 * 好友头像加载
		 */
		avatar = (GetAvatar
			.getAvatarImage(friendID, isGroup ? "./res/avatar/Group/" : "./res/avatar/User/", friendAvatarString))
			.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
		setIconImage(avatar);

		setSize(750, 700);
		init();

		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * 窗口初始化
	 */
	private void init() {
		/**
		 * 关闭按钮
		 */
		closeBtn = new JButton("");
		closeBtn.setMargin(new Insets(0, 0, 0, 0));
		closeBtn.setBounds(730, 0, 20, 20);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setFocusPainted(false);
		closeBtn.setToolTipText("关闭窗口");
		closeBtn.setIcon(new ImageIcon("./res/UI/chatUI/closeOrigin.png"));
		closeBtn.setRolloverIcon(new ImageIcon("./res/UI/chatUI/closeHover.png"));
		closeBtn.setPressedIcon(new ImageIcon("./res/UI/chatUI/closeClick.png"));
		closeBtn.addActionListener(new CloseListener(this, friendID, isGroup));
		/**
		 * 最大化按钮
		 */
		maxBtn = new JButton();
		maxBtn.setMargin(new Insets(0, 0, 0, 0));
		maxBtn.setBounds(710, 0, 20, 20);
		maxBtn.setContentAreaFilled(false);
		maxBtn.setFocusPainted(false);
		maxBtn.setBorderPainted(false);
		maxBtn.setToolTipText("最大化");
		maxBtn.setIcon(new ImageIcon("./res/UI/chatUI/maxOrigin.png"));
		maxBtn.setRolloverIcon(new ImageIcon("./res/UI/chatUI/maxHover.png"));
		maxBtn.setPressedIcon(new ImageIcon("./res/UI/chatUI/maxClick.png"));
		maxBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if (getExtendedState() == JFrame.NORMAL) {
					//最大化，改变窗体大小
					maxBtn.setIcon(new ImageIcon("./res/UI/chatUI/restoreOrigin.png"));
					maxBtn.setRolloverIcon(new ImageIcon("./res/UI/chatUI/restoreHover.png"));
					maxBtn.setPressedIcon(new ImageIcon("./res/UI/chatUI/restoreClick.png"));
					setExtendedState(JFrame.MAXIMIZED_BOTH);
					closeBtn.setBounds(getWidth() - 20, 0, 20, 20);
					maxBtn.setBounds(getWidth() - 40, 0, 20, 20);
					minBtn.setBounds(getWidth() - 60, 0, 20, 20);
					headPanel.setBounds(0, 0, getWidth(), 120);
					if (isGroup) {
						chatScroll.setBounds(0, 120, getWidth() - 200, getHeight() - 320);
						inputPanel.setBounds(0, getHeight() - 200, getWidth() - 200, 200);
						sendButton.setBounds(inputPanel.getWidth() - 280, inputPanel.getHeight() - 28, 70, 24);
						groupListPanel.setBounds(getWidth() - 200, 120, 200, getHeight() - 120);
						groupScroll.setBounds(0, 0, 200, getHeight() - 120);
					} else {
						chatScroll.setBounds(0, 120, getWidth(), getHeight() - 320);
						inputPanel.setBounds(0, getHeight() - 200, getWidth(), 200);
						sendButton.setBounds(inputPanel.getWidth() - 80, inputPanel.getHeight() - 28, 70, 24);
					}
					input.setBounds(0, 0, inputPanel.getWidth(), 170);
					inputScroll.setBounds(0, 0, inputPanel.getWidth(), 170);
					adapter.setCanMove(false);
				} else if (getExtendedState() == JFrame.MAXIMIZED_BOTH) {
					//最小化，改变窗体大小
					maxBtn.setIcon(new ImageIcon("./res/UI/chatUI/maxOrigin.png"));
					maxBtn.setRolloverIcon(new ImageIcon("./res/UI/chatUI/maxHover.png"));
					maxBtn.setPressedIcon(new ImageIcon("./res/UI/chatUI/maxClick.png"));
					setExtendedState(JFrame.NORMAL);
					closeBtn.setBounds(getWidth() - 20, 0, 20, 20);
					maxBtn.setBounds(getWidth() - 40, 0, 20, 20);
					minBtn.setBounds(getWidth() - 60, 0, 20, 20);
					headPanel.setBounds(0, 0, getWidth(), 120);
					if (isGroup) {
						chatScroll.setBounds(0, 120, getWidth() - 200, getHeight() - 320);
						inputPanel.setBounds(0, getHeight() - 200, getWidth() - 200, 200);
						sendButton.setBounds(inputPanel.getWidth() - 280, inputPanel.getHeight() - 28, 70, 24);
						groupListPanel.setBounds(750, 120, 200, 580);
						groupScroll.setBounds(0, 0, 200, 580);
					} else {
						chatScroll.setBounds(0, 120, getWidth(), getHeight() - 320);
						inputPanel.setBounds(0, getHeight() - 200, getWidth(), 200);
						sendButton.setBounds(inputPanel.getWidth() - 80, inputPanel.getHeight() - 28, 70, 24);
					}
					input.setBounds(0, 0, inputPanel.getWidth(), 170);
					inputScroll.setBounds(0, 0, inputPanel.getWidth(), 170);
					adapter.setCanMove(true);
				}
			}
		});
		/**
		 * 最小化按钮
		 */
		minBtn = new JButton();
		minBtn.setMargin(new Insets(0, 0, 0, 0));
		minBtn.setBounds(690, 0, 20, 20);
		minBtn.setContentAreaFilled(false);
		minBtn.setBorderPainted(false);
		minBtn.setFocusPainted(false);
		minBtn.setToolTipText("最小化");
		minBtn.setIcon(new ImageIcon("./res/UI/chatUI/minOrigin.png"));
		minBtn.setRolloverIcon(new ImageIcon("./res/UI/chatUI/minHover.png"));
		minBtn.setPressedIcon(new ImageIcon("./res/UI/chatUI/minClick.png"));
		minBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});
		/**
		 * 顶部条 拖动可以移动窗口
		 */
		headPanel = new ImagePanel(Toolkit.getDefaultToolkit().createImage("./res/UI/img/grandeur-light.png"));
		headPanel.setLayout(null);
		headPanel.setBackground(new Color(122, 180, 202));
		adapter = new WindowMoveAdapter();
		headPanel.addMouseMotionListener(adapter);
		headPanel.addMouseListener(adapter);
		headPanel.addMouseListener(new MouseListener() {

			@Override public void mouseReleased(MouseEvent e) {
			}

			@Override public void mousePressed(MouseEvent e) {
			}

			@Override public void mouseExited(MouseEvent e) {
			}

			@Override public void mouseEntered(MouseEvent e) {
			}

			@Override public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// 最大化
				}
			}
		});

		/**
		 * 好友头像
		 */
		friendAvatar = new JLabel(new ImageIcon(friendAvatarString));
		friendAvatar.setBounds(15, 10, 90, 90);
		friendAvatar.setIcon(new ImageIcon(avatar));
		headPanel.add(friendAvatar);

		/**
		 * 好友名字
		 */
		friendName = new JLabel(friendNameString);
		friendName.setBounds(120, 25, 200, 22);
		friendName.setFont(new Font("黑体", Font.BOLD, 20));
		headPanel.add(friendName);

		/**
		 * 好友个性签名
		 */
		friendTag = new JLabel(friendTagString);
		friendTag.setBounds(120, 65, 200, 15);
		friendTag.setFont(new Font("宋体", Font.BOLD, 14));
		headPanel.add(friendTag);

		/**
		 * 主面板（背景、滚动条等）
		 */
		mainBox = Box.createVerticalBox();
		//		mainBox.setOpaque(true);
		chatScroll = new JScrollPane(mainBox);
		chatScroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		//滚动条
		JScrollBar jsb = chatScroll.getVerticalScrollBar();
		jsb.setUI(new ScrollBar());
		chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// 设置滚动速率
		jsb.setUnitIncrement(20);
		chatScroll.setBounds(0, 120, 750, 380);

		/**
		 * 获取聊天记录
		 */
		Vector<String> record = ServerService.getChatRecord(selfID, friendID, isGroup);
		for (int i = 0; i < record.size(); i++) {
			/**
			 * res[0] 消息发送时间 res[1] fromId res[2] toId res[3] message
			 */
			String[] res = record.get(i).split("```", 4);
			// 聊天面板显示用户昵称
			String fromName = res[1].equals(selfID) ? selfName
				: MainWindow.getFriend().containsKey(res[1]) ? MainWindow.getFriend().get(res[1]).getFriendName()
					: ("陌生人:" + res[1]);
			if (res.length == 4) {
				if (res[1].equals(selfID)) {
					//如果是自己发的消息
					addMessage(fromName, res[0], res[3], true, true);
				} else {
					//如果是别人发的消息
					addMessage(fromName, res[0], res[3], true, false);
				}
			}
		}

		// 设置自动滑到底
		chatScroll.getViewport().setViewPosition(new Point(0, chatScroll.getHeight() + 100000));

		/**
		 * 输入框
		 */
		inputPanel = new ImagePanel(Toolkit.getDefaultToolkit().createImage("./res/UI/img/grandeur-windy.png"));
		inputPanel.setLayout(null);
		inputPanel.setBounds(0, 500, 750, 200);
		input = new JTextArea();
		input.setFont(new Font("黑体", Font.PLAIN, 20));
		input.setBorder(new EmptyBorder(0, 0, 0, 0));
		input.setBounds(0, 0, 750, 170);
		input.setLineWrap(true);
		inputScroll = new JScrollPane(input);
		inputScroll.setBounds(0, 0, 750, 170);
		inputScroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		inputScroll.getVerticalScrollBar().setUI(new ScrollBar());
		inputScroll.getVerticalScrollBar().setUnitIncrement(15);
		inputPanel.add(inputScroll);

		/**
		 * 发送按钮
		 */
		sendButton = new JButton("发送");
		sendButton.setBorderPainted(false);
		sendButton.setFocusPainted(false);
		sendButton.setMargin(new Insets(0, 0, 0, 0));
		sendButton.setBackground(new Color(238, 238, 238));
		sendButton.setFont(new Font("黑体", Font.BOLD, 15));
		sendButton.setBounds(670, 172, 70, 24);
		Send2FriendListener send2FriendListener = new Send2FriendListener(selfName, friendID, isGroup);
		send2FriendListener.setMessage(input);
		send2FriendListener.setTempChat(this);
		sendButton.addActionListener(send2FriendListener);
		inputPanel.add(sendButton);

		//监听回车事件
		input.addKeyListener(new KeyAdapter() {
			@Override public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					send2FriendListener.enter();
				}
			}
		});

		/**
		 * 群聊额外列表
		 */
		if (isGroup) {
			groupBox = Box.createVerticalBox();
			Vector<String> members = ServerService.getGroupMembers(friendID);
			for (int i = 0; i < members.size(); i++) {
				String tempMember = members.get(i);
				ImageIcon icon = new ImageIcon("./res/UI/img/defaultAvatar.png");
				String content = "陌生人(" + tempMember + ")";
				if (tempMember.equals(selfID)) {
					content = "我自己";
					icon = new ImageIcon(GetAvatar.getAvatarImage(selfID, "./res/avatar/User/", "").getImage()
						.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				}
				Box peopleBox = Box.createHorizontalBox();
				peopleBox.setBounds(0, 0, 200, 60);
				if (MainWindow.getFriend().containsKey(tempMember)) {
					icon = GetAvatar.getAvatarImage(tempMember, "./res/avatar/User/",
						MainWindow.getFriend().get(tempMember).getFriendAvatar());
					content = MainWindow.getFriend().get(tempMember).getFriendName() + "(" + MainWindow.getFriend()
						.get(tempMember).getFriendID() + ")";
				}
				icon = new ImageIcon(icon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
				JLabel iconLabel = new JLabel(icon);
				JLabel nameLabel = new JLabel(content);
				JLabel blank = new JLabel();
				iconLabel.setPreferredSize(new Dimension(80, 30));
				nameLabel.setPreferredSize(new Dimension(80, 30));
				peopleBox.add(iconLabel);
				peopleBox.add(nameLabel);
				JPanel peoplePanel = new ImagePanel(Toolkit.getDefaultToolkit().createImage("./res/UI/img/white.png"));
				peoplePanel.add(peopleBox);
				groupBox.add(peoplePanel);
			}
			groupScroll = new JScrollPane(groupBox);

			groupScroll.getVerticalScrollBar().setUI(new ScrollBar());
			groupScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			// 设置滚动速率
			groupScroll.getVerticalScrollBar().setUnitIncrement(16);
			groupScroll.setBounds(0, 0, 200, 580);
			groupScroll.setBorder(new EmptyBorder(0, 0, 0, 0));

			groupListPanel = new ImagePanel(Toolkit.getDefaultToolkit().createImage("./res/UI/img/group.png"));
			groupListPanel.setBounds(750, 120, 200, 580);
			groupListPanel.add(groupScroll);
			groupListPanel.setBorder(new EmptyBorder(0, 4, 0, 0));
			//其他更改位置的按钮
			closeBtn.setBounds(930, 0, 20, 20);
			maxBtn.setBounds(910, 0, 20, 20);
			minBtn.setBounds(890, 0, 20, 20);
			headPanel.setBounds(0, 0, 950, 120);
			setSize(950, 700);
			add(groupListPanel);
		} else {
			setSize(750, 700);
			headPanel.setBounds(0, 0, 750, 120);
			closeBtn.setBounds(730, 0, 20, 20);
			maxBtn.setBounds(710, 0, 20, 20);
			minBtn.setBounds(690, 0, 20, 20);
		}

		headPanel.add(closeBtn);
		headPanel.add(maxBtn);
		headPanel.add(minBtn);
		add(headPanel);
		add(chatScroll);
		add(inputPanel);
	}

	/**
	 * 对象的messageNum的getter方法
	 *
	 * @return 对象属性 messageNum
	 */
	public int getMessageNum() {
		return messageNum;
	}

	/**
	 * 对象的messageNum的setter方法
	 *
	 * @param messageNum 需要set的值
	 */
	public void setMessageNum(int messageNum) {
		this.messageNum = messageNum;
	}

	/**
	 * 增加消息
	 *
	 * @param userName 用户名
	 * @param sendTime 发送时间
	 * @param message  消息内容
	 * @param isOld    是否是以前的消息
	 * @param isSelf   是否是自己的消息
	 */
	public void addMessage(String userName, String sendTime, String message, boolean isOld, boolean isSelf) {
		String head = "<html><div style =\"font-size:12px;color:#0d171f\">";
		String tail = "</div></html>";
		sendTime = " <span style=\"font-size:10px;color:#d39325\">&nbsp;" + sendTime + "&nbsp;</span>";
		message = "<html><div style =\"font-size:14px;background-color:white;padding:2px 8px;border:1px solid;" + (isOld
			? "color:#acacac" : "") + "\">" + message + "</div><br/></html>";
		//聊天记录分立
		if (isSelf) {
			JLabel jLabel = new JLabel(head + sendTime + userName + tail, JLabel.RIGHT);
			mainBox.add(jLabel);
			JLabel jLabel2 = new JLabel(message, JLabel.RIGHT);
			mainBox.add(jLabel2);
		} else {
			mainBox.add(new JLabel(head + "&nbsp;" + userName + sendTime + tail));
			mainBox.add(new JLabel(message));
		}

		//设置新消息自动滑到底
		chatScroll.getViewport().setViewPosition(new Point(0, chatScroll.getHeight() + 100000));
	}
}