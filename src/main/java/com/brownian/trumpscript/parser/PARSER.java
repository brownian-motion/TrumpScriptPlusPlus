package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.SCANNER;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.io.IOException;
import java.util.Stack;

public class PARSER {
    private SCANNER scanner;

    public PARSER(SCANNER scanner) {
        this.scanner = scanner;
    }

    public LLStackItem parse() throws ParsingException {
        Stack<LLStackItem> stack = new Stack<>();
        stack.push(new LLStackItem(StackItemType.STACK_BOTTOM_MARKER));
        LLStackItem program = new LLStackItem(StackItemType.TRUMP); //will be modified to include its children
        stack.push(program);
        try {
            Token lookahead;
            LLStackItem top;
            while (scanner.hasMoreTokens()) {
                lookahead = scanner.getNextToken();
                top = stack.pop();
                if (top.getType() == StackItemType.STACK_BOTTOM_MARKER) {
                    throw new EmptyParseStackException(lookahead);
                }
                if (top.getType().isTerminal()) {
                    if (!top.getType().matches(lookahead.getType()))
                        throw new MismatchedTerminalException(top.getType(), lookahead);
                    top.derive(lookahead);
                } else {
                    try {
                        StackItemType[] productionTypes = TrumpscriptLL1ParseTable.lookupProductionFor(top.getType(), lookahead.getType());
                        LLStackItem[] production = new LLStackItem[productionTypes.length];
                        for (int i = 0; i < productionTypes.length; i++) {
                            production[i] = new LLStackItem(productionTypes[i]);
                            stack.push(production[i]);
                        }
                        top.derive(production);
                    } catch (IllegalArgumentException e) {
                        throw new ParsingException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new ParsingException("IOException from token scanner while parsing", e);
        }

        if (stack.pop().getType() != StackItemType.STACK_BOTTOM_MARKER) {
            throw new UnexpectedEOFException();
        }

        return program;
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
        UnexpectedEOFException() {
            super("Unexpected EOF while parsing. All programs should end with \"America is great\"");
        }
    }
}
