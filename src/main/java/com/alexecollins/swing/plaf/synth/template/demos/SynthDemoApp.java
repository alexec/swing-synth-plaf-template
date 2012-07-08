package com.alexecollins.swing.plaf.synth.template.demos;

import com.alexecollins.swing.plaf.synth.template.CustomPainter;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Display a page of the various components that you can use is swing.
 * @author alexec (alex.e.c@gmail.com)
 */
public class SynthDemoApp {

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
	        @Override
	        public void run() {
		        SynthLookAndFeel synth = new SynthLookAndFeel();
		        try {
			        synth.load(CustomPainter.class.getResourceAsStream("synth.xml"), CustomPainter.class);
			        UIManager.setLookAndFeel(synth);
			        final DemoFrame f = new DemoFrame();
			        f.addWindowListener(new WindowAdapter() {
				        @Override
				        public void windowClosing(WindowEvent e) {
					        System.exit(0);
				        }
			        });
			        f.pack();
			        f.setVisible(true);
			        UIDefaults x = UIManager.getLookAndFeel().getDefaults();
			        for (Object y : x.keySet()) {
				        System.out.println(y + " = " + x.get(y));
			        }

		        } catch (Exception e) {
			        throw new RuntimeException(e);
		        }

	        }
        });
    }

}
