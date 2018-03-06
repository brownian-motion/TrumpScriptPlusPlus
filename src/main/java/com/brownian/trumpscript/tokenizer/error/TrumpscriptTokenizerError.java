package com.brownian.trumpscript.tokenizer.error;

import com.brownian.trumpscript.error.TrumpscriptError;

/**
 * A {@link TrumpscriptError} encountered during tokenizization of TrumpScript++ source code
 */
public class TrumpscriptTokenizerError extends TrumpscriptError {
    private static final String UNSPECIFIED_ERROR_MESSAGE = "Trump doesn't want to hear it.";

    /**
     * Creates a {@link TrumpscriptTokenizerError} with the given message
     * @param message the message to display to the user
     */
    public TrumpscriptTokenizerError(String message) {
        super(message);
    }

    /**
     * Creates an unspecified {@link TrumpscriptTokenizerError} error, with a super helpful message, the best
     */
    public TrumpscriptTokenizerError() {
        this(UNSPECIFIED_ERROR_MESSAGE);
    }
}
