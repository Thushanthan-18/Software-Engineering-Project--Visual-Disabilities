import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

// Inner class for audible confirmation
public class SplashScreen extends JWindow {
    private static final String VOICE_NAME = "kevin16";
    private JProgressBar progressBar;

    static class SplashSound {
        public static void play() {
            Voice voice = VoiceManager.getInstance().getVoice(VOICE_NAME);
            if (voice != null) {
                voice.allocate();
                voice.speak("HEAT IDE is starting");
                voice.deallocate();
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

        JLabel label = new JLabel("HEAT IDE is launching", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 24));
        content.add(label, BorderLayout.CENTER);

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

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();

        // Play TTS in a separate thread
        new Thread(SplashSound::play).start();

        // Use SwingWorker to simulate loading tasks and update progress
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