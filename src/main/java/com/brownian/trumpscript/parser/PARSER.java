package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.SCANNER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.IOException;
import java.util.Stack;

public class PARSER {
    private SCANNER scanner;
    private Stack<LLStackItem> stack;

    public PARSER(SCANNER scanner) {
        this.scanner = scanner;
    }

    public LLStackItem parse() throws ParsingException {
        LLStackItem program = initializeStack();
        Token lookahead;
        try {
            lookahead = scanner.getNextToken();
            LLStackItem top;
            while (scanner.hasMoreTokens() || lookahead != null) {
                if (lookahead == null)
                    lookahead = scanner.getNextToken();
                top = stack.pop();
                if (top.getType() == StackItemType.STACK_BOTTOM_MARKER) {
                    throw new EmptyParseStackException(lookahead);
                }
                if (top.getType().isTerminal()) {
                    matchTerminal(top, lookahead);
                    lookahead = null;
                    continue;
                } else {
                    try {
                        StackItemType[] productionTypes = getProductionFor(top, lookahead);
                        LLStackItem[] production = new LLStackItem[productionTypes.length];
                        for (int i = productionTypes.length - 1; i >= 0 /* have to push first thing we want to read on LAST */ ; i--) {
                            production[i] = new LLStackItem(productionTypes[i]);
                            stack.push(production[i]);
                        }
                        top.derive(production);
                    } catch (IllegalArgumentException e) {
                        throw new ParsingException(e);
                    }
                    continue;
                }
            }
        } catch (IOException e) {
            throw new ParsingException("IOException from token scanner while parsing", e);
        }

        if (stack.peek().getType() != StackItemType.STACK_BOTTOM_MARKER) {
            throw new UnexpectedEOFException(stack);
        }

        return program;
    }

    protected LLStackItem initializeStack() {
        stack = new Stack<>();
        stack.push(new LLStackItem(StackItemType.STACK_BOTTOM_MARKER));
        LLStackItem program = new LLStackItem(StackItemType.TRUMP); //will be modified to include its children
        stack.push(program);
        return program;
    }

    protected void matchTerminal(LLStackItem stackTop, Token terminal) {
        if (!stackTop.getType().matches(terminal.getType()))
            throw new MismatchedTerminalException(stackTop.getType(), terminal);
        stackTop.derive(terminal);
    }

    protected StackItemType[] getProductionFor(LLStackItem top, Token lookahead) {
        return TrumpscriptLL1ParseTable.lookupProductionFor(top.getType(), lookahead.getType());
    }

    public static class ParsingException extends RuntimeException {
        ParsingException(Exception e) {
            super(e);
        }

        ParsingException(String message) {
            super(message);
        }

        ParsingException(String message, Throwable cause) {
            super(message, cause);
        }

        ParsingException() {
            super("Error encountered during parsing.");
        }
    }

    public static class MismatchedTerminalException extends ParsingException {
        MismatchedTerminalException(StackItemType expected, Token actual) {
            super(String.format("Could not consume terminal stack item: expected %s but found %s", expected, actual));
        }
    }

    public static class EmptyParseStackException extends ParsingException {
        EmptyParseStackException(Token extraToken) {
            super("Reached the bottom of the parse stack before reaching EOF. Current lookahead: " + extraToken);
        }
    }

    public static class UnexpectedEOFException extends ParsingException {

        UnexpectedEOFException(Stack<LLStackItem> stack) {
            super("Unexpected EOF while parsing. All programs should end with \"America is great\"\nCurrent stack: " + stack);
        }
    }
}
