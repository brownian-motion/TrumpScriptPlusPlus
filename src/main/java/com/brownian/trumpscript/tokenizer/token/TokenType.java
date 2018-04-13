package com.brownian.trumpscript.tokenizer.token;

/**
 * Indicate the type of a particular token.
 *
 * The numeric value of that type can be found via {@link TokenType#ordinal()}.
 *
 * Except for a {@link TokenType#MALFORMED_TOKEN}, these are matched
 * to the same ordinal of equivalent {@link com.brownian.trumpscript.parser.StackItemType} enums.
 */
public enum TokenType {
    MALFORMED_TOKEN, // 0 -- should not be used to select a StackItemType
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
    RIGHT_PAREN // 33
}
