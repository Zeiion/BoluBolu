package client.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.border.Border;

/**
 * 圆角边框
 */
public class RoundBorder implements Border {
	private Color color;

	private int arcH = 15;
	private int arcW = 15;

	public RoundBorder() {
		this(Color.BLACK);
		// 如果实例化时，没有传值，默认是黑色边框
	}

	public RoundBorder(Color color) {
		this.color = color;
	}

	@Override public Insets getBorderInsets(Component c) {

		// top:可以调节光标与边框的距离, 间接影响高度
		// left:可以调节光标与边框的距离
		// bottom:可以调节光标与边框的距离, 间接影响高度
		// right:可以调节光标与边框的距离
		return new Insets(10, 15, 10, 15);
	}

	@Override public boolean isBorderOpaque() {
		return false;
	}

	// 改写Border父类方法
	@Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

		Graphics2D g2d = (Graphics2D)g.create();

		g2d.setColor(color);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcH, arcW);

		g2d.dispose();
	}
}