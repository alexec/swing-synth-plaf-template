package com.alexecollins.swing.plaf.overide;

import com.alexecollins.swing.plaf.synth.template.demos.DemoFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Display demo components for the PLAF.
 *
 * @author alexec (alex.e.c@gmail.com)
 */
public class OverridingLookAndFeelDemoApp {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(new OverridingLookAndFeel());
                    final DemoFrame f = new DemoFrame();
                    f.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    f.setVisible(true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
