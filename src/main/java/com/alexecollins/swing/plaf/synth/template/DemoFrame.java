package com.alexecollins.swing.plaf.synth.template;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 *
 * @author alex.e.@gmail.com
 */
class DemoFrame extends JFrame {

    DemoFrame() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        setTitle("Swing Preview");

        // use name to target the frame
        getContentPane().setName("Frame");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        // http://fhe.technikum-wien.at/~poeial/java/javaguidev.pdf
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
        final JProgressBar bar2 = new JProgressBar() {{
            setIndeterminate(true);
            setStringPainted(true);
            setString("ProgressBar Indeterminate");
        }};
        final List<? extends JComponent> components = Arrays.asList(
                new JButton("Button") {{addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JOptionPane.showMessageDialog(DemoFrame.this, "OptionPane");
                    }
                });}},
                new JCheckBox("Checkbox") {{setSelected(true);}},
                new JColorChooser(),
                new JComboBox(new String[] {"ComboBox Item 0", "ComboBox Item 1", "ComboBox Item 2"}),
                new JFileChooser("FileChooser"),
                new JEditorPane() {{setText("EditorPane");}},
                new JLabel("Label"),
                new JList(new String[] {"List Item 0", "List Item 1", "List Item 2", "List Item 3"}),
                new JMenuBar(),
                new JPasswordField("PasswordField"),
                bar1,
                bar2,
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
            c.setToolTipText(createTooltip(c));
            panel.add(c);
        }

        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(panel1), new JScrollPane(panel2)));

        pack();
    }

    private static String createTooltip(JComponent c) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        StringBuilder b = new StringBuilder();
        for (PropertyDescriptor d : Introspector.getBeanInfo(c.getClass()).getPropertyDescriptors()) {
            final Method method = d.getReadMethod();
            if (method != null) {
                final Object v = method.invoke(c, new Object[0]);
                if (v != null)
                    b.append(v).append('\'');
            }
        }
        return b.toString();
    }

}
