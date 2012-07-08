package com.alexecollins.swing.plaf.synth.template;

import java.awt.*;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class RadioButtonOnIcon extends RadioButtonIcon {
	public RadioButtonOnIcon(int size) {
		super(size);
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		super.paintIcon(c, g, x, y);
		int a = CustomPainter.getArc(c);
		g.fillOval(x + a, y + a, getIconWidth() - 2*a, getIconHeight() - 2*a);
	}
}
