package com.brownian.trumpscript.tokenizer.token;

/**
 * A {@link Token} with the attributes of an integer constant.
 */
public class IntegerConstantToken extends Token {
    /**
     * Gets the integer constant associated with this {@link Token}
     * @return the integer value of this token
     */
    public long getValue() {
        return value;
    }

    private final long value;

    /**
     * Creates an {@link IntegerConstantToken} with the given value as its attribute
     * @param value the integer value of this constant token
     */
    public IntegerConstantToken(long value){
        super(String.valueOf(value), TokenType.CONST);

        this.value = value;
    }


    /**
     * Returns the attribute indicating what kind of {@link Token} this is
     * @return a string indicating the type of this {@link Token}
     */
    @Override
    protected String getTokenTypeString() {
        return "Const";
    }
}
