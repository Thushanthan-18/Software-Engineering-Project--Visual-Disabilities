package utils.jsyntax;

/*
 * SyntaxUtilities.java - Utility functions used by syntax colorizing Copyright
 * (C) 1999 Slava Pestov You may use and modify this package for any purpose.
 * Redistribution is permitted, in both source and binary form, provided that
 * this notice remains intact in all source distributions of this package.
 */

import utils.jsyntax.tokenmarker.Token;

import javax.swing.text.Segment;
import javax.swing.text.TabExpander;
import javax.swing.text.Utilities;
import java.awt.*;

/**
 * Class with several utility functions used by jEdit's syntax colorizing
 * subsystem.
 *
 * @author Slava Pestov
 * @version $Id: SyntaxUtilities.java,v 1.9 1999/12/13 03:40:30 sp Exp $
 */
public class SyntaxUtilities {
    /**
     * Checks if a subregion of a <code>Segment</code> is equal to a string.
     *
     * @param ignoreCase True if case should be ignored, false otherwise
     * @param text       The segment
     * @param offset     The offset into the segment
     * @param match      The string to match
     */
    public static boolean regionMatches(boolean ignoreCase, Segment text,
                                        int offset, String match) {
        int length = offset + match.length();
        char[] textArray = text.array;
        if (length > text.offset + text.count)
            return false;
        for (int i = offset, j = 0; i < length; i++, j++) {
            char c1 = textArray[i];
            char c2 = match.charAt(j);
            if (ignoreCase) {
                c1 = Character.toUpperCase(c1);
                c2 = Character.toUpperCase(c2);
            }
            if (c1 != c2)
                return false;
        }
        return true;
    }

    /**
     * Checks if a subregion of a <code>Segment</code> is equal to a character
     * array.
     *
     * @param ignoreCase True if case should be ignored, false otherwise
     * @param text       The segment
     * @param offset     The offset into the segment
     * @param match      The character array to match
     */
    public static boolean regionMatches(boolean ignoreCase, Segment text,
                                        int offset, char[] match) {
        int length = offset + match.length;
        char[] textArray = text.array;
        if (length > text.offset + text.count)
            return false;
        for (int i = offset, j = 0; i < length; i++, j++) {
            char c1 = textArray[i];
            char c2 = match[j];
            if (ignoreCase) {
                c1 = Character.toUpperCase(c1);
                c2 = Character.toUpperCase(c2);
            }
            if (c1 != c2)
                return false;
        }
        return true;
    }

    /**
     * Returns the default style table. This can be passed to the
     * <code>setStyles()</code> method of <code>SyntaxDocument</code> to use
     * the default syntax styles.
     */
    public static SyntaxStyle[] getDefaultSyntaxStyles() {
        SyntaxStyle[] styles = new SyntaxStyle[Token.ID_COUNT];
        /* Comments */
        styles[Token.COMMENT1] = new SyntaxStyle(new Color(0, 153, 0), false, false);
        styles[Token.COMMENT2] = new SyntaxStyle(new Color(0, 153, 0), false, false);
        /* Keywords */
        styles[Token.KEYWORD1] = new SyntaxStyle(Color.black, false, true);
        styles[Token.KEYWORD2] = new SyntaxStyle(Color.magenta, false, false);
        styles[Token.KEYWORD3] = new SyntaxStyle(Color.blue, false, true);
        styles[Token.LITERAL1] = new SyntaxStyle(new Color(0x650099), false, false);
        styles[Token.LITERAL2] = new SyntaxStyle(Color.PINK, false, true);
        styles[Token.LABEL] = new SyntaxStyle(new Color(0x990033), false, true);
        styles[Token.OPERATOR] = new SyntaxStyle(Color.black, false, false);
        styles[Token.INVALID] = new SyntaxStyle(Color.black, false, false);

        return styles;
    }

    /**
     * Paints the specified line onto the graphics context. Note that this method
     * munges the offset and count values of the segment.
     *
     * @param line     The line segment
     * @param tokens   The token list for the line
     * @param styles   The syntax style list
     * @param expander The tab expander used to determine tab stops. May be null
     * @param gfx      The graphics context
     * @param x        The x co-ordinate
     * @param y        The y co-ordinate
     * @return The x co-ordinate, plus the width of the painted string
     */
    public static int paintSyntaxLine(Segment line, Token tokens,
                                      SyntaxStyle[] styles, TabExpander expander, Graphics gfx, int x, int y) {
        Font defaultFont = gfx.getFont();
        Color defaultColor = gfx.getColor();

        int offset = 0;
        for (; ; ) {
            byte id = tokens.id;
            if (id == Token.END)
                break;

            int length = tokens.length;
            if (id == Token.NULL) {
                if (!defaultColor.equals(gfx.getColor()))
                    gfx.setColor(defaultColor);
                if (!defaultFont.equals(gfx.getFont()))
                    gfx.setFont(defaultFont);
            } else
                styles[id].setGraphicsFlags(gfx, defaultFont);

            line.count = length;
            x = Utilities.drawTabbedText(line, x, y, gfx, expander, 0);
            line.offset += length;
            offset += length;

            tokens = tokens.next;
        }

        return x;
    }

    // private members
    private SyntaxUtilities() {
    }
}