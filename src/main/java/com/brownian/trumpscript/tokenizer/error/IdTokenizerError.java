package com.brownian.trumpscript.tokenizer.error;

public class IdTokenizerError extends TrumpscriptTokenizerError {

    private static final String ID_ERROR_MESSAGE = "This is a country where we speak English.";

    public IdTokenizerError() {
        super(ID_ERROR_MESSAGE);
    }
}
