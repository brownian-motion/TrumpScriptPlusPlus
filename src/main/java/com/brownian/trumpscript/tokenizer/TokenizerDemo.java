package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.SymbolTable;
import com.brownian.trumpscript.TrumpscriptErrorReporter;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.*;

public class TokenizerDemo {
    private static final String TOKENIZER_TEST_SCRIPT_RESOURCE_NAME = "TokenizerTestScript.trump";

    public static void main(String[] args) {
        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            printContentsOfReader(testScriptReader);
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
            return;
        }

        System.out.println();

        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            SymbolTable symbolTable = new SymbolTable();
            TrumpscriptErrorReporter errorHandler = new TrumpscriptErrorReporter(System.err);
            TokenizerDFA tokenizerDFA = new TokenizerDFA(testScriptReader, symbolTable, errorHandler);
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

    private static InputStream getTokenizerTestTrumpScriptInputStream() {
        return TokenizerDemo.class.getResourceAsStream(TOKENIZER_TEST_SCRIPT_RESOURCE_NAME);
    }

    private static void printContentsOfReader(Reader reader) throws IOException{
        int read;
        while((read = reader.read()) != -1){
            System.out.print((char) read);
        }
    }
}
