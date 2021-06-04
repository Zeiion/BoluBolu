package client.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * 滚动条
 *
 * @author Zeiion
 */
public final class ScrollBar extends BasicScrollBarUI {

	/**
	 * 滑道颜色与滚动条宽度
	 */
	@Override protected void configureScrollBarColors() {
		// 滑道
		trackColor = new Color(0, 0, 0, 0);

		// 滚动条宽度
		scrollBarWidth = 10;
	}

	@Override protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		super.paintTrack(g, c, trackBounds);
	}

	/**
	 * 滚动条UI
	 */
	@Override protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		// 重写父类方法，如果不加这一句无法拖动滑动条
		g.translate(thumbBounds.x, thumbBounds.y);

		// 设置把手颜色
		g.setColor(Color.black);

		// 消除锯齿
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.addRenderingHints(rh);

		// 设置半透明效果
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));

		// 填充圆角矩形
		g2.fillRoundRect(0, 0, 10, thumbBounds.height - 1, 15, 15);
	}

	/**
	 * 隐藏向下按钮
	 */
	@Override protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton();

		// 使按钮不显示
		button.setVisible(false);
		return button;
	}

	/**
	 * 隐藏向上按钮
	 */
	@Override protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton();
		button.setVisible(false);
		return button;
	}
}
