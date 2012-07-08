package com.alexecollins.swing.plaf.synth.template.demos;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class LoginFrame extends JFrame {
	private static class FormPanel extends JPanel {
		FormPanel() {
			super(new GridBagLayout());

			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1.0;
			c.insets = new Insets(5,5,5,5);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = WEST;
			c.gridx = 0;
			c.gridy = 0;
			add(new JLabel("Username"), c);
			c.gridx = 1;
			add(new JTextField(), c);
			c.gridx = 0;
			c.gridy = 1;
			add(new JLabel("Password"), c);
			c.gridx = 1;
			add(new JPasswordField(), c);
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 2;
			add(new JPanel() {{
				add(new JButton("Login") {{
					setName("primary");
				}});
				add(new JButton("Cancel"));
			}}, c);
		}
	}

	public LoginFrame() {
		setMinimumSize(new Dimension(300, 100));
		setResizable(false);
		getContentPane().add(new JPanel(new BorderLayout()) {{
			setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			add(new JLabel("Welcome") {{
				setName("title");
			}}, BorderLayout.NORTH);
			add(new FormPanel(), BorderLayout.CENTER);
		}});
	}
}
