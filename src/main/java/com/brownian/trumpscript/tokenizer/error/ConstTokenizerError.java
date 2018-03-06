package com.brownian.trumpscript.tokenizer.error;

/**
 * A {@link TrumpscriptTokenizerError} shown to the user when a poorly-formed integer constant is found.
 */
public class ConstTokenizerError extends TrumpscriptTokenizerError {
    private static final String CONST_ERROR_MESSAGE = "I'm really rich, part of the beauty of me is I'm very rich.";

    /**
     * Creates a {@link ConstTokenizerError} with the error message indicating a poorly-formed const has been found
     */
    public ConstTokenizerError() {
        super(CONST_ERROR_MESSAGE);
    }
}
