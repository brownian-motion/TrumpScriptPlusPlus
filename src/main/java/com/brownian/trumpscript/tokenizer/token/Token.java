package com.brownian.trumpscript.tokenizer.token;

public abstract class Token {
    private String lexeme;
    private boolean isValid;

    protected Token(String lexeme, boolean isValid) {
        this.lexeme = lexeme;
        this.isValid = isValid;
    }

    public String getLexeme() {
        return lexeme;
    }

    abstract protected String getTokenTypeString();

    @Override
    public String toString(){
        return String.format("%s (%s)", getLexeme(), getTokenTypeString());
    }

    public boolean isValid() {
        return isValid;
    }
}
