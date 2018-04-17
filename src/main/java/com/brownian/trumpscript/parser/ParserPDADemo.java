package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.BOOKKEEPER;
import com.brownian.trumpscript.ERRORHANDLER;
import com.brownian.trumpscript.tokenizer.SCANNER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.*;
import java.util.Map;

public class ParserPDADemo {
    private static final String PARSER_TEST_SCRIPT_RESOURCE_NAME = "ParserTestScript.trump";

    public static void main(String[] args) {
        String sourceContents = null;
        try (Reader source = getSourceCodeReaderOrExit(args)) { // pull from the first arg, or from stdin, or exit with message
            sourceContents = (new java.util.Scanner(source)).useDelimiter("\\A").next();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }

        System.out.println(sourceContents);

        printSeparator(System.out);

        BOOKKEEPER symbolTable = new BOOKKEEPER();
        ERRORHANDLER errorhandler = new ERRORHANDLER(System.out);
        SCANNER tokenizer = new SCANNER(new StringReader(sourceContents), symbolTable, errorhandler);

        PARSER parser = new StepPrintingPARSER(tokenizer, System.out);

        parser.parse();

        printSeparator(System.out);

        System.out.println("Symbol table:");

        System.out.println("-------------------------------------");
        System.out.printf("| %10s | %20s |\n", "lexeme", "value");
        System.out.println("-------------------------------------");
        for (Map.Entry<String, Token> entry : symbolTable.getEntries().entrySet()) {
            System.out.printf("| %10s | %20s |\n", entry.getKey(), entry.getValue());
        }
        System.out.println("-------------------------------------");

    }

    private static void printSeparator(PrintStream out) {
        out.println("=========================");
    }

    private static Reader getSourceCodeReaderOrExit(String[] args) {
        if (args.length == 1) {
            if (args[0].equals("--help") || args[0].equals("-h")) {
                printHelpMessageAndExit();
            }

            if (args[0].equals("--sample")) {
                return new InputStreamReader(new BufferedInputStream(getTestTrumpScriptInputStream()));
            }

            if (!args[0].equals("-")) {
                try {
                    return new BufferedReader(new FileReader(new File(args[0])));
                } catch (FileNotFoundException e) {
                    System.err.println("Could not read file " + args[0]);
                    System.exit(2);
                }
            }
        }

        return new InputStreamReader(System.in);
    }

    /**
     * Gets a fresh handle to the test input file our professor gave us
     * to test this module of this project.
     *
     * @return a fresh handle to a sample TrumpScript++ file
     */
    private static InputStream getTestTrumpScriptInputStream() {
        return ParserPDADemo.class.getResourceAsStream(PARSER_TEST_SCRIPT_RESOURCE_NAME);
    }

    private static void printHelpMessageAndExit() {
        System.err.println("Usage: java " + ParserPDADemo.class.getSimpleName() + " [<input file>]");
        System.err.println("\tParses the TrumpScript++ program specified by the argument, or via stdin.");
        System.err.println("\tPrints the result of parsing, and any parse errors.");
        System.err.println("\tRun \"java " + ParserPDADemo.class.getSimpleName() + " --sample\" for an example run.");
        System.exit(1);
    }
}
