import managers.InterpreterManager;
import managers.SettingsManager;
import managers.WindowManager;
import managers.UndoManager;
import view.windows.EditorWindow;
import view.windows.SplashScreen;
import view.windows.FeatureSelectionScreen;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.io.File;

/**
 * Main HEAT class
 */
public class Main {
    public static void main(String[] args) {
        // Create and show the splash screen
        SplashScreen splash = new SplashScreen(); // Create splash screen

        SwingWorker<Void, Integer> loader = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                splash.updateProgress(progress);
            }

            @Override
            protected void done() {
                splash.close();

                // Open FeatureSelectionScreen
                SwingUtilities.invokeLater(() -> {
                    FeatureSelectionScreen featureSelectionScreen = new FeatureSelectionScreen();
                    featureSelectionScreen.setVisible(true);

                    // Run HEAT IDE after FeatureSelectionScreen is closed
                    featureSelectionScreen.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                            System.out.println("FeatureSelectionScreen closed. Running HEAT IDE...");
                            runHEAT(args);  // Call runHEAT when FeatureSelectionScreen is closed
                        }
                    });
                });
            }
        };
        loader.execute();
    }

    /**
     * Used to run HEAT
     * @param args Command-line arguments
     */
    public static void runHEAT(String[] args) {
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
       // ew.speak();

        if (sm.isNewSettingsFile()) {
            wm.showWizardWindow();
            // will also start interpreter process
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