package com.brownian.trumpscript.tokenizer.token;

/**
 * A single lexeme in TrumpScript++, such as an ID, keyword, or integer constant.
 */
public abstract class Token {
    private String lexeme;
    private TokenType type;

    /**
     * Creates a {@link Token} with the given text (valid or not),
     * and a flag indicating whether or not the token is well-formed.
     * @param lexeme the text of this token, as read from the TrumpScript++ source code
     * @param type the type of this token. {@link TokenType#MALFORMED_TOKEN} indicates some kind of error
     */
    protected Token(String lexeme, TokenType type) {
        this.lexeme = lexeme;
        this.type = type;
    }

    /**
     * Gets the text of this {@link Token} as it was written in the source code.
     * @return the text of this {@link Token} as it was written in the source code.
     */
    public String getLexeme() {
        return lexeme;
    }

    /**
     * Returns a short human-readable {@link String} indicating what kind of {@link Token} this is,
     * for printing Tokens in a human-readable way.
     * @return the kind of {@link Token} this is, as a {@link String}
     */
    abstract protected String getTokenTypeString();

    public TokenType getType() {
        return this.type;
    }

    /**
     * A short, human-readable representation of this {@link Token}, including its lexeme and its type.
     * @see #getLexeme()
     * @see #getTokenTypeString()
     * @return a short, human-readable representation of this {@link Token}, including its lexeme and its type
     */
    @Override
    public String toString(){
        return String.format("%s (%s)", getLexeme(), getTokenTypeString());
    }

    @Override
    public int hashCode() {
        return getLexeme().toLowerCase().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Token))
            return false;
        return this.getLexeme().equalsIgnoreCase(((Token) other).getLexeme());
    }

    /**
     * Indicates whether or not this {@link Token} is well-formed.
     * @return whether or not this {@link Token} is well-formed
     */
    public boolean isValid() {
        return type != TokenType.MALFORMED_TOKEN;
    }
}
