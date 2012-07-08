package com.alexecollins.swing.plaf.synth.template;

import javax.swing.*;
import java.awt.*;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public abstract class RadioButtonIcon implements Icon {
	private final int size;

	public RadioButtonIcon(int size) {
		this.size = size;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {;
		Graphics2D g2 = (Graphics2D)g;
		Color fg = c.getForeground();
		Color bg = c.getBackground();
		g2.setPaint(CustomPainter.getGradient(x+1, y+1, getIconHeight()-2, fg, bg));
		g2.fillOval(x, y, getIconWidth(), getIconHeight());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(fg);
		g2.setStroke(CustomPainter.THIN_STROKE);
		g2.drawOval(x+1, y+1, getIconWidth()-3, getIconHeight()-3);
	}

	@Override
	public int getIconWidth() {
		return size;
	}

	@Override
	public int getIconHeight() {
		return size;
	}
}
