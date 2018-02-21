package com.brownian.trumpscript.tokenizer;

public class ConstTokenizerError extends TrumpscriptTokenizerError {
    private static final String CONST_ERROR_MESSAGE = "I'm very rich. <ERROR MESSAGE>";

    public ConstTokenizerError() {
        super(CONST_ERROR_MESSAGE);
    }
}
