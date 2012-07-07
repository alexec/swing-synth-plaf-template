package com.alexecollins.swing.plaf.overide;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.util.Comparator;
import java.util.Formatter;
import java.util.TreeSet;

/**
 * @author alex.e.c@gmail.com
 */
public class OverridingLookAndFeel extends MetalLookAndFeel {

	private static final Font font = new Font("Lucida Sans Unicode", Font.BOLD, 12);
	private static final ColorUIResource foreground = new ColorUIResource(100,25,0);
	private static final ColorUIResource background = new ColorUIResource(255,255,240);

	@Override
	public String getName() {
		return "OverridingLookAndFeel";
	}

	@Override
	public String getID() {
		return "OverridingLookAndFeel";
	}

	@Override
	public String getDescription() {
		return "Extends metal, changing a few options";
	}

	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}

	@Override
	protected void initComponentDefaults(UIDefaults table) {
		table.put("text", foreground);
		table.put("control", background);
		table.put("window", background);

		super.initComponentDefaults(table);

		table.put("Button.font", font);
		table.put("Button.foreground", foreground);

		final TreeSet<Object> objects = new TreeSet<Object>(new Comparator<Object>() {
			@Override
			public int compare(final Object o1, final Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		objects.addAll(table.keySet());
		for (Object x : objects) {
			try {
				System.out.println(new Formatter().format("%40s = %s", x, table.get(x)));
			} catch(Exception e) {
				System.err.println(e);
			}
		}
	}
}
