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
    private final Stroke thin = new BasicStroke(2);

    public CustomPainter() throws IOException {
        this.background = ImageIO.read(getClass().getResource("images/background.png"));
    }

    private void paintBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
        // For simplicity this always recreates the GradientPaint. In a
        // real app you should cache this to avoid garbage.
        Graphics2D g2 = (Graphics2D)g;

        // bug in list rendering
        int arc = getArc(context);

        final boolean isFocused = (context.getComponentState() & SynthConstants.FOCUSED) > 0;

        g2.setColor(context.getStyle().getColor(context, ColorType.BACKGROUND));
        g2.fillRoundRect(x + 1, y + 1, w - 3, h - 3, arc, arc);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(createTransparentColor(isFocused ? getHighlight(context) : context.getStyle().getColor(context, ColorType.FOREGROUND)));
        g2.setStroke(thin);
        g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }

    private int getArc(SynthContext context) {
        return context.getComponent() instanceof JList ? 0 : 5;
    }

    private static Insets getInset(SynthContext context) {
        return context.getStyle().getInsets(context,new Insets(0,0,0,0));
    }


    private void paintVerticalGradient(SynthContext context, Graphics g, int x, int y, int w, int h) {
        // http://nadeausoftware.com/node/85
        final Color bg = context.getStyle().getColor(context, ColorType.BACKGROUND);
        final int arc = getArc(context);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(getGradient(context, x, y, h, bg));
        g2.fillRoundRect(x, y, w - 1, h - 1, arc, arc);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(context.getStyle().getColor(context, ColorType.FOREGROUND));
        g2.setStroke(thin);
        g2.drawRoundRect(x, y, w - 1, h - 1, arc, arc);
    }

    private GradientPaint getGradient(SynthContext context, int x, int y, int h, Color bg) {
        final boolean mouseOver = (context.getComponentState() & SynthConstants.MOUSE_OVER) > 0;
        return new GradientPaint(x, y, bg, x, y + h, mouseOver ? getHighlight(context) : bg.darker().darker());
    }

    /** Convenience method for getting highlight color. */
    private static Color getHighlight(SynthContext context) {
        return context.getStyle().getColor(context, ColorType.FOCUS);
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
                g2.drawImage(background, x,y, null);
                x+=background.getWidth(null);
            }
            y+=background.getHeight(null);
        }
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
        g2.setPaint(null);
    }
    @Override
    public void paintComboBoxBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintVerticalGradient(context,graphics,x,y,w,h);
    }

    @Override
    public void paintCheckBoxBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        final Insets i = getInset(context);
        x+=i.top;y+=i.top;h-=i.top+i.bottom;

        paintVerticalGradient(context,graphics,x,y,h,h);

        final boolean isSelected = (context.getComponentState() & SynthConstants.SELECTED) > 0;

        if (isSelected) {
            Graphics2D g2 = (Graphics2D)graphics;
            int a = getArc(context);
            g2.setStroke(thin);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawPolyline(new int[]{x + a, x + h / 2, x + h}, new int[]{y + h / 2, y + h - a, y}, 3);
        }
    }

    @Override
    public void paintRadioButtonBackground(SynthContext context, Graphics g, int x, int y, int w, int h)  {
        final Insets i = getInset(context);

        x+=i.top;y+=i.top;h-=i.top+i.bottom;

        final Color bg = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(getGradient(context, x, y, h, bg));
        g2.fillOval(x, y, h - 1, h - 1);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(context.getStyle().getColor(context, ColorType.FOREGROUND));
        g2.setStroke(thin);
        g2.drawOval(x, y, h - 1, h - 1);
        final boolean isSelected = (context.getComponentState() & SynthConstants.SELECTED) > 0;

        if (isSelected) {
            int a = getArc(context);
            g2.fillOval(x + a, y + a, h - 2*a, h - 2*a);
        }
    }

    @Override
    public void paintProgressBarBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        JProgressBar c = (JProgressBar) context.getComponent();
        paintBorder(context, graphics, x, y, w, h);
        paintVerticalGradient(context, graphics, x, y, (int)(w * c.getPercentComplete()), h);
    }

    @Override
    public void paintSpinnerBackground(SynthContext context, Graphics graphics, int x, int y, int w, int h) {
        paintBorder(context, graphics,x ,y ,w ,h);
    }
}