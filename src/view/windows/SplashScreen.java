package view.windows;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

// // Nested class to handle audible feedback
public class SplashScreen extends JWindow {
    private static final String VOICE_NAME = "kevin16";
    private final JProgressBar progressBar;

    static class SplashSound {
        public static void play() {

            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            Voice voiceSplash = VoiceManager.getInstance().getVoice(VOICE_NAME);
            if (voiceSplash != null) {
                voiceSplash.allocate();
                voiceSplash.speak("Welcome to HEAT");
                voiceSplash.deallocate();
            } else {
                System.err.println("Voice not found");
            }


        }
    }

    public SplashScreen() {
        JPanel content = (JPanel) getContentPane();
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());

        int width = 800;
        int height = 500;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 5;
        int y = (screen.height - height) / 5;
        setBounds(x, y, width, height);

        JLabel label = new JLabel("Welcome to HEAT", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 24));
        content.add(label, BorderLayout.CENTER);

// Dimensions of the images
        int splashWidth = width;
        int splashHeight = height;

// PNG image from Icon Folders
        URL imageURL = getClass().getResource("/icons/splash.png");
        if (imageURL == null) {
            System.out.println("Image not found!");
        } else {
            System.out.println("Image URL: " + imageURL.toExternalForm());
        }

        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image originalImage = originalIcon.getImage();

// // Adjust the image size to fit the dimensions of the splash screen
        Image scaledImage = originalImage.getScaledInstance(splashWidth, splashHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

// Creates a JLabel which would hold the scaled image and add it to the content pane
        JLabel imageLabel = new JLabel(scaledIcon, JLabel.CENTER);
        content.add(imageLabel, BorderLayout.CENTER);


        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        content.add(progressBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateProgress(final int value) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
    /*
    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();

        // Play TTS in a separate thread
        new Thread(SplashSound::play).start();

        // Implement SwingWorker to handle loading tasks and provide progress updates
        SwingWorker<Void, Integer> loader = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                int progress = 0;
                while (progress <= 100) {
                    Thread.sleep(30);  // Simulate a task with dynamic loading time
                    publish(progress);
                    progress++;
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latestProgress = chunks.get(chunks.size() - 1);
                splash.updateProgress(latestProgress);
            }

            @Override
            protected void done() {
                splash.close();
                SwingUtilities.invokeLater(() -> {
                    JFrame mainFrame = new JFrame("HEAT IDE");
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.setSize(800, 500);
                    mainFrame.setLocationRelativeTo(null);
                    mainFrame.setVisible(true);
                });
            }
        };

        loader.execute();
    }


}
*/
