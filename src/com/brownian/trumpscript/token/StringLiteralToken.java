package com.brownian.trumpscript.token;

public class StringLiteralToken extends Token {
    public StringLiteralToken(String stringLiteralStatement){
        super(stringLiteralStatement);
    }

    public String getLiteralContents(){
        return getText().substring(1, getText().length()-1);
    }
}
