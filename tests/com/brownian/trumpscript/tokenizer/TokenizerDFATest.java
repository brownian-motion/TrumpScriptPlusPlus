package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.SymbolTable;
import com.brownian.trumpscript.TrumpscriptErrorReporter;
import com.brownian.trumpscript.tokenizer.token.KeywordToken;
import com.brownian.trumpscript.tokenizer.token.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerDFATest {

    private static final String
            AMERICA_IS_GREAT = "America is great";

    @Test
    void getNextToken() {
    }

    @Test
    void isAtEOF(){}

    @Test
    void testCanReadSingleKeywordToken() throws IOException{
        try (StringReader americaIsGreatReader = new StringReader(AMERICA_IS_GREAT)) {
            SymbolTable symbolTable = new SymbolTable();
            TrumpscriptErrorReporter errorReporter = new TrumpscriptErrorReporter(System.err);
            TokenizerDFA tokenizerDFA = new TokenizerDFA(americaIsGreatReader, symbolTable, errorReporter);
            assertFalse(tokenizerDFA.isAtEOF(), "Just opened TokenizerDFA - with contents - but it's at EOF");

            Token token = tokenizerDFA.getNextToken();
            assertEquals("America", token.getLexeme(), "Mismatch on first token");
            assertTrue(token instanceof KeywordToken);
            assertFalse(tokenizerDFA.isAtEOF(), "EOF after first token, should be 3");
        }
    }

    @Test
    void testCanReadTwoKeywordTokens() throws IOException{
        try (StringReader americaIsGreatReader = new StringReader(AMERICA_IS_GREAT)) {
            SymbolTable symbolTable = new SymbolTable();
            TrumpscriptErrorReporter errorReporter = new TrumpscriptErrorReporter(System.err);
            TokenizerDFA tokenizerDFA = new TokenizerDFA(americaIsGreatReader, symbolTable, errorReporter);
            assertFalse(tokenizerDFA.isAtEOF(), "Just opened TokenizerDFA - with contents - but it's at EOF");

            Token token = tokenizerDFA.getNextToken();
            assertEquals("America", token.getLexeme(), "Mismatch on first token");
            assertTrue(token instanceof KeywordToken);
            assertTrue(tokenizerDFA.hasMoreTokens(), "No more tokens after first, should be 3");

            token = tokenizerDFA.getNextToken();
            assertEquals("is", token.getLexeme(), "Mismatch on second token");
            assertTrue(token instanceof KeywordToken);
            assertTrue(tokenizerDFA.hasMoreTokens(), "No more tokens after second, should be 3");
        }
    }


    @Test
    void testCanReadAmericaIsGreatThenEOF() throws IOException{
        try (StringReader americaIsGreatReader = new StringReader(AMERICA_IS_GREAT)) {
            SymbolTable symbolTable = new SymbolTable();
            TrumpscriptErrorReporter errorReporter = new TrumpscriptErrorReporter(System.err);
            TokenizerDFA tokenizerDFA = new TokenizerDFA(americaIsGreatReader, symbolTable, errorReporter);
            assertTrue(tokenizerDFA.hasMoreTokens(), "Just opened TokenizerDFA - with contents - but it's at EOF");

            Token token = tokenizerDFA.getNextToken();
            assertEquals("America", token.getLexeme(), "Mismatch on first token");
            assertTrue(token instanceof KeywordToken);
            assertTrue(tokenizerDFA.hasMoreTokens(), "No more tokens after first, should be 3");

            token = tokenizerDFA.getNextToken();
            assertEquals("is", token.getLexeme(), "Mismatch on second token");
            assertTrue(token instanceof KeywordToken);
            assertTrue(tokenizerDFA.hasMoreTokens(), "No more tokens after second, should be 3");

            token = tokenizerDFA.getNextToken();
            assertEquals("great", token.getLexeme(), "Mismatch on last (third) token");
            assertTrue(token instanceof KeywordToken);
            assertFalse(tokenizerDFA.hasMoreTokens(), "No EOF after last (third) token");
        }
    }
}