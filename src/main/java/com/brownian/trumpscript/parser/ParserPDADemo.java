package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.BOOKKEEPER;
import com.brownian.trumpscript.ERRORHANDLER;
import com.brownian.trumpscript.tokenizer.SCANNER;

import java.io.*;

public class ParserPDADemo {
    public static void main(String[] args) {
        Reader source = getSourceCodeReaderOrExit(args); // pull from the first arg, or from stdin, or exit with message

        BOOKKEEPER symbolTable = new BOOKKEEPER();
        ERRORHANDLER errorhandler = new ERRORHANDLER(System.out);
        SCANNER tokenizer = new SCANNER(source, symbolTable, errorhandler);

        PARSER parser = new StepPrintingPARSER(tokenizer, System.out);
    }

    private static Reader getSourceCodeReaderOrExit(String[] args) {
        if (args.length == 1) {
            if (args[0].equals("--help") || args[0].equals("-h")) {
                printHelpMessageAndExit();
            }

            if (!args[0].equals("-")) {
                try {
                    return new FileReader(new File(args[0]));
                } catch (FileNotFoundException e) {
                    System.err.println("Could not read file " + args[0]);
                    System.exit(2);
                }
            }
        }

        return new InputStreamReader(System.in);
    }

    private static void printHelpMessageAndExit() {
        System.err.println("Usage: java " + ParserPDADemo.class.getSimpleName() + " [<input file>]");
        System.err.println("\tParses the TrumpScript++ program specified by the argument, or via stdin.");
        System.err.println("\tPrints the result of parsing, and any parse errors.");
        System.exit(1);
    }
}
