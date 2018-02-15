package com.brownian.trumpscript.token;

public class IntegerConstantToken extends Token {
    public long getValue() {
        return value;
    }

    private final long value;

    public IntegerConstantToken(long value){
        super(String.valueOf(value));

        this.value = value;
    }


}
