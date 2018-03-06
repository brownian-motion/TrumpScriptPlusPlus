package com.brownian.trumpscript.error;

/**
 * Parent class for all {@link TrumpscriptError}s. Indicates that an error has been found in
 * a TrumpScript++ program.
 */
public class TrumpscriptError extends Exception {
    /**
     * Creates a {@link TrumpscriptError} with the given error message
     * @param message the error message to display to the user
     */
    public TrumpscriptError(String message){
        super(message);
    }
}
