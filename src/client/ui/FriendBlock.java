package client.ui;

import java.awt.Font;
import javax.swing.JLabel;

/**
 * 好友列表项
 *
 * @author Zeiion
 * @version: 1.0
 */
public class FriendBlock extends GroupBlock {
	private String friendStatus;

	private JLabel friendStatusLabel;

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

	public String getFriendStatus() {
		return friendStatus;
	}

	public JLabel getFriendStatusLabel() {
		return friendStatusLabel;
	}

	public void setFriendStatus(String friendStatus) {
		this.friendStatus = friendStatus;
		friendStatusLabel.setText(friendStatus);
	}
}