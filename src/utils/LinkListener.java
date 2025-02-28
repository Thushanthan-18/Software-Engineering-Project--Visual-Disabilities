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
 * @author John Travers
 */

package utils;

// import view.windows.TutorialWindow;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;
import java.io.FileNotFoundException;

/**
 * LinkListener.java
 * A hyperlink listener for use with JEditorPane. This
 * listener changes the cursor over hotspots based on enter/exit
 * events and also load a new page when a valid hyperlink is clicked.
 */
public class LinkListener implements HyperlinkListener {

    private final JEditorPane pane;
    private final JTextField urlField;
    private final JLabel statusBar;

    public LinkListener(JEditorPane jep, JTextField jtf, JLabel jl) {
        pane = jep;
        urlField = jtf;
        statusBar = jl;
    }

    public LinkListener(JEditorPane jep) {
        this(jep, null, null);
    }

    public void hyperlinkUpdate(HyperlinkEvent he) {
        HyperlinkEvent.EventType type = he.getEventType();

        if (type == HyperlinkEvent.EventType.ENTERED) {

            if (statusBar != null)
                statusBar.setText(he.getURL().toString());
        } else if (type == HyperlinkEvent.EventType.EXITED) {

            if (statusBar != null)
                statusBar.setText(" ");
        } else {

            if (he instanceof HTMLFrameHyperlinkEvent evt) {

                HTMLDocument doc = (HTMLDocument) pane.getDocument();

                doc.processHTMLFrameHyperlinkEvent(evt);
            } else {
                try {
                    // TutorialWindow.getHistory().add(he.getURL());
                    pane.setPage(he.getURL());

                    if (urlField != null)
                        urlField.setText(he.getURL().toString());
                } catch (FileNotFoundException fnfe) {
                    pane.setText("Could not open file: <tt>" + he.getURL() +
                            "</tt>.<hr>");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
