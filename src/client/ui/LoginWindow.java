package client.ui;

import com.sun.awt.AWTUtilities;
import server.config.Verify;
import server.database.DataCheck;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

import static java.awt.Image.SCALE_SMOOTH;

/**
 * 登录窗口
 *
 * @version: 1.1
 * @author: Zeiion
 */
public final class LoginWindow extends JFrame {

	private JTextField userId;
	private JLabel leftLabel, rememberPwd, autoLogin, headPortrait;
	private JPasswordField password;
	private JButton loginBtn, findPwdBtn, registerBtn, closeBtn, minBtn;
	private JCheckBox rememberPwdCheckBox, autoLoginCheckBox;
	private JPanel mainPanel;
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
		closeBtn.setBounds(783, 1, 20, 20);
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
		 * 最小化按钮
		 */
		minBtn = new JButton();
		minBtn.setMargin(new Insets(0, 0, 0, 0));
		minBtn.setBounds(763, 1, 20, 20);
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
		String headPortraitAddress = "./res/avatar/defaultAvatar.jpg";
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
				if (String.valueOf(password.getPassword()).trim().equals("密码")) {
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
		registerBtn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg) {
				//注册账号
				try{
					if (String.valueOf(password.getPassword()).equals("密码")){
						JOptionPane.showMessageDialog(null,"请输入密码！");
						return;
					}
					if (String.valueOf(password.getPassword()).equals("账号")){
						JOptionPane.showMessageDialog(null,"请输入账号！");
					}
					if (dataCheck.checkRegister(userId.getText().trim())){
						JOptionPane.showMessageDialog(null,"用户已存在！");
						return;
					}
					if (dataCheck.register(userId.getText().trim(), Verify.getMd5(String.valueOf(password.getPassword())))){
						JOptionPane.showMessageDialog(null,"注册成功！");
					}else {
						JOptionPane.showMessageDialog(null,"注册失败！");
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
		findPwdBtn.setBounds(565, 460, 100, 16);
		findPwdBtn.setContentAreaFilled(false);
		findPwdBtn.setBorderPainted(false);
		findPwdBtn.setText("找回密码");
		findPwdBtn.addActionListener(new ActionListener() {

				//找回密码'
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 自动生成的方法存根
//
					JTextField pressingPassword = new JTextField();
					JButton putup = new JButton("");
					ImageIcon backGround = new ImageIcon("./res/UI/img/loginBackground.jpg");
					JLabel label = new JLabel(backGround);
					label.setBounds(0, 0, backGround.getIconWidth(), backGround.getIconHeight());
					JPanel imagePanel = new JPanel();
					imagePanel = (JPanel) changingPassword.getContentPane();
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
					pressingPassword.setBounds(70, 170, 230, 35);
					pressingPassword.setText("请输入需要找回密码的账号！");
					pressingPassword.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
							if (pressingPassword.getText().trim().equals("请输入需要找回密码的账号！")) {
								pressingPassword.setText("");
							}
						}

						@Override
						public void focusLost(FocusEvent e) {
							if (pressingPassword.getText().trim().equals("")) {
								pressingPassword.setText("请输入需要找回密码的账号！");
							}
						}
					});
					ImageIcon i = new ImageIcon("./res/UI/loginUI/radioTick.png");
					Image i1 = i.getImage().getScaledInstance(35, 35, SCALE_SMOOTH);
					i = new ImageIcon(i1);
					putup.setIcon(i);
					changingPassword.add(putup);
					putup.setBounds(325, 170, 35, 35);
					putup.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (pressingPassword.getText().trim().equals("请输入需要找回密码的账号！")) {
								JOptionPane.showMessageDialog(changingPassword,"请输入账号！");
							}else{
									if (dataCheck.getPassword(pressingPassword.getText())!=null){
										JOptionPane.showMessageDialog(changingPassword,"您的密码是："+dataCheck.getPassword(pressingPassword.getText()).trim());
									}else{
										JOptionPane.showMessageDialog(changingPassword,"找回密码失败！");
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
		//增加记住密码
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
