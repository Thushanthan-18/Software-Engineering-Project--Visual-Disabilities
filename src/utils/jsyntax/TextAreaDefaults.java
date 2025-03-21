/*
 * TextAreaDefaults.java - Encapsulates default values for various settings
 * Copyright (C) 1999 Slava Pestov You may use and modify this package for any
 * purpose. Redistribution is permitted, in both source and binary form,
 * provided that this notice remains intact in all source distributions of this
 * package.
 */

package utils.jsyntax;

import javax.swing.*;
import java.awt.*;

/**
 * Encapsulates default settings for a text area. This can be passed to the
 * constructor once the necessary fields have been filled out. The advantage of
 * doing this over calling lots of set() methods after creating the text area is
 * that this method is faster.
 */
public class TextAreaDefaults {
    private static TextAreaDefaults DEFAULTS;

    public InputHandler inputHandler;

    public SyntaxDocument document;

    public boolean editable;

    public boolean caretVisible;

    public boolean caretBlinks;

    public boolean blockCaret;

    public int electricScroll;

    public int cols;

    public int rows;

    public SyntaxStyle[] styles;

    public Color caretColor;

    public Color selectionColor;

    public Color lineHighlightColor;

    public boolean lineHighlight;

    public Color bracketHighlightColor;

    public boolean bracketHighlight;

    public Color eolMarkerColor;

    public boolean eolMarkers;

    public boolean paintInvalid;

    public boolean lineNumbers;

    public JPopupMenu popup;

    /**
     * Returns a new TextAreaDefaults object with the default values filled in.
     */
    public static TextAreaDefaults getDefaults() {
        if (DEFAULTS == null) {
            DEFAULTS = new TextAreaDefaults();

            DEFAULTS.inputHandler = new DefaultInputHandler();
            DEFAULTS.inputHandler.addDefaultKeyBindings();
            DEFAULTS.document = new SyntaxDocument();
            DEFAULTS.editable = true;

            DEFAULTS.caretVisible = true;
            DEFAULTS.caretBlinks = true;
            DEFAULTS.electricScroll = 3;

            DEFAULTS.cols = 50;
            DEFAULTS.rows = 15;
            DEFAULTS.styles = SyntaxUtilities.getDefaultSyntaxStyles();
            DEFAULTS.caretColor = Color.black;
            DEFAULTS.selectionColor = new Color(0xccccff);
            DEFAULTS.lineHighlightColor = new Color(255, 253, 216);
            DEFAULTS.lineHighlight = true;
            DEFAULTS.bracketHighlightColor = Color.black;
            DEFAULTS.bracketHighlight = true;
            DEFAULTS.eolMarkerColor = new Color(0x009999);
            DEFAULTS.eolMarkers = false;
            DEFAULTS.paintInvalid = false;
            DEFAULTS.lineNumbers = true;
        }

        return DEFAULTS;
    }
}