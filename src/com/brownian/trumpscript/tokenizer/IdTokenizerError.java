package com.brownian.trumpscript.tokenizer;

public class IdTokenizerError extends TrumpscriptTokenizerError {

    private static final String ID_ERROR_MESSAGE = "In this country, we speak English.";

    public IdTokenizerError() {
        super(ID_ERROR_MESSAGE);
    }
}
