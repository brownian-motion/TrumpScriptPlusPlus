package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.BOOKKEEPER;
import com.brownian.trumpscript.ERRORHANDLER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.*;

public class TokenizerDemo {
    private static final String TOKENIZER_TEST_SCRIPT_RESOURCE_NAME = "TokenizerTestScript.trump";

    public static void main(String[] args) {
        echoContentsOfTestScript();

        System.out.println();

        tokenizeTestScriptAndPrintTokensAndSymbolTable();
    }

    private static void tokenizeTestScriptAndPrintTokensAndSymbolTable() {
        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            BOOKKEEPER symbolTable = new BOOKKEEPER();
            ERRORHANDLER errorHandler = new ERRORHANDLER(System.out);
            SCANNER tokenizerDFA = new SCANNER(testScriptReader, symbolTable, errorHandler);

            printAllTokensAndErrors(tokenizerDFA);

            System.out.println();

            printSymbolTable(symbolTable);
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }

    private static void printSymbolTable(BOOKKEEPER symbolTable) {
        System.out.println("Symbol table entries:");
        for (Token token : symbolTable.getEntries().values()) {
            System.out.println(token);
        }
    }

    private static void printAllTokensAndErrors(SCANNER tokenizerDFA) throws IOException {
        Token token;
        System.out.flush();
        while (tokenizerDFA.hasMoreTokens()) {
            token = tokenizerDFA.getNextToken();
            if (token.isValid()) {
                System.out.println(token);
                System.out.flush(); //make sure errors and tokens are interleaved correctly
            } else {
                System.err.flush();//make sure errors and tokens are interleaved correctly
            }
        }
    }

    private static void echoContentsOfTestScript() {
        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            printContentsOfReader(testScriptReader);
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
        }

        System.out.println();
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
