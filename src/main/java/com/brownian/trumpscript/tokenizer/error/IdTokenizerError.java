package com.brownian.trumpscript.tokenizer.error;

/**
 * A {@link TrumpscriptTokenizerError} indicating there was a problem reading
 * an {@link com.brownian.trumpscript.tokenizer.token.IdToken} during tokenization
 * of TrumpScript++ source code
 */
public class IdTokenizerError extends TrumpscriptTokenizerError {

    private static final String ID_ERROR_MESSAGE = "This is a country where we speak English.";

    /**
     * Creates a {@link TrumpscriptTokenizerError} with the error message for a poorly-formed ID.
     */
    public IdTokenizerError() {
        super(ID_ERROR_MESSAGE);
    }
}
