package com.brownian.trumpscript.tokenizer.token;

public abstract class Token {
    private String lexeme;

    protected Token(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }
}
