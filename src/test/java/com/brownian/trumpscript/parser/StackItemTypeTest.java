package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.TokenType;
import org.junit.jupiter.api.Test;

import static com.brownian.trumpscript.parser.StackItemType.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackItemTypeTest {
    private static StackItemType[] KEYWORDS = {
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
    },
            NOT_KEYWORDS = {
                    STACK_BOTTOM_MARKER, // 0
                    //// Terminal tokens
                    ID, // 1
                    CONST, // 2
                    STRING, // 3
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
                    ARITH_TAIL
            },
            SPECIAL_SYMBOLS = {
                    // special symbols
                    COMMA, // 27
                    SEMICOLON,
                    COLON,
                    EXCLAMATION_MARK,
                    QUESTION_MARK,
                    LEFT_PAREN,
                    RIGHT_PAREN // 33
            },
            NOT_SPECIAL_SYMBOLS = {
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
                    ARITH_TAIL
            },
    TERMINALS = {
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
    },
    NONTERMINALS = {
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
            ARITH_TAIL
    };

    @Test
    public void test_allTypesHaveNonEmptyToStrings() {
        for (StackItemType type : StackItemType.values()) {
            assertNotEquals(type.toString(), "");
        }
    }

    @Test
    public void test_allKeywordTypesAreKeywords() {
        for (StackItemType type : KEYWORDS) {
            assertTrue(type.isKeyword());
        }
    }

    @Test
    public void test_allNonKeywordTypesAreNotKeywords() {
        for (StackItemType type : NOT_KEYWORDS) {
            assertFalse(type.isKeyword());
        }
    }

    @Test
    public void test_allSpecialCharacterTypesAreSpecialCharacters() {
        for (StackItemType type : SPECIAL_SYMBOLS) {
            assertTrue(type.isSpecialSymbol());
        }
    }

    @Test
    public void test_allNonSpecialCharacterTypesAreNotSpecialCharacters() {
        for (StackItemType type : NOT_SPECIAL_SYMBOLS) {
            assertFalse(type.isSpecialSymbol());
        }
    }

    @Test
    public void test_allTerminalsAreTerminals(){
        for(StackItemType type : TERMINALS){
            assertTrue(type.isTerminal());
            assertFalse(type.isNonterminal());
        }
    }

    @Test
    public void test_allNonTerminalsAreNonTerminals(){
        for(StackItemType type : NONTERMINALS){
            assertTrue(type.isNonterminal());
            assertFalse(type.isTerminal());
        }
    }

    @Test
    public void test_stackBottomIsNeitherTerminalNorNonTerminal(){
        assertFalse(STACK_BOTTOM_MARKER.isNonterminal());
        assertFalse(STACK_BOTTOM_MARKER.isTerminal());
    }

    @Test
    public void test_onlyStackBottomIsStackBottom(){
        assertTrue(STACK_BOTTOM_MARKER.isStackBottom());
        StackItemType[] values = StackItemType.values();
        for(int i = 1 ; i < values.length ; i++){
            assertFalse(values[i].isStackBottom());
        }
    }
}
