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
 * @author Dean Ashton, Chris Olive, John Travers
 */

package managers;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import utils.Resources;
import view.windows.EditorWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The manager Class responsible for all GUI action commands
 */
public class ActionManagerAccessibility extends ActionManager {

    private static Voice voice;
    private final TTSAction tTSAction = new TTSAction("",
            Resources.getIcon("speakOutloud"), "Text To speech", KeyEvent.VK_Q,
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final MagnifierAction toolbarMagnifierAction = new MagnifierAction(
            "",  // Text of the action
            Resources.getIcon("magnifier22"),  // Icon representing the action
            "Magnify the view",  // What action will do
            null,  // No mnemonic key for mouse-triggered actions
            null  // No accelerator for mouse-triggered actions.
    );


    public ActionManagerAccessibility() {
        super();

        // file/program actions
        getExitProgramAction().putValue(Action.SMALL_ICON, Resources.getIcon("exitHC"));
        getToolbarExitProgramAction().putValue(Action.SMALL_ICON, Resources.getIcon("exitHC"));
        getExitProgramAction().putValue(Action.SMALL_ICON, Resources.getIcon("exitHC"));
        getOpenFileAction().putValue(Action.SMALL_ICON, Resources.getIcon("fileopenHC"));
        getToolbarOpenFileAction().putValue(Action.SMALL_ICON, Resources.getIcon("fileopenHC"));
        getCloseFileAction().putValue(Action.SMALL_ICON, Resources.getIcon("filecloseHC"));
        getToolbarCloseFileAction().putValue(Action.SMALL_ICON, Resources.getIcon("filecloseHC"));
        getPrintFileAction().putValue(Action.SMALL_ICON, Resources.getIcon("fileprintHC"));
        getShowOptionsAction().putValue(Action.SMALL_ICON, Resources.getIcon("listHC"));

        // editing actions
        getUndoAction().putValue(Action.SMALL_ICON, Resources.getIcon("undoHC"));
        getToolbarUndoAction().putValue(Action.SMALL_ICON, Resources.getIcon("undoHC"));
        getRedoAction().putValue(Action.SMALL_ICON, Resources.getIcon("redoHC"));
        getToolbarRedoAction().putValue(Action.SMALL_ICON, Resources.getIcon("redoHC"));
        getSearchAction().putValue(Action.SMALL_ICON, Resources.getIcon("filefindHC"));
        getToolbarSearchAction().putValue(Action.SMALL_ICON, Resources.getIcon("filefindHC"));
        getEditCutAction().putValue(Action.SMALL_ICON, Resources.getIcon("editcutHC"));
        getToolbarEditCutAction().putValue(Action.SMALL_ICON, Resources.getIcon("editcutHC"));
        getEditCopyAction().putValue(Action.SMALL_ICON, Resources.getIcon("editcopyHC"));
        getToolbarEditCopyAction().putValue(Action.SMALL_ICON, Resources.getIcon("editcopyHC"));
        getEditPasteAction().putValue(Action.SMALL_ICON, Resources.getIcon("editpasteHC"));
        getToolbarEditPasteAction().putValue(Action.SMALL_ICON, Resources.getIcon("editpasteHC"));

        // run actions
        getCompileAction().putValue(Action.SMALL_ICON, Resources.getIcon("reloadHC"));
        getToolbarCompileAction().putValue(Action.SMALL_ICON, Resources.getIcon("reloadHC"));
        getInterruptAction().putValue(Action.SMALL_ICON, Resources.getIcon("stopHC"));
        getToolbarInterruptAction().putValue(Action.SMALL_ICON, Resources.getIcon("stopHC"));
        getTestAction().putValue(Action.SMALL_ICON, Resources.getIcon("debugHC"));
        getToolbarTestAction().putValue(Action.SMALL_ICON, Resources.getIcon("debugHC"));

        // help actions
        getShowHelpAction().putValue(Action.SMALL_ICON, Resources.getIcon("helpHC"));
        getShowAboutAction().putValue(Action.SMALL_ICON, Resources.getIcon("infoHC"));
        getRefreshTreeAction().putValue(Action.SMALL_ICON, Resources.getIcon("reloadHC"));
        getExpandTreeAction().putValue(Action.SMALL_ICON, Resources.getIcon("expandTreeWindowHC"));
        getCollapseTreeAction().putValue(Action.SMALL_ICON, Resources.getIcon("collapseTreeWindowHC"));
        getToggleTreeAction().putValue(Action.SMALL_ICON, Resources.getIcon("tree_window_HC"));
        getToggleOutputAction().putValue(Action.SMALL_ICON, Resources.getIcon("output_window_HC"));

        // for the console window:
        getSendEvaluationAction().putValue(Action.SMALL_ICON, Resources.getIcon("effectHC"));
    }

    public TTSAction getTTSAction() {
        return tTSAction;
    }

    public MagnifierAction getToolbarMagnifierAction() {
        return toolbarMagnifierAction;
    }

    protected class TTSAction extends AbstractAction {
        public TTSAction(String text, ImageIcon icon, String desc,
                         Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            VoiceManager voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice("kevin16");

            if (voice != null) {
                voice.allocate();
            } else {
                System.out.println("Voice not found.");
            }

            // Add caret listener to the text area
            if (EditorWindow.jtaCodeView.getSelectedText() != null) {
                speakText(EditorWindow.jtaCodeView.getSelectedText());
            }
        }
    }

    private static void speakText(String text) {
        if (voice != null) {
            voice.speak(text);
        }
    }
}
