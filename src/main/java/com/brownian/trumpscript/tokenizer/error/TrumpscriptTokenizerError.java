package com.brownian.trumpscript.tokenizer.error;

import com.brownian.trumpscript.error.TrumpscriptError;

public class TrumpscriptTokenizerError extends TrumpscriptError {
    private static final String UNSPECIFIED_ERROR_MESSAGE = "UNSPECIFIED ERROR MESSAGE";

    public TrumpscriptTokenizerError(String message) {
        super(message);
    }

    public TrumpscriptTokenizerError() {
        this(UNSPECIFIED_ERROR_MESSAGE);
    }
}
