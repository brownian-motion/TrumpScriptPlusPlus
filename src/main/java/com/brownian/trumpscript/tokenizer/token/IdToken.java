package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} representing an identifier in TrumpScript++
 */
public class IdToken extends Token {
    /**
     * Creates a {@link Token} with the given lexeme as its identifier attribute
     * @param id the lexeme to set as this {@link Token}'s identifier attribute
     */
    public IdToken(String id){
        super(id, true);
    }

    /**
     * Returns the attribute indicating what kind of {@link Token} this is
     * @return a string indicating the type of this {@link Token}
     */
    @Override
    protected String getTokenTypeString() {
        return "Id";
    }
}
