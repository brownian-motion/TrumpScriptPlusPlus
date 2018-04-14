package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.TokenType;

/**
 * Uniquely and exhaustively identifies
 * the type of an item on the parser stack.
 *
 * The numeric value associated with each
 * enum element is the .ordinal() value
 * indicating its position.
 */
public enum StackItemType {
    STACK_BOTTOM_MARKER, // 0
    //// Terminal tokens
    ID, // 1
    CONST, // 2
    STRING, // 3
    // keywords
    MAKE,  // 4
    PROGRAMMING,
    GREAT,
    AGAIN,
    AMERICA,
    IS,
    ELSE,
    NUMBER,
    BOOLEAN,
    IF,
    AS,
    LONG,
    TELL,
    SAY,
    FACT,
    LIE,
    NOT,
    AND,
    OR,
    LESS,
    MORE,
    PLUS,
    TIMES, //26
    // special symbols
    COMMA, // 27
    SEMICOLON,
    COLON,
    EXCLAMATION_MARK,
    QUESTION_MARK,
    LEFT_PAREN,
    RIGHT_PAREN, // 33
    //// Nonterminals
    TRUMP, // 34
    FIRST,
    LAST,
    STMTS,
    MORE_STMTS,
    STMT,
    DECL,
    TYPE,
    ASMT,
    COND,
    LOOP,
    OUTPUT,
    IDS,
    MORE_IDS,
    EXPR,
    BOOL,
    BOOL_TAIL,
    TEST,
    ARITH,
    ARITH_TAIL; // 53

    public boolean isStackBottom() {
        return this == STACK_BOTTOM_MARKER;
    }

    public boolean isTerminal() {
        return this.ordinal() >= 1 && this.ordinal() <= 33;
    }

    public boolean isNonterminal() {
        return this.ordinal() >= 34;
    }

    public boolean isKeyword(){
        return this.isTerminal() && this.ordinal() >= 4 && this.ordinal() <= 26;
    }

    public boolean isSpecialSymbol(){
        return this.isTerminal() && this.ordinal() >= 27 && this.ordinal() <= 33;
    }

    @Override
    public String toString(){
        if(this.isStackBottom()) {
            return "Z₀";
        }
        if(this.isKeyword()){
            return this.name().toLowerCase();
        }
        if(this.isNonterminal()){
            return String.format("<%s>", this.name().toLowerCase());
        }
        if(this.isSpecialSymbol()){
            switch(this){
                case COMMA:
                    return ",";
                case SEMICOLON:
                    return ";";
                case COLON:
                    return ":";
                case EXCLAMATION_MARK:
                    return "!";
                case QUESTION_MARK:
                    return "?";
                case LEFT_PAREN:
                    return "(";
                case RIGHT_PAREN:
                    return ")";
                default:
                    throw new IllegalStateException("Calling toString() of unexpected special symbol "+this.name());
            }
        }
        if(this == ID || this == CONST || this == STRING){
            return String.format("[%s]", this.name().toLowerCase());
        }
        throw new IllegalStateException("Calling toString() of unexpected, unknown stack item type "+this.name());
    }

    public boolean matches(TokenType tokenType) {
        return this.isTerminal() && this.ordinal() == tokenType.ordinal();
    }
}
