package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.SymbolTable;
import com.brownian.trumpscript.TrumpscriptErrorReporter;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.IOException;
import java.io.StringReader;

public class TokenizerDemo {
    private static final String AMERICA_IS_GREAT = "America is great";

    public static void main(String[] args) {
        try (StringReader americaIsGreatReader = new StringReader(AMERICA_IS_GREAT)) {
            SymbolTable symbolTable = new SymbolTable();
            TrumpscriptErrorReporter errorHandler = new TrumpscriptErrorReporter(System.err);
            TokenizerDFA tokenizerDFA = new TokenizerDFA(americaIsGreatReader, symbolTable, errorHandler);
            while (tokenizerDFA.hasMoreTokens()) {
                Token token = tokenizerDFA.getNextToken();
                System.out.printf("%s: <<%s>>\n", token.getClass(), token.getLexeme());
            }
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Um what?");
            e.printStackTrace();
        }
    }
}
