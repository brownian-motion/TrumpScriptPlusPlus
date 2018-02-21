package com.brownian.trumpscript.tokenizer.token;

public class SpecialCharacterToken extends Token{
    private char specialCharacter;

    public SpecialCharacterToken(char specialCharacter){
        super(Character.toString(specialCharacter));

        this.specialCharacter = specialCharacter;
    }

    public char getSpecialCharacter(){
        return specialCharacter;
    }
}
