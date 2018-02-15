package com.brownian.trumpscript.token;

public abstract class Token {
    private String text;

    protected Token(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
