package com.alexecollins.swing.synth.template;

import com.alexecollins.swing.plaf.synth.template.CustomPainter;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class EditorApp {
	public static void main(final String[] args) throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				SynthLookAndFeel synth = new SynthLookAndFeel();
				try {
					synth.load(CustomPainter.class.getResourceAsStream("synth.xml"), CustomPainter.class);
					UIManager.setLookAndFeel(synth);
					final EditorFrame f = new EditorFrame();
					if (args.length == 1) {
						f.load(new File(args[0]));
					}
					f.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
					f.pack();
					f.setVisible(true);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}
