package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} representing a poorly-formed lexeme,
 * which cannot be identified as any other kind of {@link Token}.
 */
public class ErrorToken extends Token{
    /**
     * Reterns an {@link ErrorToken} for the given poorly-formed lexeme
     * @param erroneousText the unrecognizable source code text
     */
    public ErrorToken(String erroneousText){
        super(erroneousText, TokenType.MALFORMED_TOKEN);
    }

    /**
     * Returns the attribute indicating what kind of {@link Token} this is
     * @return a string indicating the type of this {@link Token}
     */
    @Override
    protected String getTokenTypeString() {
        return "Error";
    }
}
