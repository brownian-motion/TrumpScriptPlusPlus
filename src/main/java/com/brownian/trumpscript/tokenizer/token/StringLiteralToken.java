package com.brownian.trumpscript.tokenizer.token;

/**
 * A {link Token token} representing a string literal in TrumpScript++.
 * A string literal in TrumpScript++ starts and ends with the literal quote symbol, which is represented in Java as \".
 */
public class StringLiteralToken extends Token {
    public StringLiteralToken(String stringLiteralStatement){
        super(stringLiteralStatement, stringLiteralStatement.startsWith("\"") && stringLiteralStatement.endsWith("\""));
    }

    /**
     * Returns a short, human-readable {@link String} indicating that this {@link Token} is a {@link StringLiteralToken}
     * @return a short, human-readable {@link String} indicating that this is a string literal
     */
    @Override
    protected String getTokenTypeString() {
        return "String";
    }

    /**
     * Returns the contents of the string literal, in between the surrounding quotes
     * @return the contents of the string literal, in between the surrounding quotes
     */
    public String getLiteralContents(){
        return getLexeme().substring(1, getLexeme().length()-1);
    }
}
