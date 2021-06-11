package client.ui;

import static client.client.ServerService.sendRegister;
import static java.awt.Image.SCALE_SMOOTH;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import server.config.Verify;
import server.database.DataCheck;

/**
 * 登录窗口
 *
 * @author BoluBolu
 */
public final class LoginWindow extends JFrame {
	/**
	 * 用户ID
	 */
	private JTextField userId;
	/**
	 * 左侧图像label 记住密码label 自动登录label 头像label
	 */
	private JLabel leftLabel, rememberPwd, autoLogin, headPortrait;
	/**
	 * 密码
	 */
	private JPasswordField password;
	/**
	 * 登录，找回密码，关闭，最小化按钮
	 */
	private JButton loginBtn, findPwdBtn, registerBtn, closeBtn, minBtn;
	/**
	 * 是否记住密码和自动登录的选择框
	 */
	private JCheckBox rememberPwdCheckBox, autoLoginCheckBox;
	/**
	 * 主区域
	 */
	private JPanel mainPanel;
	/**
	 * 修改密码的窗体
	 */
	private JFrame changingPassword = new JFrame();
	DataCheck dataCheck = new DataCheck();

	/**
	 * 初始化界面
	 */
	private void init() {

		/**
		 * 主界面
		 */
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 800, 500);
		mainPanel.setBackground(new Color(255, 255, 255));

		/**
		 * 关闭按钮
		 */
		closeBtn = new JButton();
		closeBtn.setMargin(new Insets(0, 0, 0, 0));
		closeBtn.setBounds(773, 1, 20, 20);
		closeBtn.setContentAreaFilled(false);
		closeBtn.setBorderPainted(false);
		closeBtn.setFocusPainted(false);
		closeBtn.setToolTipText("关闭");
		closeBtn.setIcon(new ImageIcon("./res/UI/commonButton/closeOrigin-blue.png"));
		closeBtn.setRolloverIcon(new ImageIcon("./res/UI/commonButton/closeClick.png"));
		closeBtn.setPressedIcon(new ImageIcon("./res/UI/commonButton/closeClick.png"));
		ExitListener closeListener = new ExitListener();
		closeBtn.addActionListener(closeListener);

		/**
		 * 最小化按钮
		 */
		minBtn = new JButton();
		minBtn.setMargin(new Insets(0, 0, 0, 0));
		minBtn.setBounds(753, 1, 20, 20);
		minBtn.setContentAreaFilled(false);
		minBtn.setBorderPainted(false);
		minBtn.setFocusPainted(false);
		minBtn.setToolTipText("最小化");
		minBtn.setIcon(new ImageIcon("./res/UI/commonButton/minOrigin-blue.png"));
		minBtn.setRolloverIcon(new ImageIcon("./res/UI/commonButton/minClick-blue.png"));
		minBtn.setPressedIcon(new ImageIcon("./res/UI/commonButton/minClick-blue.png"));
		minBtn.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {
				setExtendedState(JFrame.ICONIFIED);
			}
		});

		/**
		 * 左侧图
		 */
		leftLabel = new JLabel();
		leftLabel.setBounds(0, 0, 430, 500);
		String loginLeftAddress = "./res/UI/img/loginBackground.jpg";
		Image loginLeftPic =
			(new ImageIcon(loginLeftAddress)).getImage().getScaledInstance(430, 500, Image.SCALE_DEFAULT);
		// ImageIcon headIcon = new ImageIcon(temp);
		leftLabel.setIcon(new ImageIcon(loginLeftPic));

		/**
		 * 头像
		 */
		headPortrait = new JLabel();
		headPortrait.setBounds(570, 50, 100, 100);
		String headPortraitAddress = "./res/avatar/defaultAvatar.png";
		Image headPic =
			(new ImageIcon(headPortraitAddress)).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		// ImageIcon headIcon = new ImageIcon(temp);
		headPortrait.setIcon(new ImageIcon(headPic));

		/**
		 * 账号框
		 */
		userId = new JTextField("账号");
		userId.setBounds(470, 190, 290, 50);
		//设置圆角边框
		userId.setBorder(new RoundBorder());
		userId.addFocusListener(new FocusListener() {

			@Override public void focusLost(FocusEvent e) {
				if (userId.getText().trim().equals("")) {
					userId.setForeground(Color.GRAY);
					userId.setText("账号");
				}
			}

			@Override public void focusGained(FocusEvent arg0) {
				if ("账号".equals(userId.getText().trim())) {
					userId.setText("");
					userId.setForeground(Color.BLACK);
				}
			}
		});

		/**
		 * 密码框
		 */
		password = new JPasswordField("密码");
		password.setBounds(470, 260, 290, 50);
		password.setEchoChar((char)0);
		//设置圆角边框
		password.setBorder(new RoundBorder());
		password.addFocusListener(new FocusListener() {

			@Override public void focusLost(FocusEvent e) {
				if (String.valueOf(password.getPassword()).trim().equals("")) {
					password.setEchoChar((char)0);
					password.setForeground(Color.GRAY);
					password.setText("密码");
				}
			}

			@Override public void focusGained(FocusEvent e) {
				if ("密码".equals(String.valueOf(password.getPassword()).trim())) {
					password.setEchoChar('•');
					password.setForeground(Color.BLACK);
					password.setText("");
				}
			}
		});

		/**
		 * 记住密码框
		 */
		rememberPwdCheckBox = new JCheckBox();
		rememberPwdCheckBox.setMargin(new Insets(0, 0, 0, 0));
		rememberPwdCheckBox.setBounds(490, 331, 17, 17);
		rememberPwdCheckBox.setIcon(new ImageIcon("./res/UI/loginUI/radioOrigin.png"));
		rememberPwdCheckBox.setRolloverIcon(new ImageIcon("./res/UI/loginUI/radioHover.png"));
		rememberPwdCheckBox.setPressedIcon(new ImageIcon("./res/UI/loginUI/radioClick.png"));
		rememberPwdCheckBox.setSelectedIcon(new ImageIcon("./res/UI/loginUI/radioTickDark.png"));
		rememberPwdCheckBox.setRolloverSelectedIcon(new ImageIcon("./res/UI/loginUI/radioTick.png"));

		/**
		 * 记住密码文字
		 */
		rememberPwd = new JLabel("记住密码");
		rememberPwd.setFont(new Font("黑体", Font.PLAIN, 13));
		rememberPwd.setBounds(510, 331, 60, 15);

		/**
		 * 自动登录框
		 */
		autoLoginCheckBox = new JCheckBox();
		autoLoginCheckBox.setMargin(new Insets(0, 0, 0, 0));
		autoLoginCheckBox.setBounds(660, 331, 17, 17);
		autoLoginCheckBox.setIcon(new ImageIcon("./res/UI/loginUI/radioOrigin.png"));
		autoLoginCheckBox.setRolloverIcon(new ImageIcon("./res/UI/loginUI/radioHover.png"));
		autoLoginCheckBox.setPressedIcon(new ImageIcon("./res/UI/loginUI/radioClick.png"));
		autoLoginCheckBox.setSelectedIcon(new ImageIcon("./res/UI/loginUI/radioTickDark.png"));
		autoLoginCheckBox.setRolloverSelectedIcon(new ImageIcon("./res/UI/loginUI/radioTick.png"));

		/**
		 * 自动登录文字
		 */
		autoLogin = new JLabel("自动登录");
		autoLogin.setFont(new Font("黑体", Font.PLAIN, 13));
		autoLogin.setBounds(680, 331, 52, 15);

		/**
		 * 登录按钮
		 */
		loginBtn = new JButton("登   录");
		loginBtn.setMargin(new Insets(0, 0, 0, 0));
		loginBtn.setBounds(470, 375, 290, 50);
		loginBtn.setFocusPainted(false);
		loginBtn.setForeground(new Color(255, 255, 255));
		loginBtn.setFont(new Font("华文新魏", Font.BOLD, 24));
		loginBtn.setBackground(new Color(211, 147, 37));

		/**
		 * 注册账号
		 */
		registerBtn = new JButton();
		registerBtn.setMargin(new Insets(0, 0, 0, 0));
		registerBtn.setBounds(565, 440, 100, 16);
		registerBtn.setContentAreaFilled(false);
		registerBtn.setBorderPainted(false);
		registerBtn.setText("注册账号");
		registerBtn.setFont(new Font("黑体", Font.BOLD, 13));
		registerBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg) {
				//注册账号
				try {
					if ("密码".equals(String.valueOf(password.getPassword()))) {
						JOptionPane.showMessageDialog(null, "请输入密码！", "错误！", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if ("账号".equals(String.valueOf(password.getPassword()))) {
						JOptionPane.showMessageDialog(null, "请输入账号！", "错误！", JOptionPane.ERROR_MESSAGE);
					}
					if (dataCheck.checkName(userId.getText().trim())) {
						JOptionPane.showMessageDialog(null, "用户已存在！", "错误！", JOptionPane.ERROR_MESSAGE);
						return;
					}
					int index =
						sendRegister(userId.getText().trim(), Verify.getMd5(String.valueOf(password.getPassword())));
					System.out.println("注册账号" + (100 + index));
					if (index > 0) {
						JOptionPane.showMessageDialog(null, "注册成功！\n您的账号为" + (100 + index), "成功！",
							JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "注册失败！", "错误！", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		/**
		 * 找回密码按钮
		 */
		findPwdBtn = new JButton();
		findPwdBtn.setMargin(new Insets(0, 0, 0, 0));
		findPwdBtn.setBounds(565, 465, 100, 16);
		findPwdBtn.setContentAreaFilled(false);
		findPwdBtn.setBorderPainted(false);
		findPwdBtn.setText("找回密码");
		findPwdBtn.setFont(new Font("黑体", Font.BOLD, 13));
		findPwdBtn.addActionListener(new ActionListener() {
			//找回密码
			@Override public void actionPerformed(ActionEvent e) {
				JTextField pressingPassword = new JTextField();
				JButton putUp = new JButton("");
				ImageIcon backGround = new ImageIcon("./res/UI/img/loginBackground.jpg");
				JLabel label = new JLabel(backGround);
				label.setBounds(0, 0, backGround.getIconWidth(), backGround.getIconHeight());
				JPanel imagePanel = new JPanel();
				imagePanel = (JPanel)changingPassword.getContentPane();
				imagePanel.setOpaque(false);
				changingPassword.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
				//changingPassword.add(imagePanel);
				imagePanel.setLayout(new FlowLayout());
				changingPassword.setSize(backGround.getIconWidth(), backGround.getIconHeight());
				//mainPanel.add(changingPassword,-1);
				changingPassword.setLayout(null);
				changingPassword.setLocationRelativeTo(null);
				changingPassword.setAlwaysOnTop(true);
				changingPassword.setTitle("找回密码");
				changingPassword.setIconImage(Toolkit.getDefaultToolkit().createImage("./res/UI/mainUI/logo.png"));
				changingPassword.setVisible(true);
				changingPassword.add(pressingPassword);
				changingPassword.setFocusable(true);
				pressingPassword.setBounds(70, 205, 230, 35);
				pressingPassword.setBorder(new RoundBorder());
				pressingPassword.setText("请输入需要找回密码的账号！");
				pressingPassword.addFocusListener(new FocusListener() {
					@Override public void focusGained(FocusEvent e) {
						if ("请输入需要找回密码的账号！".equals(pressingPassword.getText().trim())) {
							pressingPassword.setText("");
						}
					}

					@Override public void focusLost(FocusEvent e) {
						if (pressingPassword.getText().trim().equals("")) {
							pressingPassword.setText("请输入需要找回密码的账号！");
						}
					}
				});
				ImageIcon i = new ImageIcon("./res/UI/loginUI/radioTick.png");
				i.setImage(i.getImage().getScaledInstance(30, 30, SCALE_SMOOTH));
				//Image i1 = i.getImage().getScaledInstance(35, 35, SCALE_SMOOTH);
				//i = new ImageIcon(i);
				putUp.setIcon(i);
				changingPassword.add(putUp);
				putUp.setBounds(325, 210, 30, 30);
				putUp.addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						if ("请输入需要找回密码的账号！".equals(pressingPassword.getText().trim())) {
							JOptionPane.showMessageDialog(changingPassword, "请输入账号！", "错误！", JOptionPane.ERROR_MESSAGE);
						} else {
							if (dataCheck.getPassword(pressingPassword.getText()) != null) {
								JOptionPane.showMessageDialog(changingPassword,
									"您的MD5密码是：" + (dataCheck.getPassword(pressingPassword.getText()).trim())
										+ "\n您可以到 https://www.cmd5.com 查找相似解", "找回密码成功",
									JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane
									.showMessageDialog(changingPassword, "找回密码失败！", "错误！", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
			}
		});
		/**
		 * 找回密码界面
		 */

		/**
		 * 登录监听器
		 */
		LoginListener loginListener = new LoginListener();
		loginListener.setTempWindow(this);
		loginListener.setUserId(userId);
		loginListener.setPasswd(password);
		loginListener.setIsRememberPassword(rememberPwdCheckBox);
		loginListener.setIsAutoLogin(autoLoginCheckBox);
		userId.addActionListener(loginListener);
		password.addActionListener(loginListener);

		loginBtn.addActionListener(loginListener);
		//读取记录
		try {
			FileInputStream in = new FileInputStream("./res/save/saveInfo.txt");
			int t;
			String username = "";
			String userPassword = "";
			//对用户名密码进行解密
			while ((t = in.read()) != -1) {
				if (t == '\n') {
					break;
				}
				t += 1;
				t ^= 'Z';
				username = username + (char)t;
			}
			if (!username.equals("")) {
				while ((t = in.read()) != -1) {
					if (t == '\n') {
						break;
					}
					t -= 1;
					t ^= 'Y';
					userPassword = userPassword + (char)t;
				}
				userId.setForeground(Color.BLACK);
				userId.setText(username);
				password.setEchoChar('•');
				password.setForeground(Color.BLACK);
				password.setText(userPassword);
				t = (char)in.read();
				rememberPwdCheckBox.setSelected(true);
				if (t == '1') {
					autoLoginCheckBox.setSelected(true);
					//自动登录
					loginListener.actionPerformed(null);
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println("读取记录失败！");
		}
	}

	/**
	 * 登录窗口构造方法
	 */
	public LoginWindow() {
		setLayout(null);

		// 显示小图标
		setIconImage(Toolkit.getDefaultToolkit().createImage("./res/UI/mainUI/logo.png"));
		setTitle("波噜波噜-登录");
		init();
		add(mainPanel);
		mainPanel.add(closeBtn);
		mainPanel.add(minBtn);
		mainPanel.add(leftLabel);
		mainPanel.add(headPortrait);
		mainPanel.add(userId);
		mainPanel.add(password);
		mainPanel.add(registerBtn);
		mainPanel.add(findPwdBtn);
		mainPanel.add(rememberPwdCheckBox);
		mainPanel.add(autoLoginCheckBox);
		mainPanel.add(rememberPwd);
		mainPanel.add(autoLogin);
		mainPanel.add(loginBtn);
		//mainPanel.add(changingPassword);
		WindowMoveAdapter adapter = new WindowMoveAdapter();
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
		//整体大小
		setSize(800, 500);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		//圆角界面
		Rectangle bound = getBounds();
		AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0, 0, bound.width, bound.height, 40, 40));
	}
}
