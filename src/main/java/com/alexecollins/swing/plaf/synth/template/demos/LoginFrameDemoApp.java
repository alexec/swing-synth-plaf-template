package com.alexecollins.swing.plaf.synth.template.demos;

import com.alexecollins.swing.plaf.synth.template.CustomPainter;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class LoginFrameDemoApp {
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				SynthLookAndFeel synth = new SynthLookAndFeel();
				try {
					synth.load(CustomPainter.class.getResourceAsStream("synth.xml"), CustomPainter.class);
					UIManager.setLookAndFeel(synth);
					final Frame f = new LoginFrame();
					f.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
					f.pack();
					f.setLocationRelativeTo(null);
					f.setVisible(true);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

			}
		});
	}
}
