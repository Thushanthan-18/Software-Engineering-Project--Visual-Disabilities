/**
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 * <p>
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 * @author Dean Ashton, Sergei Krot
 */

package view.toolbars;

import managers.ActionManager;
import managers.ActionManagerAccessibility;
import utils.Resources;

import javax.swing.*;


/**
 * The toolbar used within HEAT
 */
public class Toolbar {
    /* The toolbar */
    private int mode;
    private final JToolBar toolBar = new JToolBar();
    private ActionManager am;

    /* some icons */
    private final ImageIcon iiCompileSuccess = Resources.getIcon("buttonok22");
    private final ImageIcon iiCompileUnknown = Resources.getIcon("buttonquestion22");
    private final ImageIcon iiCompileFail = Resources.getIcon("buttoncancel22");
    private final ImageIcon iiWorking = Resources.getIcon("effect22");

    /* The buttons in use */
    private JButton closeButton = null;
    private JButton compileButton = null;
    private JButton interruptButton = null;
    private JButton testButton = null;
    private JButton tTSButton = null;
    private JButton magnifierButton = null;
    private JButton statusButton = new JButton();

    /**
     * Creates a new Toolbar object.
     */
    public Toolbar(int mode) {
        try {
            this.mode = mode;
            if (mode == 1) {
                ActionManager.initialize(new ActionManagerAccessibility());
                am = ActionManagerAccessibility.getInstance();
                if (am instanceof ActionManagerAccessibility) {
                    tTSButton = new JButton(((ActionManagerAccessibility) am).getTTSAction());
                    magnifierButton = new JButton(((ActionManagerAccessibility) am).getToolbarMagnifierAction());
                }
            } else {
                ActionManager.initialize(new ActionManager());
                am = ActionManager.getInstance();
            }

            createButtons();
            createToolbar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createButtons() {
        closeButton = new JButton(am.getToolbarCloseFileAction());
        compileButton = new JButton(am.getToolbarCompileAction());
        interruptButton = new JButton(am.getToolbarInterruptAction());
        testButton = new JButton(am.getToolbarTestAction());
        statusButton = new JButton();
    }

    /**
     * Creates the toolbar component
     *
     */
    public void createToolbar() {
        toolBar.setFloatable(false);
        toolBar.add(am.getToolbarOpenFileAction());
        toolBar.add(closeButton);
        toolBar.addSeparator();
        toolBar.add(am.getToolbarUndoAction());
        toolBar.add(am.getToolbarRedoAction());
        toolBar.addSeparator();
        toolBar.add(am.getToolbarEditCutAction());
        toolBar.add(am.getToolbarEditCopyAction());
        toolBar.add(am.getToolbarEditPasteAction());
        toolBar.add(am.getToolbarSearchAction());
        toolBar.addSeparator();
        toolBar.add(compileButton);
        toolBar.add(interruptButton);
        toolBar.add(testButton);
        toolBar.addSeparator();
        toolBar.add(am.getToggleTreeAction());
        toolBar.add(am.getToggleOutputAction());
        if (mode == 1) {
            toolBar.add(tTSButton);
            toolBar.add(magnifierButton);
        }
        toolBar.add(javax.swing.Box.createHorizontalGlue());
        toolBar.add(statusButton);
        statusButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        statusButton.setFocusable(false);
        statusButton.setBorderPainted(false);
        statusButton.setContentAreaFilled(false);
        statusButton.setText("Status");
        statusButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        setCompileStatus(1);
    }

    /**
     * Returns the toolbar
     *
     * @return the toolbar object
     */
    public JToolBar getToolBar() {
        return toolBar;
    }

    public void setInterruptEnabled(boolean enabled) {
        interruptButton.setEnabled(enabled);
    }

    public void setTestEnabled(boolean enabled) {
        testButton.setEnabled(enabled);
    }

    public void setCompileEnabled(boolean enabled) {
        compileButton.setEnabled(enabled);
    }

    public void setCloseEnabled(boolean enabled) {
        closeButton.setEnabled(enabled);
    }

    /**
     * Sets the compile status icon
     *
     * @param status compilation status, 0=Fail, 1=Success, 2=Unknown
     */
    public void setCompileStatus(int status) {
        switch (status) {
            case 0:
                statusButton.setIcon(iiCompileFail);
                break;
            case 1:
                statusButton.setIcon(iiCompileSuccess);
                break;
            case 2:
                statusButton.setIcon(iiCompileUnknown);
                break;
            case 3:
                statusButton.setIcon(iiWorking);
                break;
        }
    }

}