package client.ui;

import java.io.File;
import javax.swing.ImageIcon;

/**
 * 该类主要用户获取头像
 *
 * @author BoluBolu
 */
public final class GetAvatar {
	/**
	 * 获取用户头像
	 *
	 * @param id           用户ID
	 * @param relativePath 头像所在的相对路径
	 * @param avatarUrl    默认头像所在路径
	 * @return 返回用户头像
	 */
	public static ImageIcon getAvatarImage(String id, String relativePath, String avatarUrl) {
		ImageIcon avatar = null;
		try {
			String path = relativePath + id + ".png";
			if (!new File(path).exists()) {
				path = relativePath + id + ".jpg";
				if (!new File(path).exists()) {
					avatar = new ImageIcon("./res/avatar/defaultAvatar.png");
				}
			} else {
				avatar = new ImageIcon(path);
			}
		} catch (Exception e) {
			avatar = new ImageIcon("./res/avatar/defaultAvatar.png");
			System.out.println("获取头像失败，改为默认头像：" + avatarUrl);
		}
		return avatar;
	}
}
