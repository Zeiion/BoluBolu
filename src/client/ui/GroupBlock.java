package client.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

/**
 * 群聊列表项
 *
 * @BoluBolu
 */
public class GroupBlock extends JRadioButton {
	/**
	 * 好友头像,好友名,好友签名,好友ID
	 */
	private String friendAvatar, friendName, friendTag, friendID;
	/**
	 * 好友头像,好友名,好友签名,好友ID的label
	 */
	private JLabel friendAvatarLabel, friendNameLabel, friendTagLabel;
 
	/**
	 * 群聊列表项的构造方法
	 *
	 * @param friendAvatar 好友头像
	 * @param friendName   好友姓名
	 * @param friendTag    好友签名
	 * @param friendID     好友ID
	 * @param avatarPath   好友头像路径
	 */
	public GroupBlock(String friendAvatar, String friendName, String friendTag, String friendID, String avatarPath) {
		this.friendAvatar = friendAvatar;
		this.friendName = friendName;
		this.friendTag = friendTag;
		this.friendID = friendID;
		this.setMargin(new Insets(0, 0, 0, 0));
		this.setIcon(new ImageIcon("./res/UI/mainUI/white-block.jpg"));
		this.setRolloverIcon(new ImageIcon("./res/UI/mainUI/gold-block.jpg"));
		this.setPressedIcon(new ImageIcon("./res/UI/mainUI/gold-deep-block.jpg"));
		this.setSelectedIcon(new ImageIcon("./res/UI/mainUI/gold-deep-block.jpg"));
		this.setLayout(null);

		//好友头像
		friendAvatarLabel = new JLabel();
		friendAvatarLabel.setIcon(new ImageIcon(
			(GetAvatar.getAvatarImage(friendID, avatarPath, friendAvatar)).getImage()
				.getScaledInstance(55, 55, Image.SCALE_DEFAULT)));

		friendAvatarLabel.setBounds(6, 5, 55, 55);
		this.add(friendAvatarLabel);
		//好友名字
		friendNameLabel = new JLabel(friendName);
		friendNameLabel.setBounds(69, 5, 110, 30);
		friendNameLabel.setFont(new Font("黑体", Font.BOLD, 14));
		this.add(friendNameLabel);
		//好友个性签名
		friendTagLabel = new JLabel(friendTag);
		friendTagLabel.setBounds(69, 37, 110, 19);
		friendTagLabel.setToolTipText(friendTag);
		friendTagLabel.setForeground(Color.GRAY);
		this.add(friendTagLabel);
	}

	/**
	 * 好友头像的getter方法
	 *
	 * @return 好友头像
	 */
	public String getFriendAvatar() {
		return friendAvatar;
	}

	/**
	 * 好友名的getter方法
	 *
	 * @return 好友名
	 */
	public String getFriendName() {
		return friendName;
	}

	/**
	 * 好友签名的getter方法
	 *
	 * @return 好友签名
	 */
	public String getFriendTag() {
		return friendTag;
	}

	/**
	 * 好友ID的getter方法
	 *
	 * @return 好友ID
	 */
	public String getFriendID() {
		return friendID;
	}

	/**
	 * 好友头像的setter方法
	 *
	 * @param friendAvatar 好友头像
	 */
	public void setFriendAvatar(String friendAvatar) {
		this.friendAvatar = friendAvatar;
		friendAvatarLabel.setIcon(new ImageIcon(
			(GetAvatar.getAvatarImage(friendID, "./res/avatar/Group/", friendAvatar)).getImage()
				.getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
	}

	/**
	 * 好友名的setter方法
	 *
	 * @param friendName 好友名
	 */
	public void setFriendName(String friendName) {
		this.friendName = friendName;
		friendNameLabel.setText(friendName);
	}

	/**
	 * 好友签名的setter方法
	 *
	 * @param friendTag 好友签名
	 */
	public void setFriendTag(String friendTag) {
		this.friendTag = friendTag;
		friendTagLabel.setText(friendTag);
	}
}
