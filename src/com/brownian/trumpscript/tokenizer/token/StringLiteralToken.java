package com.brownian.trumpscript.tokenizer.token;

public class StringLiteralToken extends Token {
    public StringLiteralToken(String stringLiteralStatement){
        super(stringLiteralStatement);
    }

    public String getLiteralContents(){
        return getLexeme().substring(1, getLexeme().length()-1);
    }
}
