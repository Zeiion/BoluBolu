package client.ui;

import java.io.File;
import javax.swing.ImageIcon;

/**
 * 获取头像
 */
public final class GetAvatar {

	public static ImageIcon getAvatarImage(String id, String relativePath, String avatarUrl) {
		ImageIcon avatar = null;
		try {
			String path = relativePath + id + ".jpg";
			if (!new File(path).exists()) {
				System.out.println("头像不存在");
				avatar = new ImageIcon("./res/avatar/defaultAvatar.jpg");
			} else {
				avatar = new ImageIcon(path);
			}
		} catch (Exception e) {
			avatar = new ImageIcon("./res/avatar/defaultAvatar.jpg");
			System.out.println("获取头像失败，改为默认头像：" + avatarUrl);
		}
		return avatar;
	}
}
