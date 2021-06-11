package client.ui;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * 好友列表项
 *
 * @author BoluBolu
 */
public class FriendBlock extends GroupBlock {
	/**
	 * 好友状态,在线或者离线
	 */
	private String friendStatus;
	/**
	 * 好友状态的label
	 */
	private JLabel friendStatusLabel;

	/**
	 * 好友列表项的构造方法
	 *
	 * @param friendAvatar 好友头像
	 * @param friendName   好友名字
	 * @param friendTag    好友签名
	 * @param friendID     好友ID
	 * @param friendStatus 好友状态
	 */
	public FriendBlock(String friendAvatar, String friendName, String friendTag, String friendID, String friendStatus) {
		super(friendAvatar, friendName, friendTag, friendID, "./res/avatar/User/");
		this.friendStatus = friendStatus;
		friendStatusLabel = new JLabel(friendStatus);
		friendStatusLabel.setBounds(295, 20, 42, 22);
		if ("在线".equals(friendStatus)) {
			friendStatusLabel.setFont(new Font("黑体", Font.BOLD, 13));
		} else {
			friendStatusLabel.setFont(new Font("黑体", Font.PLAIN, 13));
		}
		this.add(friendStatusLabel);
	}

	/**
	 * 好友状态的getter方法
	 *
	 * @return 好友状态
	 */
	public String getFriendStatus() {
		return friendStatus;
	}

	/**
	 * 好友状态的label的getter方法
	 *
	 * @return 好友状态的label
	 */
	public JLabel getFriendStatusLabel() {
		return friendStatusLabel;
	}

	/**
	 * 好友状态的setter方法
	 *
	 * @param friendStatus 好友状态
	 */
	public void setFriendStatus(String friendStatus) {
		this.friendStatus = friendStatus;
		friendStatusLabel.setText(friendStatus);
	}
}