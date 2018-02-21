package com.brownian.trumpscript.tokenizer.token;

public class IdToken extends Token {
    public IdToken(String id){
        super(id, true);
    }

    @Override
    protected String getTokenTypeString() {
        return "Id";
    }
}
