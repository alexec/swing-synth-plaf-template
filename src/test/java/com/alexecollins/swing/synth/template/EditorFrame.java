package com.alexecollins.swing.synth.template;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * @author alexec (alex.e.c@gmail.com)
 */
public class EditorFrame extends JFrame {

	private final JEditorPane text = new JEditorPane();
	private final String algorithm = "AES";
	private byte[] key;
	private File file;

	private class EditorPanel extends JPanel {
		private EditorPanel() {
			super(new BorderLayout());

			add(new EditorToolBar(), BorderLayout.NORTH);
			add(new JScrollPane(text), BorderLayout.CENTER);
		}
	}

	private class EditorToolBar extends JToolBar {
		private EditorToolBar() {
			add(new JButton("New") {{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						if (!text.getText().isEmpty()) {
							if (JOptionPane.showConfirmDialog(EditorToolBar.this, "Are you sure?", "New",
									JOptionPane.YES_NO_OPTION)
									!= JOptionPane.YES_OPTION) {
								return;
							}
						}
						neu();
					}
				});}
			});
			add(new JButton("Open") {{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						JFileChooser chooser = new JFileChooser();
						if (chooser.showOpenDialog(EditorFrame.this) != JFileChooser.APPROVE_OPTION) {return;}
						try {
							load(chooser.getSelectedFile());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(EditorFrame.this,
									"Failed to open file:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});}
			});
			add(new JButton("Save") {{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						if (file == null) {
							JFileChooser chooser = new JFileChooser();
							if (chooser.showSaveDialog(EditorFrame.this) != JFileChooser.APPROVE_OPTION) {return;}
							file = chooser.getSelectedFile();
						}
						try {
							save();
						} catch (Exception e) {
							JOptionPane.showMessageDialog(EditorFrame.this,
									"Failed to save file:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
			}});
			add(new JButton("About") {{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						JOptionPane.showMessageDialog(EditorFrame.this,
								"This app encrypts your files using " + algorithm);
					}
				});
			}});
		}
	}

	// new document
	private void neu() {
		file = null;
		key = null;
		text.setText("");
		setTitle("Untitled");
	}

	public void load(File file) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
		requestKey();
		SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		BufferedReader in = new BufferedReader(new InputStreamReader(new CipherInputStream(new BufferedInputStream(new FileInputStream(file)), cipher)));
		try {
			StringBuilder b = new StringBuilder();
			String l;
			while ((l = in.readLine()) != null) {
				b.append(l).append('\n');
			}
			text.setText(b.toString());
		} finally {
			in.close();
		}
		setTitle(file.toString());
	}

	private void requestKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
		// TODO - hide password
		char[] password = JOptionPane.showInputDialog(this, "Please enter secret password").toCharArray();
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] salt = new byte[] {1,2,3,4}; // TODO - new salt
		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
		key = factory.generateSecret(spec).getEncoded();

	}

	private void save() throws FileNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
		if (key == null) {requestKey();}
		SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		PrintWriter out = new PrintWriter(new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(file)), cipher));
		try {
			out.print(text.getText());
			out.flush();
		} finally {
			out.close();
		}
	}

	@Override
	public void setTitle(String s) {
		super.setTitle(s + " - " + algorithm + " - Editor");
	}

	public EditorFrame() {
		super();
		setMinimumSize(new Dimension(400, 300));
		getContentPane().add(new EditorPanel());
		setTitle("Untitled");
	}
}
