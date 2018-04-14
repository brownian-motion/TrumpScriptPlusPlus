package com.brownian.trumpscript;

import com.brownian.trumpscript.error.TrumpscriptError;

import java.io.PrintStream;

/**
 * Response to {@link TrumpscriptError errors} in a graceful way
 * (It ignores them and then prints them out)
 */
public class ERRORHANDLER {
    private PrintStream outputStreamWriter;

    /**
     * Creates an {@link ERRORHANDLER} that prints to the given {@link PrintStream}
     * @param outputStreamWriter a valid stream for output
     */
    public ERRORHANDLER(PrintStream outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    /**
     * Logs the given error to this {@link ERRORHANDLER}'s {@link PrintStream}.
     * @param error the error to display to the user
     */
    public synchronized void logError(TrumpscriptError error) {
        outputStreamWriter.println(error.getMessage());
    }
}
