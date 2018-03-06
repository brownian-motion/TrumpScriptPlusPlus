package com.brownian.trumpscript.tokenizer.token;

/**
 * A single lexeme in TrumpScript++, such as an ID, keyword, or integer constant.
 */
public abstract class Token {
    private String lexeme;
    private boolean isValid;

    /**
     * Creates a {@link Token} with the given text (valid or not),
     * and a flag indicating whether or not the token is well-formed.
     * @param lexeme the text of this token, as read from the TrumpScript++ source code
     * @param isValid whether or not this token is well-formed
     */
    protected Token(String lexeme, boolean isValid) {
        this.lexeme = lexeme;
        this.isValid = isValid;
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

    /**
     * A short, human-readable representation of this {@link Token}, including its lexeme and its type.
     * @see #getLexeme()
     * @see #getTokenTypeString()
     * @return a short, human-readable representation of this {@link Token}, including its lexeme and its type
     */
    @Override
    public String toString(){
        return String.format("%s\t(%s)", getLexeme(), getTokenTypeString());
    }

    /**
     * Indicates whether or not this {@link Token} is well-formed.
     * @return whether or not this {@link Token} is well-formed
     */
    public boolean isValid() {
        return isValid;
    }
}
