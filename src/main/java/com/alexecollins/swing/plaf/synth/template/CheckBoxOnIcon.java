package com.alexecollins.swing.plaf.synth.template;

import java.awt.*;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class CheckBoxOnIcon extends CheckBoxIcon {

    public CheckBoxOnIcon(int size) {
        super(size);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        super.paintIcon(c, g, x, y);
        Graphics2D g2 = (Graphics2D) g;
        int a = CustomPainter.getArc(c);
        g2.setStroke(CustomPainter.THIN_STROKE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawPolyline(new int[]{x + a, x + getIconWidth() / 2, x + getIconWidth()},
                new int[]{y + getIconWidth() / 2, y + getIconHeight() - a, y}, 3);
    }
}
