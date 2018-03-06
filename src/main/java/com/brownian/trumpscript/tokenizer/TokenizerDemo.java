package com.brownian.trumpscript.tokenizer;

import com.brownian.trumpscript.BOOKKEEPER;
import com.brownian.trumpscript.ERRORHANDLER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.*;

/**
 * The MAIN class for the {@link SCANNER}. Demonstrates the functionality of the tokenizer module according
 * to the specifications of the first project.
 */
public class TokenizerDemo {
    private static final String TOKENIZER_TEST_SCRIPT_RESOURCE_NAME = "TokenizerTestScript.trump",
                                MAJOR_SEPARATOR="=========================";

    public static void main(String[] args) {
        echoContentsOfTestScript();

        System.out.println(MAJOR_SEPARATOR);

        tokenizeTestScriptAndPrintTokensAndSymbolTable();
    }

    /**
     * Tokenizes input from the sample input file,
     * prints all of the tokens and errors in order,
     * then prints the {@link BOOKKEEPER} it generated.
     */
    private static void tokenizeTestScriptAndPrintTokensAndSymbolTable() {
        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            BOOKKEEPER symbolTable = new BOOKKEEPER();
            ERRORHANDLER errorHandler = new ERRORHANDLER(System.out);
            SCANNER tokenizerDFA = new SCANNER(testScriptReader, symbolTable, errorHandler);

            printAllTokensAndErrors(tokenizerDFA);

            System.out.println(MAJOR_SEPARATOR);

            printSymbolTable(symbolTable);
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
        } catch (Exception e){
            System.err.println("Unexpected error");
            e.printStackTrace();
        }
    }

    /**
     * Prints a of the lexeme->{@link Token} associations in the given {@link BOOKKEEPER}
     * @param symbolTable a {@link BOOKKEEPER} containing lexeme->{@link Token} associations
     */
    private static void printSymbolTable(BOOKKEEPER symbolTable) {
        System.out.println("Symbol table entries:");
        for (Token token : symbolTable.getEntries().values()) {
            System.out.println(token);
        }
    }

    /**
     * Uses the given {@link SCANNER} to get and then print all of the tokens provided by that {@link SCANNER}.
     * @param tokenizerDFA a {@link SCANNER} that will provide tokens
     * @throws IOException if there is a problem reading input
     */
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

    /**
     * Echos the contents of the test script this demo uses, line for line.
     */
    private static void echoContentsOfTestScript() {
        try (Reader testScriptReader = new BufferedReader(new InputStreamReader(getTokenizerTestTrumpScriptInputStream()))) {
            printContentsOfReader(testScriptReader);
        } catch (IOException e) {
            System.err.println("IO ERROR");
            e.printStackTrace();
        }

        System.out.println();
    }

    /**
     * Gets a fresh handle to the test input file our professor gave us to test this project.
     * @return a fresh handle to a sample TrumpScript++ file
     */
    private static InputStream getTokenizerTestTrumpScriptInputStream() {
        return TokenizerDemo.class.getResourceAsStream(TOKENIZER_TEST_SCRIPT_RESOURCE_NAME);
    }

    /**
     * Prints every char provided by the given reader, until EOF is reached
     * @param reader an opened input source
     * @throws IOException if there's problem reading input
     */
    private static void printContentsOfReader(Reader reader) throws IOException{
        int read;
        while((read = reader.read()) != -1){
            System.out.print((char) read);
        }
    }
}
