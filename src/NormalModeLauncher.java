//import com.incors.plaf.alloy.AlloyLookAndFeel;

import managers.InterpreterManager;
import managers.SettingsManager;
import managers.UndoManager;
import managers.WindowManager;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Normal Mode Launcher for HEAT IDE
 */
public class NormalModeLauncher {

    /**
     * Used to run HEAT Normal Mode
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("✅ NormalModeLauncher is running!");

        Logger log = Logger.getLogger("heat");
        try {
            log.setUseParentHandlers(false);
            FileHandler handler = new FileHandler(System.getProperty("user.home") + File.separator + "heat.log");
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
        } catch (Exception e) {
            System.out.println("Could not install file handler for logging.");
        }

        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "");
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        SettingsManager sm = SettingsManager.getInstance();
        WindowManager wm = WindowManager.getInstance();

        sm.loadSettings();
        WindowManager.setLookAndFeel();
        wm.createGUI();

        if (sm.isNewSettingsFile()) {
            wm.showWizardWindow();
        } else {
            InterpreterManager im = InterpreterManager.getInstance();
            im.startProcess(false);
        }

        if (args.length > 0) {
            wm.openFile(new java.io.File(args[0]));
            wm.showAll();
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
