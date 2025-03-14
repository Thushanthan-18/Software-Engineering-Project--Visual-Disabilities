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

import utils.HaskellFilter;
import utils.InterpreterParser;
import utils.Resources;
import utils.Settings;
import utils.jsyntax.JEditTextArea;
import view.windows.ConsoleWindow;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Logger;

/**
 * The manager Class responsible for all GUI action commands
 */
public class ActionManager {
    private static final Logger log = Logger.getLogger("heat");
    private static ActionManager instance = null;
    private final File selectedFile = null;
    private static JEditTextArea jtaCodeView;

    /* Static instantiation of our custom Actions */

    // file/program actions

    private final ExitProgramAction exitProgramAction = new ExitProgramAction("Quit",
            Resources.getIcon("exit22"), "Quit HEAT", KeyEvent.VK_Q,
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final ExitProgramAction toolbarExitProgramAction = new ExitProgramAction(null,
            Resources.getIcon("exit22"), "Quit HEAT", KeyEvent.VK_Q,
            KeyStroke.getKeyStroke(KeyEvent.VK_Q, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final OpenFileAction openFileAction = new OpenFileAction("Open..",
            Resources.getIcon("fileopen22"), "Open an existing or new file in the editor", KeyEvent.VK_O,
            KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final OpenFileAction toolbarOpenFileAction = new OpenFileAction(null,
            Resources.getIcon("fileopen22"), "Open an existing or new file in the editor", KeyEvent.VK_O,
            KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final CloseFileAction closeFileAction = new CloseFileAction("Close",
            Resources.getIcon("fileclose22"), "Save file and close editor", null, null);
    private final CloseFileAction toolbarCloseFileAction = new CloseFileAction(null,
            Resources.getIcon("fileclose22"), "Save file and close editor", null, null);
    private final PrintFileAction printFileAction = new PrintFileAction("Print",
            Resources.getIcon("fileprint22"), "Print editor content or interpreter console", KeyEvent.VK_P,
            KeyStroke.getKeyStroke(KeyEvent.VK_P, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final ShowOptionsAction showOptionsAction = new ShowOptionsAction("Options",
            Resources.getIcon("list22"), "Change HEAT Options", KeyEvent.VK_D,
            KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));


    // editing actions
    private final UndoAction undoAction = new UndoAction("Undo", Resources.getIcon("undo22"),
            "Undo last change", KeyEvent.VK_Z,
            KeyStroke.getKeyStroke(KeyEvent.VK_Z, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final UndoAction toolbarUndoAction = new UndoAction(null, Resources.getIcon("undo22"),
            "Undo last change", KeyEvent.VK_Z,
            KeyStroke.getKeyStroke(KeyEvent.VK_Z, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final RedoAction redoAction = new RedoAction("Redo", Resources.getIcon("redo22"),
            "Redo last change", KeyEvent.VK_Y,
            KeyStroke.getKeyStroke(KeyEvent.VK_Y, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final RedoAction toolbarRedoAction = new RedoAction(null, Resources.getIcon("redo22"),
            "Redo last change", KeyEvent.VK_Y,
            KeyStroke.getKeyStroke(KeyEvent.VK_Y, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final ShowSearchAction showSearchAction = new ShowSearchAction("Find",
            Resources.getIcon("filefind22"), "Find text in the program", KeyEvent.VK_F,
            KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final ShowSearchAction toolbarSearchAction = new ShowSearchAction(null,
            Resources.getIcon("filefind22"), "Find text in the program", KeyEvent.VK_F,
            KeyStroke.getKeyStroke(KeyEvent.VK_F, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditCutAction editCutAction = new EditCutAction("Cut",
            Resources.getIcon("editcut22"), "Cut selected text", KeyEvent.VK_X,
            KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditCutAction toolbarEditCutAction = new EditCutAction(null,
            Resources.getIcon("editcut22"), "Cut selected text", KeyEvent.VK_X,
            KeyStroke.getKeyStroke(KeyEvent.VK_X, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditCopyAction editCopyAction = new EditCopyAction("Copy",
            Resources.getIcon("editcopy22"), "Copy selected text", KeyEvent.VK_C,
            KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditCopyAction toolbarEditCopyAction = new EditCopyAction(null,
            Resources.getIcon("editcopy22"), "Copy selected text", KeyEvent.VK_C,
            KeyStroke.getKeyStroke(KeyEvent.VK_C, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditPasteAction editPasteAction = new EditPasteAction("Paste",
            Resources.getIcon("editpaste22"), "Paste selected text", KeyEvent.VK_V,
            KeyStroke.getKeyStroke(KeyEvent.VK_V, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final EditPasteAction toolbarEditPasteAction = new EditPasteAction(null,
            Resources.getIcon("editpaste22"), "Paste selected text", KeyEvent.VK_V,
            KeyStroke.getKeyStroke(KeyEvent.VK_V, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));

    // run actions
    private final CompileAction compileAction = new CompileAction(null,
            Resources.getIcon("reload22"), "Load & compile program", KeyEvent.VK_L,
            KeyStroke.getKeyStroke(KeyEvent.VK_L, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final CompileAction toolbarCompileAction = new CompileAction(null,
            Resources.getIcon("reload22"), "Load program into interpreter and compile it", KeyEvent.VK_L,
            KeyStroke.getKeyStroke(KeyEvent.VK_L, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final InterruptAction interruptAction = new InterruptAction(null,
            Resources.getIcon("stop22"), "Interrupt interpreter", KeyEvent.VK_I,
            KeyStroke.getKeyStroke(KeyEvent.VK_I, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final InterruptAction toolbarInterruptAction = new InterruptAction(null,
            Resources.getIcon("stop22"), "Interrupt interpreter", KeyEvent.VK_I,
            KeyStroke.getKeyStroke(KeyEvent.VK_I, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final TestAction testAction = new TestAction(null,
            Resources.getIcon("debug22"), "Check properties", KeyEvent.VK_T,
            KeyStroke.getKeyStroke(KeyEvent.VK_T, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final TestAction toolbarTestAction = new TestAction(null,
            Resources.getIcon("debug22"), "Check properties", KeyEvent.VK_T,
            KeyStroke.getKeyStroke(KeyEvent.VK_T, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));

    // help actions
    private final ShowHelpAction showHelpAction = new ShowHelpAction("Help",
            Resources.getIcon("help22"), "Display help", KeyEvent.VK_L,
            KeyStroke.getKeyStroke(KeyEvent.VK_H, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final ShowAboutAction showAboutAction = new ShowAboutAction("About",
            Resources.getIcon("info22"), "Display about information", null, null);


    private final RefreshTreeAction refreshTreeAction = new RefreshTreeAction("", Resources.getIcon("reload22"),
            "Refresh overview");
    private final ExpandTreeAction expandTreeAction = new ExpandTreeAction("", Resources.getIcon("expandTreeWindow22"),
            "Expand all overview elements");
    private final CollapseTreeAction collapseTreeAction = new CollapseTreeAction("", Resources.getIcon("collapseTreeWindow22"),
            "Collapse all overview elements");
    private final ToggleTreeAction toggleTreeAction = new ToggleTreeAction(null, Resources.getIcon("tree_window_22"),
            "Show/hide overview");
    private final ToggleConsoleAction toggleOutputAction = new ToggleConsoleAction(null, Resources.getIcon("output_window_22"),
            "Show/hide interpreter console");

    // for the console window:
    private final SendEvaluationAction sendEvaluationAction = new SendEvaluationAction("Send",
            Resources.getIcon("effect22"), "Sends Evaluation to Interpreter", KeyEvent.VK_E,
            KeyStroke.getKeyStroke(KeyEvent.VK_E, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final GoToPastConsoleHistory goToPastConsoleHistory = new GoToPastConsoleHistory();
    private final GoToRecentConsoleHistory goToRecentConsoleHistory = new GoToRecentConsoleHistory();

    private final SaveOptionsAction saveOptionsAction = new SaveOptionsAction("Apply",
            Resources.getIcon(""), "Apply options", KeyEvent.VK_S,
            KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
    private final SaveWizardAction saveWizardAction = new SaveWizardAction("Continue",
            Resources.getIcon(""), "Save path and continue", KeyEvent.VK_S,
            KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));


    public ActionManager() {
        /* Prevent instantiation */
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public static void initialize(ActionManager actionManagerInstance) {
        instance = actionManagerInstance;
    }

    /**
     * Returns an instance of the singleton class ActionManager
     * @return ActionManager instance
     */
    public static ActionManager getInstance() {
        if (instance == null) {
            //throw new IllegalStateException("Instance not initialized. Call initialize() first.");
            instance = new ActionManager();
        }

        return instance;
    }

    /* Getters for the Action objects */

    public OpenFileAction getOpenFileAction() {
        return openFileAction;
    }

    public CloseFileAction getCloseFileAction() {
        return closeFileAction;
    }

    public CloseFileAction getToolbarCloseFileAction() {
        return toolbarCloseFileAction;
    }

    public EditCopyAction getEditCopyAction() {
        return editCopyAction;
    }

    public EditCopyAction getToolbarEditCopyAction() {
        return toolbarEditCopyAction;
    }

    public EditCutAction getEditCutAction() {
        return editCutAction;
    }

    public EditCutAction getToolbarEditCutAction() {
        return toolbarEditCutAction;
    }

    public EditPasteAction getEditPasteAction() {
        return editPasteAction;
    }

    public EditPasteAction getToolbarEditPasteAction() {
        return toolbarEditPasteAction;
    }

    public ExitProgramAction getExitProgramAction() {
        return exitProgramAction;
    }

    public PrintFileAction getPrintFileAction() {
        return printFileAction;
    }

    public CompileAction getCompileAction() {
        return compileAction;
    }

    public CompileAction getToolbarCompileAction() {
        return toolbarCompileAction;
    }

    public SaveOptionsAction getSaveOptionsAction() {
        return saveOptionsAction;
    }

    public SaveWizardAction getSaveWizardAction() {
        return saveWizardAction;
    }

    public SendEvaluationAction getSendEvaluationAction() {
        return sendEvaluationAction;
    }

    public ShowAboutAction getShowAboutAction() {
        return showAboutAction;
    }

    public ShowHelpAction getShowHelpAction() {
        return showHelpAction;
    }

    public ShowOptionsAction getShowOptionsAction() {
        return showOptionsAction;
    }

    public ExitProgramAction getToolbarExitProgramAction() {
        return toolbarExitProgramAction;
    }

    public OpenFileAction getToolbarOpenFileAction() {
        return toolbarOpenFileAction;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public UndoAction getToolbarUndoAction() {
        return toolbarUndoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public RedoAction getToolbarRedoAction() {
        return toolbarRedoAction;
    }

    public ShowSearchAction getSearchAction() {
        return showSearchAction;
    }

    public ShowSearchAction getToolbarSearchAction() {
        return toolbarSearchAction;
    }

    public RefreshTreeAction getRefreshTreeAction() {
        return refreshTreeAction;
    }

    public ExpandTreeAction getExpandTreeAction() {
        return expandTreeAction;
    }

    public CollapseTreeAction getCollapseTreeAction() {
        return collapseTreeAction;
    }

    public ToggleTreeAction getToggleTreeAction() {
        return toggleTreeAction;
    }

    public ToggleConsoleAction getToggleOutputAction() {
        return toggleOutputAction;
    }

    public TestAction getTestAction() {
        return testAction;
    }

    public TestAction getToolbarTestAction() {
        return toolbarTestAction;
    }

    public InterruptAction getInterruptAction() {
        return interruptAction;
    }

    public InterruptAction getToolbarInterruptAction() {
        return toolbarInterruptAction;
    }

    public GoToPastConsoleHistory getGoToPastConsoleHistory() {
        return goToPastConsoleHistory;
    }

    public GoToRecentConsoleHistory getGoToRecentConsoleHistory() {
        return goToRecentConsoleHistory;
    }

    /* The Action SubClasses Follow  */
  /*
    protected class NewFileAction extends AbstractAction {
        public NewFileAction(String text, ImageIcon icon, String desc,
        Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            InterpreterManager im = InterpreterManager.getInstance();

            if (wm.getEditorWindow().getSavedModStatus())
            if (SystemDialogs.getInstance().confirmSave())
              saveFileAction.actionPerformed(e);

            wm.getEditorWindow().clearLineMark();
            wm.getEditorWindow().setEditorContent("");

            FileManager fm = FileManager.getInstance();
            // fm.saveTemporary();
            fm.setCurrentFile(null);
            wm.setTitleFileName(null);
            wm.getEditorWindow().setModifiedStatus(false);
            wm.getEditorWindow().setSavedModStatus(false);
            wm.setSaveEnabled(false);
            wm.setCloseEnabled(false);
            //wm.setDecButtonsEnabled(true);
            wm.getEditorWindow().changeTokenMarker(false);

            wm.getConsoleWindow().restart();

            ParserManager.getInstance().refresh();
            wm.getTreeWindow().refreshTree();
            wm.hideTree();

            //clear the undo/redo manager
            UndoManager.getInstance().discardAllEdits();
            getUndoAction().updateUndoState();
            getRedoAction().updateRedoState();
            getToolbarUndoAction().updateUndoState();
            getToolbarRedoAction().updateRedoState();
        }
    }  end NewAction */

    /**
     * The action that is invoked by the Run Properties button
     *
     * @author ii23
     *
     */
    protected class TestAction extends AbstractAction {
        public TestAction(String text, ImageIcon icon, String desc,
                          Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent arg0) {
            WindowManager wm = WindowManager.getInstance();
            if (!wm.isTestEnabled()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            InterpreterManager im = InterpreterManager.getInstance();
            InterpreterParser ip = InterpreterParser.getInstance();
            ParserManager pm = ParserManager.getInstance();
            if (ParserManager.getParser().getTests().size() > 0) {
                wm.getTreeWindow().runTests();
            }
        }
    }

    protected class ExitProgramAction extends AbstractAction {
        public ExitProgramAction(String text, ImageIcon icon, String desc,
                                 Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            FileManager fm = FileManager.getInstance();
            fm.ensureSaved();
            System.exit(0);
      /*
      // FileManager.getInstance().deleteTemporaryFile();
      WindowManager wm = WindowManager.getInstance();
      SettingsManager sm = SettingsManager.getInstance();
      InterpreterManager im = InterpreterManager.getInstance();
      if (sm.isHaveChanges())
        sm.saveSettings();
      if (wm.getEditorWindow().getSavedModStatus()) {
        int result = SystemDialogs.getInstance().showExit();
        if (result == 0) {
          saveFileAction.actionPerformed(e);
          System.exit(0);
        }
        if (result == 1)
          System.exit(0);
      } else
        System.exit(0);
       *
       */
        }
    } /* end ExitProgramAction */

    protected class OpenFileAction extends AbstractAction {
        public OpenFileAction(String text, ImageIcon icon, String desc,
                              Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            FileManager fm = FileManager.getInstance();

            fm.ensureSaved();
      /*
      if (wm.getEditorWindow().getSavedModStatus())
        if (SystemDialogs.getInstance().confirmSave())
          saveFileAction.actionPerformed(e);
       */

            HaskellFilter haskellFilter = new HaskellFilter();

            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open an existing or create a new Haskell File");
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setCurrentDirectory(fm.getOpenDirectory());
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.setFileFilter(haskellFilter);

            int result = jfc.showDialog(wm.getMainScreenFrame(), "Open/Create");

            if (result == JFileChooser.CANCEL_OPTION) {
            } else if (result == JFileChooser.APPROVE_OPTION) {
                fm.setOpenDirectory(jfc.getCurrentDirectory());
                wm.openFile(jfc.getSelectedFile());
            } else
                JOptionPane.showMessageDialog(null, "Error opening file!",
                        "File Open Error", JOptionPane.ERROR_MESSAGE);
        }

    } /* end OpenFileAction */

  /*
  protected class SaveAsFileAction extends AbstractAction {
    public SaveAsFileAction(String text, ImageIcon icon, String desc,
      Integer mnemonic, KeyStroke accelerator) {
      super(text, icon);
      putValue(SHORT_DESCRIPTION, desc);
      putValue(MNEMONIC_KEY, mnemonic);
      putValue(ACCELERATOR_KEY, accelerator);
    }

    public void actionPerformed(ActionEvent e) {
      WindowManager wm = WindowManager.getInstance();
      HaskellFilter haskellFilter = new HaskellFilter();
      JFileChooser jfcs = new JFileChooser();
      jfcs.setDialogTitle("Save File");
      jfcs.setFileSelectionMode(JFileChooser.FILES_ONLY);
      jfcs.setCurrentDirectory(new File("."));
      jfcs.setFileFilter(haskellFilter);

      File file = null;
      String path = null;
      boolean fileConfirmed = false;

      // Loop in case the file exists, and they don't want to overwrite
      do {
        int result = jfcs.showSaveDialog(wm.getMainScreenFrame());

        if (result == JFileChooser.CANCEL_OPTION)
          return;
        else if (result == JFileChooser.APPROVE_OPTION) {
          file = jfcs.getSelectedFile();
          path = file.getAbsolutePath();

          // File already exists
          if (file.exists()) {
            JOptionPane jop = new JOptionPane();

            result = jop.showConfirmDialog(wm.getMainScreenFrame(),
                "The file " + file + " already exists, Overwrite ?", "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.CANCEL_OPTION)
              return;

            if (result == JOptionPane.YES_OPTION)
              fileConfirmed = true;

            if (result == JOptionPane.NO_OPTION)
              fileConfirmed = false;
          } else
            fileConfirmed = true;
        }
      } while (!fileConfirmed);

      // File has been chosen, and confirmed continue with write
      if (!(path.endsWith(".hs") || path.endsWith(".lhs") ||
          path.endsWith(".HS") || path.endsWith(".LHS"))) {
        path += ".hs";
        file = new File(path);
      }

      String content = wm.getEditorWindow().getEditorContent();
      FileManager fm = FileManager.getInstance();
      fm.writeFile(file, content);
      fm.setCurrentFile(file);
      wm.getEditorWindow().setModifiedStatus(false);
      wm.getEditorWindow().setSavedModStatus(false);
      wm.setSaveEnabled(false);
      wm.setCloseEnabled(true);
      fm.setCurrentFile(file);
      wm.setTitleFileName(file.getAbsolutePath());

      if (fm.getFileType())
        wm.getEditorWindow().changeTokenMarker(true);
      else
        wm.getEditorWindow().changeTokenMarker(false);
    }
  }  end SaveAsFileAction */

  /*
  protected class SaveFileAction extends AbstractAction {
    public SaveFileAction(String text, ImageIcon icon, String desc,
      Integer mnemonic, KeyStroke accelerator) {
      super(text, icon);
      putValue(SHORT_DESCRIPTION, desc);
      putValue(MNEMONIC_KEY, mnemonic);
      putValue(ACCELERATOR_KEY, accelerator);
    }

    public void actionPerformed(ActionEvent e) {
      FileManager fm = FileManager.getInstance();
      File currentFile = fm.getCurrentFile();
      WindowManager wm = WindowManager.getInstance();

      if (currentFile != null) {
        String content = wm.getEditorWindow().getEditorContent();
        fm.writeFile(currentFile, content);
        wm.getEditorWindow().setModifiedStatus(false);
        wm.getEditorWindow().setSavedModStatus(false);
        wm.setSaveEnabled(false);
      } else
        saveAsFileAction.actionPerformed(e);
    }
  }  end SaveFileAction */


    //
    public class MagnifierAction extends AbstractAction {


        public MagnifierAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            MagnifierManager mm = new MagnifierManager();
            mm.execute();
        }
    }

    /**
     * SendEvaluationAction is called whenever Enter is keyed in the
     * console window. Thus, it is used for sending an expression/command
     * to the interpreter and when sending input to an interactive Haskell program.
     */

    protected class SendEvaluationAction extends AbstractAction {
        public SendEvaluationAction(String text, ImageIcon icon, String desc,
                                    Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            if (wm.getConsoleWindow().isEnabled()) {
                String command = wm.getConsoleWindow().getCommand();
                log.info("Send Command: " + command);
                if (!wm.isEvaluating()) {
                    wm.setStatusEvaluating();
                }
                wm.getConsoleWindow().outputInput('\n', true);
                wm.getConsoleWindow().evalCommand(command);
            } else
                java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    protected class ShowOptionsAction extends AbstractAction {
        public ShowOptionsAction(String text, ImageIcon icon, String desc,
                                 Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.showOptionsWindow();
        }
    } /* end ShowOptionsAction */

    protected class ShowSearchAction extends AbstractAction {
        public ShowSearchAction(String text, ImageIcon icon, String desc,
                                Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.showSearchWindow();
        }
    } /* end ShowOptionsAction */

    protected class ShowAboutAction extends AbstractAction {
        public ShowAboutAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.showAboutWindow();
        }
    } /* end ShowAboutAction */

    protected class SaveOptionsAction extends AbstractAction {
        public SaveOptionsAction(String text, ImageIcon icon, String desc,
                                 Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            boolean essentialChange = false;

            String interpreterPath = wm.getOptionsWindow().getInterpreterPath();
            String interpreterOpts = wm.getOptionsWindow().getInterpreterOpts();
            String libraryPath = wm.getOptionsWindow().getLibraryPath();
            String outputFontSize = wm.getOptionsWindow().getOuputFontSize();
            String codeFontSize = wm.getOptionsWindow().getCodeFontSize();
            SettingsManager sm = SettingsManager.getInstance();
            InterpreterManager im = InterpreterManager.getInstance();

            if (!(sm.getSetting(Settings.INTERPRETER_PATH).equals(interpreterPath)
                    && sm.getSetting(Settings.INTERPRETER_OPTS).equals(interpreterOpts)
                    && sm.getSetting(Settings.LIBRARY_PATH).equals(libraryPath))) {
                sm.setSetting(Settings.INTERPRETER_PATH, interpreterPath);
                sm.setSetting(Settings.INTERPRETER_OPTS, interpreterOpts);
                sm.setSetting(Settings.LIBRARY_PATH, libraryPath);
                essentialChange = true;
            }

            sm.setSetting(Settings.TEST_FUNCTION, wm.getOptionsWindow().getTestFunction().trim());
            sm.setSetting(Settings.TEST_POSITIVE, wm.getOptionsWindow().getTestPositive().trim());

            /* Perform any font updates */
            try {
                int outputFontsize = Integer.parseInt(outputFontSize);
                wm.getConsoleWindow().setFontSize(outputFontsize);
                sm.setSetting(Settings.OUTPUT_FONT_SIZE, outputFontSize);
            } catch (NumberFormatException nfe) {
                log.warning("[ActionManager] - Failed to parse " +
                        Settings.OUTPUT_FONT_SIZE + " setting from options window");
            }

            try {
                int codeFontsize = Integer.parseInt(codeFontSize);
                wm.getEditorWindow().setFontSize(codeFontsize);
                sm.setSetting(Settings.CODE_FONT_SIZE, codeFontSize);
            } catch (NumberFormatException nfe) {
                log.warning("[ActionManager] - Failed to parse " +
                        Settings.CODE_FONT_SIZE + " setting from options window");
            }

            wm.getOptionsWindow().close();
            sm.saveSettings();

            if (essentialChange) {
                // wm.createGUI();
                // wm.getConsoleWindow().outputInfo("Settings changes applied.\n");
                wm.getConsoleWindow().restart();
            } else {
                wm.repaintAll();
            }
        }
    } /* end SaveOptionsAction */

    /*
     * Save Action of Wizard Window for Settings
     */
    protected class SaveWizardAction extends AbstractAction {
        public SaveWizardAction(String text, ImageIcon icon, String desc,
                                Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            String interpreterPath = wm.getWizardWindow().getInterpreterPath();
            SettingsManager sm = SettingsManager.getInstance();
            InterpreterManager im = InterpreterManager.getInstance();

            wm.getWizardWindow().close();

            sm.setSetting(Settings.INTERPRETER_PATH, interpreterPath);
            sm.saveSettings();
            sm.loadSettings();

            // FileManager fm = FileManager.getInstance();
            // fm.saveTemporary();
            im.startProcess(false);
        }
    } /* end SaveWizardAction */


    protected class CloseFileAction extends AbstractAction {
        public CloseFileAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            FileManager fm = FileManager.getInstance();
            WindowManager wm = WindowManager.getInstance();

            fm.closeCurrentFile();
            wm.setCloseEnabled(false);
            wm.setStatusNoProgram();
            wm.setStatusEvaluating();

            wm.setTitleFileName(null);
            wm.getEditorWindow().setEnabled(false);
            wm.getEditorWindow().setEditorContent("Use menu to open an existing or create a new program in the editor.");

            UndoManager.getInstance().reset();

            ParserManager.getInstance();
            ParserManager.refresh();
            wm.getTreeWindow().refreshTree();


            wm.onlyConsole();
            wm.getConsoleWindow().unload();
        }
    } /* end CloseFileAction */


    protected class PrintFileAction extends AbstractAction {
        public PrintFileAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.showPrintWindow();
        }
    } /* end PrintFileAction */

    protected class ShowHelpAction extends AbstractAction {
        public ShowHelpAction(String text, ImageIcon icon, String desc,
                              Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.showHelpWindow();
        }
    } /* end ShowHelpAction */

    protected class EditCopyAction extends AbstractAction {
        public EditCopyAction(String text, ImageIcon icon, String desc,
                              Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.getEditorWindow().copy();
            // wm.copySelected();
        }
    } /* end EditCopyAction */

    protected class EditCutAction extends AbstractAction {
        public EditCutAction(String text, ImageIcon icon, String desc,
                             Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.getEditorWindow().cut();
        }
    } /* end EditCutAction */

    protected class EditPasteAction extends AbstractAction {
        public EditPasteAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.getEditorWindow().paste();
        }
    } /* end EditPasteAction */

    protected class CompileAction extends AbstractAction {
        public CompileAction(String text, ImageIcon icon,
                             String desc, Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            if (!wm.isCompileEnabled()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            wm.getEditorWindow().clearLineMark();
            //wm.getOuputWindow().appendOutput(System.getProperty("line.separator"));

            FileManager fm = FileManager.getInstance();
            InterpreterManager im = InterpreterManager.getInstance();

            fm.ensureSaved();

            //InterpreterManager.compile();

            wm.setStatusEvaluating();
            wm.getConsoleWindow().compile();
            //im.compile();
            //im.breakInterpreter();
            //wm.getTreeWindow().refreshTree(wm.getEditorWindow().getEditorContent());
            ParserManager.getInstance();
            ParserManager.refresh();
            wm.getTreeWindow().refreshTree();
            //wm.showTree(true);
        }
    } /* end CompileAction */

    public class UndoAction extends AbstractAction {
        public UndoAction(String text, ImageIcon icon, String desc,
                          Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            setEnabled(false);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            UndoManager um = UndoManager.getInstance();

            if (!um.canUndo()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            try {
                um.undo();
            } catch (CannotUndoException ex) {
                log.warning("ActionManager: Unable to undo " + ex);
            }

            WindowManager.getInstance().getMainMenu().updateUndoRedo();
        }

        public void updateUndoState() {
            UndoManager um = UndoManager.getInstance();

            //putValue(Action.NAME, "Undo");
            //putValue(Action.NAME, "Undo");
            setEnabled(um.canUndo());
        }
    } /* end UndoAction */

    public class RedoAction extends AbstractAction {
        public RedoAction(String text, ImageIcon icon, String desc,
                          Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            setEnabled(false);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            UndoManager um = UndoManager.getInstance();

            if (!um.canRedo()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            try {
                um.redo();
            } catch (CannotRedoException ex) {
                log.warning("ActionManager: Unable to redo " + ex);
            }

            WindowManager.getInstance().getMainMenu().updateUndoRedo();
        }

        public void updateRedoState() {
            UndoManager um = UndoManager.getInstance();

            //putValue(Action.NAME, "Redo");
            //putValue(Action.NAME, "Redo");
            setEnabled(um.canRedo());
        }
    } /* end UndoAction */

    protected class RefreshTreeAction extends AbstractAction {
        public RefreshTreeAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            setEnabled(true);
            putValue(SHORT_DESCRIPTION, desc);
            //putValue(MNEMONIC_KEY, mnemonic);
            //putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            //WindowManager wm = WindowManager.getInstance();
            //wm.getTreeWindow().refreshTree(wm.getEditorWindow().getEditorContent());
            ParserManager.getInstance();
            ParserManager.refresh();
            WindowManager.getInstance().getTreeWindow().refreshTree();
        }
    }

    protected class ExpandTreeAction extends AbstractAction {
        public ExpandTreeAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            //setEnabled(true);
            putValue(SHORT_DESCRIPTION, desc);
            //putValue(MNEMONIC_KEY, mnemonic);
            //putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.getTreeWindow().refreshTree();
            wm.getTreeWindow().expandTree();
        }
    }

    protected class CollapseTreeAction extends AbstractAction {
        public CollapseTreeAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            //setEnabled(true);
            putValue(SHORT_DESCRIPTION, desc);
            //putValue(MNEMONIC_KEY, mnemonic);
            //putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager wm = WindowManager.getInstance();
            wm.getTreeWindow().refreshTree();
            wm.getTreeWindow().collapseTree();
        }
    }

    protected class ToggleTreeAction extends AbstractAction {
        public ToggleTreeAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            //setEnabled(true);
            putValue(SHORT_DESCRIPTION, desc);
            //putValue(MNEMONIC_KEY, mnemonic);
            //putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager.getInstance().toggleTree();
        }
    }

    protected class ToggleConsoleAction extends AbstractAction {
        public ToggleConsoleAction(String text, ImageIcon icon, String desc) {
            super(text, icon);
            //setEnabled(true);
            putValue(SHORT_DESCRIPTION, desc);
            //putValue(MNEMONIC_KEY, mnemonic);
            //putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent e) {
            WindowManager.getInstance().toggleConsole();
        }
    }


    /**
     * Action classes for going through the console history
     *
     */
    protected class GoToPastConsoleHistory extends AbstractAction {
        public void actionPerformed(ActionEvent arg0) {
            ConsoleWindow console = WindowManager.getInstance().getConsoleWindow();
            if (console.isEnabled())
                console.commandHistoryBackwards();
            else
                java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    protected class GoToRecentConsoleHistory extends AbstractAction {
        public void actionPerformed(ActionEvent arg0) {
            ConsoleWindow console = WindowManager.getInstance().getConsoleWindow();
            if (console.isEnabled())
                console.commandHistoryForwards();
            else
                java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }


    /**
     * Restarts the interpreter and stops any code or property evaluation running
     * @author ii23
     *
     */
    protected class InterruptAction extends AbstractAction {
        public InterruptAction(String text, ImageIcon icon, String desc,
                               Integer mnemonic, KeyStroke accelerator) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, accelerator);
        }

        public void actionPerformed(ActionEvent arg0) {
            WindowManager wm = WindowManager.getInstance();
            if (!wm.isInterruptEnabled()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            wm.getConsoleWindow().interrupt();
        }

    }
} /* end ActionManger */
