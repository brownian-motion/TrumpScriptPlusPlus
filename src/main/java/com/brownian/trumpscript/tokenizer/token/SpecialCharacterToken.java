package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} representing a single special character in TrumpScript++.
 * Special characters are determined by {@link com.brownian.trumpscript.tokenizer.TokenizerDFA#isSpecialCharacter(char)}
 */
public class SpecialCharacterToken extends Token{
    private char specialCharacter;

    /**
     * Constructs a {@link Token} representing the given special character
     * @param specialCharacter the special character for which to construct a {@link Token}
     */
    public SpecialCharacterToken(char specialCharacter){
        super(Character.toString(specialCharacter), true);

        this.specialCharacter = specialCharacter;
    }

    /**
     * Gets the special character this {@link SpecialCharacterToken} represents
     * @return the special character this {@link SpecialCharacterToken} represents
     */
    public char getSpecialCharacter(){
        return specialCharacter;
    }

    /**
     * Returns a short, human-readable string indicating that this {@link Token} is a {@link SpecialCharacterToken}
     * @return a short, human-readable string indicating that this is a special character
     */
    @Override
    protected String getTokenTypeString() {
        return "Special character";
    }
}
