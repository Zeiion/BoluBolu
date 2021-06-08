package client.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * 可以设置背景图片的JPanel
 *
 * @author Zeiion
 */
public class ImagePanel extends JPanel {

	Image img;

	/**
	 * 构造函数制定JPanel的大小
	 */
	public ImagePanel(Image img) {
		this.img = img;
		//大小自适应
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(width, height);
	}

	/**
	 * 画出背景
	 */
	@Override protected void paintComponent(Graphics g) {
		// 清屏
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}