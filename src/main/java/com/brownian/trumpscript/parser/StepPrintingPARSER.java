package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.SCANNER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.PrintStream;

public class StepPrintingPARSER extends PARSER {
    private int stepNumber = 0;
    private PrintStream out;

    StepPrintingPARSER(SCANNER scanner, PrintStream out) {
        super(scanner);
        this.out = out;
    }

    @Override
    protected LLStackItem initializeStack() {
        LLStackItem initialStackTop = super.initializeStack();
        printHeader();
        printAction(new LLStackItem(StackItemType.STACK_BOTTOM_MARKER), null, "PUSH " + formatStackItem(initialStackTop));
        return initialStackTop;
    }

    @Override
    protected void matchTerminal(LLStackItem stackTop, Token terminal) {
        super.matchTerminal(stackTop, terminal); // do this first in case there's an error to print
        printAction(stackTop, terminal, "MATCH TERMINAL, POP & CONSUME");
    }

    @Override
    protected StackItemType[] getProductionFor(LLStackItem top, Token lookahead) {
        StackItemType[] production = super.getProductionFor(top, lookahead);
        printAction(top, lookahead, formatProduction(top, production));
        return production;
    }

    private String formatProduction(LLStackItem top, StackItemType[] production) {
        String[] formattedTypes = new String[production.length];
        for (int i = 0; i < production.length; i++) {
            formattedTypes[i] = formatStackItemType(production[i]);
        }
        return String.format("Use rule %s → %s", formatStackItem(top), String.join(" ", formattedTypes));
    }


    private void printAction(LLStackItem stackTop, Token lookahead, String action) {
        out.printf("%5d %18s %18s %s", this.stepNumber, formatStackItem(stackTop), formatToken(lookahead), action);
        this.stepNumber++;
    }

    private String formatStackItem(LLStackItem stackTop) {
        return formatStackItemType(stackTop.getType());
    }

    private String formatStackItemType(StackItemType type) {
        return type.toString() + " (" + type.ordinal() + ")";
    }

    private String formatToken(Token token) {
        if (token == null)
            return "   -   ";
        return token.toString() + " (" + token.getType().ordinal() + ")";
    }

    private void printHeader() {
        out.printf("%5s %18s %18s %s", "Steps", "Stack top", "LOKAHEAD₁", "ACTION");
        final String eighteen = "------------------";
        out.printf("%5s %18s %18s %s", "-----", eighteen, eighteen, eighteen);
    }
}
