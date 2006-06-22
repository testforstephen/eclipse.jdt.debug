/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.debug.ui;


import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Assert;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;

/**
 * Utility class to simplify access to some SWT resources. 
 * @since 3.3
 */
public class SWTUtil {
		
	/**
	 * Returns a width hint for a button control.
	 */
	public static int getButtonWidthHint(Button button) {
		button.setFont(JFaceResources.getDialogFont());
		PixelConverter converter= new PixelConverter(button);
		int widthHint= converter.convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		return Math.max(widthHint, button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}
	
	/**
	 * Sets width and height hint for the button control.
	 * <b>Note:</b> This is a NOP if the button's layout data is not
	 * an instance of <code>GridData</code>.
	 * 
	 * @param	the button for which to set the dimension hint
	 */		
	public static void setButtonDimensionHint(Button button) {
		Assert.isNotNull(button);
		Object gd= button.getLayoutData();
		if (gd instanceof GridData) {
			((GridData)gd).widthHint= getButtonWidthHint(button);	
			((GridData)gd).horizontalAlignment = GridData.FILL;	 
		}
	}		
	
	
	/**
	 * Creates and returns a new push button with the given
	 * label and/or image.
	 * 
	 * @param parent parent control
	 * @param label button label or <code>null</code>
	 * @param image image of <code>null</code>
	 * 
	 * @return a new push button
	 */
	public static Button createPushButton(Composite parent, String label, Image image) {
		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());
		if (image != null) {
			button.setImage(image);
		}
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);	
		SWTUtil.setButtonDimensionHint(button);
		return button;	
	}	

	/**
	 * Creates and returns a new radio button with the given
	 * label.
	 * 
	 * @param parent parent control
	 * @param label button label or <code>null</code>
	 * 
	 * @return a new radio button
	 */
	public static Button createRadioButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.RADIO);
		button.setFont(parent.getFont());
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);	
		SWTUtil.setButtonDimensionHint(button);
		return button;	
	}	
	
	/**
	 * Creates a checked button
	 * @param parent the parent composite to add this button to
	 * @param label the label for the button
	 * @param checked the button's checked state
	 * @return a new check button
	 */
	public static Button createCheckButton(Composite parent, String label, boolean checked) {
		Button button = new Button(parent, SWT.CHECK);
		button.setFont(parent.getFont());
		if (label != null) {
			button.setText(label);
		}
		GridData gd = new GridData();
		button.setLayoutData(gd);	
		button.setSelection(checked);
		return button;	
	}
	
	/**
	 * Creates a new label widget
	 * @param parent the parent composite to add this label widget to
	 * @param text the text for the label
	 * @param hspan the horizontal span to take up in the parent composite
	 * @return the new label
	 */
	public static Label createLabel(Composite parent, String text, Font font, int hspan) {
		Label l = new Label(parent, SWT.NONE);
		l.setFont(font);
		l.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = hspan;
		l.setLayoutData(gd);
		return l;
	}
	
	/**
	 * Creates a new text widget 
	 * @param parent the parent composite to add this text widget to
	 * @param hspan the horizontal span to take up on the parent composite
	 * @return the new text widget
	 */
	public static Text createSingleText(Composite parent, int hspan) {
    	Text t = new Text(parent, SWT.SINGLE | SWT.BORDER);
    	t.setFont(parent.getFont());
    	GridData gd = new GridData(GridData.FILL_HORIZONTAL);
    	gd.horizontalSpan = hspan;
    	t.setLayoutData(gd);
    	return t;
    }
	
	/**
	 * Creates a Group widget
	 * @param parent the parent composite to add this group to
	 * @param text the text for the heading of the group
	 * @param columns the number of columns within the group
	 * @param hspan the horizontal span the group should take up on the parent
	 * @param fill the style for how this composite should fill into its parent
	 * @return the new group
	 */
	public static Group createGroup(Composite parent, String text, int columns, int hspan, int fill) {
    	Group g = new Group(parent, SWT.NONE);
    	g.setLayout(new GridLayout(columns, false));
    	g.setText(text);
    	g.setFont(parent.getFont());
    	GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
    	g.setLayoutData(gd);
    	return g;
    }
	
	/**
	 * This method allows us to open the preference dialog on the specific page, in this case the perspective page
	 * @param id the id of pref page to show
	 * @param page the actual page to show
	 */
	public static void showPreferencePage(String id, IPreferencePage page) {
		final IPreferenceNode targetNode = new PreferenceNode(id, page);
		PreferenceManager manager = new PreferenceManager();
		manager.addToRoot(targetNode);
		final PreferenceDialog dialog = new PreferenceDialog(JDIDebugUIPlugin.getActiveWorkbenchShell(), manager);
		BusyIndicator.showWhile(JDIDebugUIPlugin.getStandardDisplay(), new Runnable() {
			public void run() {
				dialog.create();
				dialog.setMessage(targetNode.getLabelText());
				dialog.open();
			}
		});		
	}
	
	/**
	 * Creates a Composite widget
	 * @param parent the parent composite to add this composite to
	 * @param columns the number of columns within the composite
	 * @param hspan the horizontal span the composite should take up on the parent
	 * @param fill the style for how this composite should fill into its parent
	 * @return the new group
	 */
	public static Composite createComposite(Composite parent, Font font, int columns, int hspan, int fill) {
    	Composite g = new Composite(parent, SWT.NONE);
    	g.setLayout(new GridLayout(columns, false));
    	g.setFont(font);
    	GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
    	g.setLayoutData(gd);
    	return g;
    }
	
	/**
	 * creates a vertical spacer for seperating components
	 * @param comp
	 * @param numlines
	 */
	public static void createVerticalSpacer(Composite comp, int numlines) {
		Label lbl = new Label(comp, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = numlines;
		lbl.setLayoutData(gd);
	}
	
	/**
	 * Creates an ExpandibleComposite widget
	 * @param parent the parent to add this widget to
	 * @param style the style for ExpandibleComposite expanding handle, and layout
	 * @param label the label for the widget
	 * @param hspan how many columns to span in the parent
	 * @param fill the fill style for the widget
	 * @return a new ExpandibleComposite widget
	 */
	public static ExpandableComposite createExpandibleComposite(Composite parent, int style, String label, int hspan, int fill) {
		ExpandableComposite ex = new ExpandableComposite(parent, SWT.NONE, style);
		ex.setText(label);
		ex.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		ex.setLayoutData(gd);
		return ex;
	}
	
}
