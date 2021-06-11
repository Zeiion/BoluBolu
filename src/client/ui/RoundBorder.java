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
 *
 * @author BoluBolu
 */
public class RoundBorder implements Border {
	
	private Color color;
	private int arcH = 15;
	private int arcW = 15;

	private int a, b, c, d;

	/**
	 * 默认黑色边框、间距
	 */
	public RoundBorder() {
		this(Color.BLACK, 10, 15, 10, 15);
	}

	/**
	 * 传入边框、间距
	 *
	 * @param color 边框颜色
	 * @param a     可以调节光标与边框的距离, 间接影响高度
	 * @param b     可以调节光标与边框的距离
	 * @param c     可以调节光标与边框的距离, 间接影响高度
	 * @param d     可以调节光标与边框的距离
	 */
	public RoundBorder(Color color, int a, int b, int c, int d) {
		this.color = color;
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	@Override public Insets getBorderInsets(Component e) {
		return new Insets(a, b, c, d);
	}

	@Override public boolean isBorderOpaque() {
		return false;
	}

	/**
	 * 改写paintBorder
	 *
	 * @param c      组件
	 * @param g      绘图
	 * @param x      x轴
	 * @param y      y轴
	 * @param width  宽度
	 * @param height 高度
	 */
	@Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

		Graphics2D g2d = (Graphics2D)g.create();

		g2d.setColor(color);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcH, arcW);

		g2d.dispose();
	}
}