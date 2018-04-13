package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.tokenizer.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.brownian.trumpscript.tokenizer.token.TokenType.*;

public class TokenTypeTest {
    private static TokenType[] KEYWORDS = {
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
            MALFORMED_TOKEN, // 0 -- should not be used to select a StackItemType
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
            RIGHT_PAREN // 33
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
    };

    @Test
    public void test_allTokensHaveNonEmptyToStrings(){
        for (TokenType token : TokenType.values()){
            assertNotEquals(token.toString(), "");
        }
    }

    @Test
    public void test_allKeywordTokenTypesAreKeywords() {
        for (TokenType token : KEYWORDS){
            assertTrue(token.isKeyword());
        }
    }

    @Test
    public void test_allNonKeywordTokenTypesAreNotKeywords() {
        for (TokenType token : NOT_KEYWORDS){
            assertFalse(token.isKeyword());
        }
    }

    @Test
    public void test_allSpecialCharacterTypesAreSpecialCharacters(){
        for(TokenType token : SPECIAL_SYMBOLS){
            assertTrue(token.isSpecialSymbol());
        }
    }

    @Test
    public void test_allNonSpecialCharacterTypesAreNotSpecialCharacters(){
        for(TokenType token : NOT_SPECIAL_SYMBOLS){
            assertFalse(token.isSpecialSymbol());
        }
    }
}
