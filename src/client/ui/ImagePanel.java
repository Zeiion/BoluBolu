package client.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 * 可以设置背景图片的JPanel
 *
 * @author BoluBolu
 */
public class ImagePanel extends JPanel {

	/**
	 * 背景图片
	 */
	Image img;

	/**
	 * 构造函数制定JPanel的大小
	 *
	 * @param img 更改后的图片背景
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
	 *
	 * @param g 传入的图形
	 */
	@Override protected void paintComponent(Graphics g) {
		// 清屏
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}