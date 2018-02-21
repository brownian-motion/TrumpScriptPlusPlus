package com.brownian.trumpscript.tokenizer.token;

public class SpecialCharacterToken extends Token{
    private char specialCharacter;

    public SpecialCharacterToken(char specialCharacter){
        super(Character.toString(specialCharacter), true);

        this.specialCharacter = specialCharacter;
    }

    public char getSpecialCharacter(){
        return specialCharacter;
    }

    @Override
    protected String getTokenTypeString() {
        return "Special character";
    }
}
