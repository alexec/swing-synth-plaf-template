package com.alexecollins.swing.plaf.synth.template;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthConstants;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
import java.awt.*;
import java.io.IOException;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class CustomPainter extends SynthPainter {

	/** Background image for panels. */
	private final Image background;
	static final Stroke THIN_STROKE = new BasicStroke(2);

	public CustomPainter() throws IOException {
	    this.background = ImageIO.read(getClass().getResource("images/background.png"));
	}

    private void paintBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
        Graphics2D g2 = (Graphics2D)g;

        final int arc = getArc(context);
        final boolean isFocused = (context.getComponentState() & SynthConstants.FOCUSED) > 0;

        g2.setColor(context.getStyle().getColor(context, ColorType.BACKGROUND));
        g2.fillRoundRect(x + 1, y + 1, w - 3, h - 3, arc, arc);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setColor(isFocused ? context.getStyle().getColor(context, ColorType.FOCUS) : context.getStyle().getColor(context, ColorType.FOREGROUND));
        g2.setStroke(THIN_STROKE);
        g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }

	private int getArc(SynthContext context) {
	    return getArc(context.getComponent());
	}

	/** Get an arc for the edges of boxes. */
	public static int getArc(Component component) {
	    // lists appear to mess up arcs
	    return component instanceof  JList ? 0 : component.getFont().getSize() / 2;
	}

	/** Paint the component using a gradient based on the two provided colors. */
    public static void paintVerticalGradient(Graphics g, int x, int y, int w, int h, int arc, Color fg, Color bg) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(getGradient(x, y, h, fg, bg));
        g2.fillRoundRect(x, y, w - 1, h - 1, arc, arc);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fg);
        g2.setStroke(THIN_STROKE);
        g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }

    public static GradientPaint getGradient(int x, int y, int h, Color fg, Color bg) {
        return new GradientPaint(x, y, bg, x, y + h, fg);
    }

	private GradientPaint getGradient(SynthContext context, int x, int y, int h) {
		// For simplicity this always recreates the GradientPaint. In a
		// real app you should cache this to avoid garbage.
		return new GradientPaint(x, y, context.getStyle().getColor(context, ColorType.BACKGROUND),
				x, y + h, mix( context.getStyle().getColor(context, ColorType.BACKGROUND),  context.getStyle().getColor(context, ColorType.FOREGROUND)));
	}

	private Color mix(Color a, Color b) {
		return new Color((a.getRed() + b.getRed()) / 2,
				(a.getGreen() + b.getGreen()) / 2,
				(a.getBlue() + b.getBlue()) / 2,
				(a.getAlpha() + b.getAlpha()) / 2);
	}

	private void paintVerticalGradient(SynthContext context, Graphics g, int x, int y, int w, int h) {
        final int arc = getArc(context);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(getGradient(context, x, y, h));
        g2.fillRoundRect(x, y, w - 1, h - 1, arc, arc);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(context.getStyle().getColor(context, ColorType.FOREGROUND));
        g2.setStroke(THIN_STROKE);
        g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }

	/** Make an existing color transparent. */
    private static Color createTransparentColor(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 0x88);
    }

	@Override
	public void paintPanelBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
	    Graphics2D g2 = (Graphics2D)graphics;
	    // tile the image
	    int x1 = x;
	    while (y < h) {
	        x = x1;
	        while (x < w) {
	            g2.drawImage(background, x, y, null);
	            x += background.getWidth(null);
	        }
	        y += background.getHeight(null);
	    }
	}

	@Override
	public void paintToolBarBackground(SynthContext synthContext, Graphics graphics, int i, int i1, int i2, int i3) {
		paintPanelBackground(synthContext, graphics, i, i1, i2, i3);
	}

	@Override
    public void paintTextFieldBackground(SynthContext context,Graphics g, int x, int y,int w, int h) {
        paintBorder(context, g, x, y, w, h);
    }
    
    @Override
    public void paintTextAreaBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics,x ,y ,w ,h);
    }

    @Override
    public void paintPasswordFieldBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics,x ,y ,w ,h);
    }

    @Override
    public void paintListBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics, x, y, w, h);
    }

    @Override
    public void paintEditorPaneBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics,x ,y ,w ,h);
    }

    @Override
    public void paintButtonBackground(SynthContext context,Graphics g, int x, int y,int w, int h) {
        paintVerticalGradient(context, g, x, y, w, h);
    }

    @Override
    public void paintScrollBarThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int i4) {
        Graphics2D g2 = (Graphics2D)g;
        int arc = getArc(context);

        g2.setColor(createTransparentColor(context.getStyle().getColor(context, ColorType.BACKGROUND).darker().darker()));
        g2.fillRoundRect(x + 1, y + 1, w - 2, h - 2, arc, arc);
    }

    @Override
    public void paintComboBoxBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintVerticalGradient(context,graphics,x,y,w,h);
    }

	@Override
    public void paintProgressBarBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
		paintBorder(context, graphics, x, y, w, h);
        paintVerticalGradient(context, graphics, x, y, (int)(w * ((JProgressBar) context.getComponent()).getPercentComplete()), h);
    }

    @Override
    public void paintSpinnerBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics,x ,y ,w ,h);
    }
}