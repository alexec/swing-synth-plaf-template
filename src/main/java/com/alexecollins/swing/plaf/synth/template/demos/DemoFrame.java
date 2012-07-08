package com.alexecollins.swing.plaf.synth.template.demos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author alex.e.@gmail.com
 */
public class DemoFrame extends JFrame {

    public DemoFrame() {
        setTitle("Swing Preview");

        // use name to target the frame
        getContentPane().setName("Frame");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        final DefaultTableModel model = new DefaultTableModel(new String[]{"Key", "Value"},0);
        for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
           model.addRow(new Object[] {
                   entry.getKey(), entry.getValue()
           });
        }
        final JProgressBar bar1 = new JProgressBar() {{
            setStringPainted(true);
        }};
        // animator for the progress bar
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final int i = bar1.getValue() + 1;
                    bar1.setValue(i % bar1.getMaximum());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }).start();
	    final List<? extends JComponent> components = Arrays.asList(
                new JButton("Button") {{addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JOptionPane.showMessageDialog(DemoFrame.this, "OptionPane");
                    }
                });}},
			    new JButton("Button named 'primary'") {{setName("primary");}},
                new JCheckBox("Checkbox") {{setSelected(true);}},
                new JColorChooser(),
                new JComboBox(new String[] {"ComboBox Item 0", "ComboBox Item 1", "ComboBox Item 2"}),
                new JFileChooser("FileChooser"),
                new JEditorPane() {{setText("EditorPane");}},
                new JLabel("Label"),
                new JList(new String[] {"List Item 0", "List Item 1", "List Item 2", "List Item 3"}) {{setSelectedIndex(1);}},
                new JMenuBar(),
                new JPasswordField("PasswordField"),
                bar1,
			    new JProgressBar() {{
			        setIndeterminate(true);
			        setStringPainted(true);
			        setString("ProgressBar Indeterminate");
			    }},
                new JRadioButton("RadioButton") {{setSelected(true);}},
                new JSlider(),
                new JSpinner(new SpinnerDateModel()),
                new JScrollPane(new JTable(model)),
                new JTextArea("TextArea"),
                new JTextField("TextField"),
                new JToggleButton("ToggleButton"),
                new JToolBar(),
                new JTree()
        );

        for (JComponent c : components) {
            final boolean l = c instanceof JColorChooser || c instanceof JFileChooser || c instanceof JScrollPane;
            final JPanel panel = l ? panel2 : panel1;
	        c.setToolTipText(c.toString());
            panel.add(c);
        }

        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(panel1), new JScrollPane(panel2)));

        pack();
    }
}
