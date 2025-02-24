package utils;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
/*
    public static void main(String[] args) {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        Voice voice = VoiceManager.getInstance().getVoice("kevin16");

        if (voice != null) {
            voice.allocate();
            voice.speak("Hello! FreeTTS is working in my Java project.");
            voice.deallocate();
        } else {
            System.out.println("Voice not found!");
        }
  */

        private static Voice voice;

        public static void main(String[] args) {

            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            VoiceManager voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice("kevin16");
            if (voice != null) {
                voice.allocate();
            } else {
                System.out.println("Voice not found.");
                return;
            }

            // Create the GUI
            JFrame frame = new JFrame("Highlight Text-to-Speech Demo");
            JTextArea textArea = new JTextArea("Highlight any part of this text to hear it spoken.");
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Add caret listener to the text area
            textArea.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    if (textArea.getSelectedText() != null) {
                        speakText(textArea.getSelectedText());
                    }
                }
            });

            frame.getContentPane().add(new JScrollPane(textArea));
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

    private static void speakText(String text) {
        if (voice != null) {
            voice.speak(text);
        }
    }
    }
