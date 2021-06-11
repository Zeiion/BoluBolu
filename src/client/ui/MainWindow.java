/**
 * 主界面类
 */
package client.ui;

import client.client.ServerService;
import client.user.User;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import server.config.UserInfo.FriendsOrGroups;
import server.database.DataCheck;

/**
 * 用户主窗口 显示主界面、用户的相关信息、好友等
 *
 * @author BoluBolu
 */
public final class MainWindow extends JFrame implements ActionListener {
	/**
	 * 用户面板 跳转好友和群的按钮面板 群面板
	 */
	private JPanel userPanel, listPanel, groupPanel;
	/**
	 * 好友面板 设置为static是为了能够在好友状态变化时可以更换好友所在位置
	 */
	private static JPanel friendPanel;
	/**
	 * 最小化，关闭，签名按钮，好友项按钮，群聊项按钮，头像按钮，添加好友按钮
	 */
	private JButton minBtn, closeBtn, tagBtn, friendBtn, groupBtn, logoBtn, avatarBtn, addFriendBtn;
	/**
	 * 用户名label
	 */
	private JLabel nameLabel;
	/**
	 * 签名文本-点击签名按钮自动切换
	 */
	private JTextField tagTextField;
	/**
	 * 储存两个切换好友和群按钮的group
	 */
	private ButtonGroup friendGroup;
	/**
	 * 切换到好友列表的按钮，切换到群列表的按钮
	 */
	private JRadioButton friendRadio, groupRadio;
	/**
	 * 用户信息
	 */
	private User userInfo;
	/**
	 * 好友列表滚动框
	 */
	private JScrollPane friendScrollPane;
	/**
	 * 所有好友按钮的group,所有群聊按钮的group
	 */
	private ButtonGroup friendBtnGroup, groupBtnGroup;
	/**
	 * 日期检查
	 */
	private DataCheck dataCheck = new DataCheck();

	public static JPanel getFriendPanel() {
		return friendPanel;
	}

	/**
	 * 好友列表的HashMap
	 */
	private static HashMap<String, FriendBlock> friend = new HashMap<>();
	/**
	 * 群聊列表的HashMap
	 */
	private static HashMap<String, GroupBlock> group = new HashMap<>();

	public static HashMap<String, ChatWindow> getWithFriend() {
		return withFriend;
	}

	/**
	 * 好友聊天窗的HashMap
	 */
	private static HashMap<String, ChatWindow> withFriend = new HashMap<>();

	public static HashMap<String, ChatWindow> getWithGroup() {
		return withGroup;
	}

	/**
	 * 群聊聊天窗的HashMap
	 */
	private static HashMap<String, ChatWindow> withGroup = new HashMap<>();

	/**
	 * 用户主窗口的构造方法
	 */
	public MainWindow(String userId) {
		// 获取用户信息
		userInfo = ServerService.getUserInfo(userId);

		// 设置窗口图标和名字
		setIconImage(Toolkit.getDefaultToolkit().createImage("./res/UI/mainUI/logo.png"));
		setTitle("波噜波噜-" + userInfo.getUserName());

		init();
		setLayout(null);

		userPanel.add(logoBtn);
		userPanel.add(closeBtn);
		userPanel.add(minBtn);
		userPanel.add(avatarBtn);
		userPanel.add(nameLabel);
		userPanel.add(tagBtn);
		userPanel.add(addFriendBtn);
		userPanel.add(tagTextField);
		listPanel.add(friendBtn);
		listPanel.add(friendRadio);
		listPanel.add(groupRadio);
		listPanel.add(groupBtn);
		listPanel.add(friendScrollPane);
		add(userPanel);
		add(listPanel);

		WindowMoveAdapter adapter = new WindowMoveAdapter();
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
		setSize(350, 750);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void init() {

		/**
		 * 用户板块
		 */
		userPanel = new ImagePanel(Toolkit.getDefaultToolkit().createImage("./res/UI/img/grandeur.png"));
		userPanel.setLayout(null);
		userPanel.setBounds(0, 0, 350, 150);
		userPanel.setBackground(new Color(122, 180, 202));

		/**
		 * logo
		 */
		ImageIcon logo = new ImageIcon("./res/UI/img/logo.png");
		Image logoImg = logo.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
		ImageIcon logo2 = new ImageIcon(logoImg);
		logoBtn = new JButton(logo2);
		logoBtn.setBounds(10, 10, 20, 20);
		logoBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		logoBtn.setContentAreaFilled(false);

		/**
		 * 关闭按钮
		 */
		closeBtn = new JButton("");
		closeBtn.setMargin(new Insets(0, 0, 0, 0));
		closeBtn.setBounds(330, 0, 20, 20);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setFocusPainted(false);
		closeBtn.setToolTipText("关闭");
		closeBtn.setIcon(new ImageIcon("./res/UI/commonButton/closeOrigin.png"));
		closeBtn.setRolloverIcon(new ImageIcon("./res/UI/commonButton/closeHover.png"));
		closeBtn.setPressedIcon(new ImageIcon("./res/UI/commonButton/closeClick.png"));
		ExitListener closeListener = new ExitListener();
		closeBtn.addActionListener(closeListener);
		/**
		 * 缩小按钮
		 */
		minBtn = new JButton();
		minBtn.setMargin(new Insets(0, 0, 0, 0));
		minBtn.setBounds(310, 0, 20, 20);
		minBtn.setContentAreaFilled(false);
		minBtn.setBorderPainted(false);
		minBtn.setFocusPainted(false);
		minBtn.setToolTipText("最小化");
		minBtn.setIcon(new ImageIcon("./res/UI/commonButton/minOrigin.png"));
		minBtn.setRolloverIcon(new ImageIcon("./res/UI/commonButton/minHover.png"));
		minBtn.setPressedIcon(new ImageIcon("./res/UI/commonButton/minClick.png"));
		minBtn.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});

		/**
		 * 头像按钮
		 */
		avatarBtn = new JButton();
		avatarBtn.setBounds(20, 50, 80, 80);
		avatarBtn.setVisible(true);
		Image headPic =
			(GetAvatar.getAvatarImage(userInfo.getUserId(), "./res/avatar/User/", userInfo.getUserAvatar())).getImage()
				.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
		avatarBtn.setIcon(new ImageIcon(headPic));
		avatarBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int flag = jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (flag == JFileChooser.APPROVE_OPTION && (file.getName().endsWith(".png") || file.getName()
					.endsWith(".jpg"))) {
					if (file.isDirectory()) {
						System.out.println("文件夹:" + file.getAbsolutePath());
					} else if (file.isFile()) {
						System.out.println("文件:" + file.getAbsolutePath());
						Image i = new ImageIcon(file.getAbsolutePath()).getImage()
							.getScaledInstance(80, 80, Image.SCALE_DEFAULT);
						avatarBtn.setIcon(new ImageIcon(i));
						//复制图片
						int begin = file.getName().indexOf('.');
						String output = "./res/avatar/User/" + userInfo.getUserId() + file.getName().substring(begin);
						FileInputStream fis = null;
						FileOutputStream fos = null;
						try {
							//指明需要复制的图片的路径
							File srcFile = new File(file.getAbsolutePath());
							//指明复制后的图片去向
							File destFile = new File(output);
							fis = new FileInputStream(srcFile);
							fos = new FileOutputStream(destFile);
							byte[] buffer = new byte[1024];
							int len;
							//写入数据
							while ((len = fis.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							if (fos != null) {
								try {
									fos.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
							if (fis != null) {
								try {
									fis.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}

			}
		});

		/**
		 * 加好友按钮
		 */
		ImageIcon m = new ImageIcon("./res/UI/mainUI/addFriend.png");
		Image mm = m.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon m2 = new ImageIcon(mm);
		addFriendBtn = new JButton(m2);
		addFriendBtn.setBounds(310, 100, 30, 30);
		addFriendBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		addFriendBtn.setContentAreaFilled(false);
		addFriendBtn.addActionListener((e) -> {
			String friendId = JOptionPane.showInputDialog("请输入对方账号！");
			try {
				//未输入
				if (friendId.isEmpty()) {
					return;
				}
			} catch (Exception exception) {
				return;
			}
			if (!dataCheck.checkRegister(friendId)) {
				JOptionPane.showMessageDialog(null, "用户不存在！");
			} else if (friendId.equals(userInfo.getUserId())) {
				JOptionPane.showMessageDialog(null, "不能加自己为好友！");
				return;
			} else if (dataCheck.checkFriend(userInfo.getUserId(), friendId)) {
				JOptionPane.showMessageDialog(null, "好友关系已存在！");
				return;
			} else if (dataCheck.addFriend(userInfo.getUserId(), friendId)) {
				userInfo.getFriends().add(dataCheck.checkUser(friendId));
				JOptionPane.showMessageDialog(null, "添加好友成功！");
			} else {
				JOptionPane.showMessageDialog(null, "添加好友失败！");
			}
		});

		/**
		 * 用户名
		 */
		String username = userInfo.getUserName();
		nameLabel = new JLabel(username);
		nameLabel.setBounds(117, 60, 160, 30);
		nameLabel.setFont(new Font("华文新魏", Font.BOLD, 24));
		nameLabel.setForeground(Color.WHITE);

		/**
		 * 个性签名按钮
		 */
		tagBtn = new JButton();
		tagBtn.setHorizontalAlignment(SwingConstants.LEFT);
		tagBtn.setMargin(new Insets(0, 0, 0, 0));
		tagBtn.setBounds(115, 100, 190, 20);
		tagBtn.setContentAreaFilled(false);
		tagBtn.setBorderPainted(false);
		tagBtn.setRolloverIcon(new ImageIcon("./res/UI/mainUI/ContactFilter_splitter.png"));
		String tag = userInfo.getUserTag();
		if (tag.equals("")) {
			tag = "编辑个性签名";
		}
		tagBtn.setText(tag);
		tagBtn.setToolTipText(tag);
		tagTextField = new JTextField();
		tagTextField.setBounds(115, 100, 190, 20);
		tagTextField.setVisible(false);
		tagBtn.addActionListener(new ActionListener() {

			// 点击之后变为TextField
			@Override public void actionPerformed(ActionEvent e) {
				tagBtn.setVisible(false);
				tagTextField.setVisible(true);
				if (tagBtn.getText().equals("编辑个性签名")) {
					tagTextField.setText("");
				} else {
					tagTextField.setText(tagBtn.getText());
				}
				tagTextField.requestFocus();
			}
		});
		tagTextField.addFocusListener(new FocusListener() {

			/**
			 * TextField失去焦点之后变为Button并将更改后的内容传送给服务器
			 *
			 * @param e 焦点事件
			 */
			@Override public void focusLost(FocusEvent e) {
				tagTextField.setVisible(false);
				if (tagTextField.getText().equals("")) {
					tagBtn.setText("编辑个性签名");
				} else {
					tagBtn.setText(tagTextField.getText());

					// 更新服务端数据
					ServerService.setTag(userInfo.getUserId(), tagTextField.getText());
				}
				tagBtn.setVisible(true);
			}

			@Override public void focusGained(FocusEvent e) {

			}
		});
		tagTextField.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {
				tagTextField.setVisible(false);
				if (tagTextField.getText().equals("")) {
					tagBtn.setText("编辑个性签名");
				} else {
					tagBtn.setText(tagTextField.getText());
				}
				// Send the message to server

				tagBtn.setVisible(true);
			}
		});
		/**
		 * 列表板块
		 */
		listPanel = new JPanel();
		listPanel.setLayout(null);
		listPanel.setBounds(0, 150, 350, 600);
		listPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		listPanel.setBackground(Color.WHITE);

		/**
		 * 好友和群聊切换
		 */
		friendBtn = new JButton();
		friendBtn.setBounds(0, 0, 75, 36);
		friendBtn.setContentAreaFilled(false);
		friendBtn.setFocusPainted(false);
		friendBtn.setBorderPainted(false);
		friendBtn.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent arg0) {
				friendRadio.setSelected(true);
				friendRadio.requestFocus();
			}
		});
		friendRadio = new JRadioButton();
		friendRadio.setBounds(75, 0, 100, 36);
		friendRadio.setHorizontalTextPosition(SwingConstants.CENTER);
		friendRadio.setBackground(Color.WHITE);
		friendRadio.setIcon(new ImageIcon("./res/UI/mainUI/friendOrigin.png"));
		friendRadio.setRolloverIcon(new ImageIcon("./res/UI/mainUI/friendActive.png"));
		friendRadio.setSelectedIcon(new ImageIcon("./res/UI/mainUI/friendActive.png"));
		friendRadio.setSelected(true);
		friendRadio.addFocusListener(new FocusListener() {

			@Override public void focusLost(FocusEvent arg0) {

			}

			@Override public void focusGained(FocusEvent arg0) {
				friendScrollPane.setViewportView(friendPanel);

			}
		});
		groupBtn = new JButton();
		groupBtn.setBounds(175, 0, 75, 36);
		groupBtn.setContentAreaFilled(false);
		groupBtn.setFocusPainted(false);
		groupBtn.setBorderPainted(false);
		groupBtn.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent arg0) {
				groupRadio.setSelected(true);
				groupRadio.requestFocus();
			}
		});
		groupRadio = new JRadioButton();
		groupRadio.setBounds(250, 0, 100, 36);
		groupRadio.setBackground(Color.WHITE);
		groupRadio.setIcon(new ImageIcon("./res/UI/mainUI/groupOrigin.png"));
		groupRadio.setRolloverIcon(new ImageIcon("./res/UI/mainUI/groupActive.png"));
		groupRadio.setSelectedIcon(new ImageIcon("./res/UI/mainUI/groupActive.png"));
		groupRadio.addFocusListener(new FocusListener() {

			@Override public void focusLost(FocusEvent e) {

			}

			@Override public void focusGained(FocusEvent e) {
				friendScrollPane.setViewportView(groupPanel);
			}
		});
		friendGroup = new ButtonGroup();
		friendGroup.add(friendRadio);
		friendGroup.add(groupRadio);
		/**
		 * 好友
		 */
		int friendsNumber = userInfo.getFriends().size();
		friendPanel = new JPanel();
		friendPanel.setLayout(null);
		friendPanel.setBounds(0, 0, 350, friendsNumber * 66);
		friendPanel.setPreferredSize(new Dimension(330, friendsNumber * 66));
		friendBtnGroup = new ButtonGroup();
		Collections.sort(userInfo.getFriends(), new Comparator<FriendsOrGroups>() {
			@Override public int compare(FriendsOrGroups f1, FriendsOrGroups f2) {
				if (f2.getStatus().equals("在线")) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		for (int i = 0; i < friendsNumber; i++) {
			FriendsOrGroups userFriend = userInfo.getFriends().get(i);
			String friendAvatar = userFriend.getAvatar();
			String friendName = userFriend.getName();
			String friendTag = userFriend.getTag();
			String friendID = userFriend.getId();
			String friendStatus = userFriend.getStatus();
			friend.put(friendID, new FriendBlock(friendAvatar, friendName, friendTag, friendID, friendStatus));
			//每个宽高
			friend.get(friendID).setBounds(0, i * 66, 350, 66);
			friend.get(friendID).addMouseListener(new MouseListener() {
				@Override public void mouseReleased(MouseEvent e) {

				}

				@Override public void mousePressed(MouseEvent e) {

				}

				@Override public void mouseExited(MouseEvent e) {

				}

				@Override public void mouseEntered(MouseEvent e) {

				}

				@Override public void mouseClicked(MouseEvent e) {
					//点击好友，不会打开多个聊天框
					if (e.getClickCount() == 2) {
						if (withFriend.get(friendID) == null) {
							withFriend.put(friendID,
								new ChatWindow(userInfo.getUserId(), userInfo.getUserName(), friendID, friendAvatar,
									friendName, friendTag, false));
						} else {
							ChatWindow c = withFriend.get(friendID);
							c.setAlwaysOnTop(true);
							c.setAlwaysOnTop(false);
						}

					}
				}
			});
			friendPanel.add(friend.get(friendID));
			friendBtnGroup.add(friend.get(friendID));
		}
		/**
		 * 群聊
		 */
		int groupsNumber = userInfo.getGroups().size();
		groupPanel = new JPanel();
		groupPanel.setLayout(null);
		groupPanel.setBounds(0, 0, 350, groupsNumber * 66);
		groupPanel.setPreferredSize(new Dimension(330, groupsNumber * 66));
		groupBtnGroup = new ButtonGroup();
		for (int j = 0; j < groupsNumber; j++) {
			FriendsOrGroups userGroup = userInfo.getGroups().get(j);
			String groupAvatar = userGroup.getAvatar(), groupName = userGroup.getName(), groupTag = userGroup.getTag(),
				groupID = userGroup.getId();
			group.put(groupID, new GroupBlock(groupAvatar, groupName, groupTag, groupID, "./res/avatar/Group/"));
			group.get(groupID).setBounds(0, j * 66, 350, 66);
			group.get(groupID).addMouseListener(new MouseListener() {

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
						if (withGroup.get(groupID) == null) {
							withGroup.put(groupID,
								new ChatWindow(userInfo.getUserId(), userInfo.getUserName(), groupID, groupAvatar,
									groupName, groupTag, true));
						} else {
							ChatWindow c = withGroup.get(groupID);
							c.setAlwaysOnTop(true);
							c.setAlwaysOnTop(false);
						}

					}
				}
			});
			groupPanel.add(group.get(groupID));
			groupPanel.setVisible(true);
			groupBtnGroup.add(group.get(groupID));
		}
		friendScrollPane = new JScrollPane(friendPanel);
		friendScrollPane.setBorder(new EmptyBorder(1, 0, 0, 0));
		// 设置滚动条样式
		friendScrollPane.getVerticalScrollBar().setUI(new ScrollBar());
		// 设置滚动速率
		friendScrollPane.getVerticalScrollBar().setUnitIncrement(20);
		friendScrollPane.setBounds(0, 36, 350, 564);
	}

	public static HashMap<String, FriendBlock> getFriend() {
		return friend;
	}

	public static HashMap<String, GroupBlock> getGroup() {
		return group;
	}

	public static HashMap<String, ChatWindow> getFriendChat() {
		return withFriend;
	}

	public static HashMap<String, ChatWindow> getGroupChat() {
		return withGroup;
	}

	@Override public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();
		if (file.isDirectory()) {
			System.out.println("文件夹:" + file.getAbsolutePath());
		} else if (file.isFile()) {
			System.out.println("文件:" + file.getAbsolutePath());
		}
		System.out.println(jfc.getSelectedFile().getName());
	}
}