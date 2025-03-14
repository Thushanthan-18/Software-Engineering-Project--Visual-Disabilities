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
 * @author Chris Olive
 */

package utils;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * Class InterpreterOutput
 * <p>
 * Keeps a vector of Strings for sending to an external process. Continually
 * sends these Strings when the process allows (i.e. won't deadlock)
 */
public class InterpreterOutput extends Thread {

    private static final Logger log = Logger.getLogger("heat");

    /* Attributes */
    private BufferedWriter bw;
    private final String stringToSend;
    private final Vector strings;
    /* Object for syncing */
    private final Object waitOnString;
    private final OutputStream os;
    boolean continueToRun;

    /**
     * Constructor. Connects to the input of an external process.
     * @param os OutputStream for sending data.
     */
    public InterpreterOutput(OutputStream os) {
        this.os = os;
        stringToSend = "";
        strings = new Vector();
        continueToRun = true;
        waitOnString = new Object();
    }

    /**
     * Send a string to the external process
     * @param toBeSent The String for sending
     */
    public void sendString(String toBeSent) {
        strings.add(toBeSent);
        /* Ensure there is no deadlock */
        synchronized (waitOnString) {
            waitOnString.notify();
        }
    }

    /**
     * Invoked by start();
     */
    public void run() {
        String str;
        while (continueToRun) {
            try {
                /* Ensure there is no deadlock */
                synchronized (waitOnString) {
                    waitOnString.wait();
                }
                while (!strings.isEmpty()) {
                    str = (String) strings.remove(0);
                    os.write(str.getBytes());
                    os.flush();
                }
            } catch (Exception e) {
                log.warning("Error sending to process");
            }
        }
    }
}