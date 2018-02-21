package com.brownian.trumpscript.tokenizer.token;

public class KeywordToken extends Token {
    public KeywordToken(String keyword){
        super(keyword, true);
    }

    @Override
    protected String getTokenTypeString() {
        return "Keyword";
    }
}
