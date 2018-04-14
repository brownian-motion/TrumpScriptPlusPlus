package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} representing a reserved word in TrumpScript++.
 */
public class KeywordToken extends Token {
    /**
     * Creates a {@link KeywordToken} for the given keyword lexeme
     * @param keyword the keyword to make a token for
     * @param keywordType the type of this keyword  token
     */
    public KeywordToken(String keyword, TokenType keywordType) {
        super(keyword, keywordType);
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
