package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} representing a reserved word in TrumpScript++.
 */
public class KeywordToken extends Token {
    public KeywordToken(String keyword){
        super(keyword, true);
    }

    /**
     * Indicates that this token is a keyword, in a human-readable way
     * @return a short String indicating that this {@link Token} is a keyword
     */
    @Override
    protected String getTokenTypeString() {
        return "Keyword";
    }
}
