/** 
 *
 * Copyright (c) 2005 University of Kent
 * Computing Laboratory, Canterbury, Kent, CT2 7NP, U.K
 *
 * This software is the confidential and proprietary information of the
 * Computing Laboratory of the University of Kent ("Confidential Information").
 * You shall not disclose such confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with
 * the University.
 *
 */

//import com.incors.plaf.alloy.AlloyLookAndFeel;

import managers.InterpreterManager;
import managers.SettingsManager;
import managers.WindowManager;
import managers.UndoManager;
import view.windows.EditorWindow;

import javax.swing.*;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.File;



/**
 * Main HEAT class
 */
public class Main {
  
  /**
   * Used to run HEAT
   * @param args
   */
public static void main(String[] args) {
    Logger log = Logger.getLogger("heat");
    try {
        log.setUseParentHandlers(false);  // turn off logging on stdout console
        FileHandler handler = new FileHandler(System.getProperty("user.home") + File.separator + "heat.log");
        handler.setFormatter(new SimpleFormatter());
        log.addHandler(handler);
    } catch (Exception e) {
        System.out.println("Could not install file handler for logging.");
    }
    
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", ""); // set name of main menu on Mac
    System.setProperty("apple.laf.useScreenMenuBar", "true");  // on Mac separate menu from window
	  
    SettingsManager sm = SettingsManager.getInstance();
    WindowManager wm = WindowManager.getInstance();
    EditorWindow ew = new EditorWindow();

    sm.loadSettings();
    WindowManager.setLookAndFeel();
    wm.createGUI();

    JPopupMenu contextMenu = new JPopupMenu();

    JMenuItem speakItem = new JMenuItem("Speak");

    contextMenu.add(speakItem);


    ew.speak();


    if (sm.isNewSettingsFile())
      wm.showWizardWindow();
      // will also start interpreter process
    else {
      // FileManager fm = FileManager.getInstance();
      // fm.saveTemporary();

      InterpreterManager im = InterpreterManager.getInstance();
      im.startProcess(false);
    }
    
    if (args.length > 0) {
    	wm.openFile(new java.io.File(args[0]));
        wm.showAll();
        /* Make edit area active */
        wm.getEditorWindow().grabFocus(); 
    } else {
        wm.getEditorWindow().setEditorContent("Use menu to open an existing or create a new program in the editor.");
        wm.setCloseEnabled(false);
        UndoManager.getInstance().reset();
        wm.onlyConsole();
        wm.getConsoleWindow().getFocus();
    }
    wm.setVisible();
   }
}
  
