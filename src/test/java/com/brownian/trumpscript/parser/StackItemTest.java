package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.Token;
import com.brownian.trumpscript.tokenizer.token.TokenType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackItemTest {

    @Test
    void test_emptyItemsWithSameTypesAreEqual() {
        for (StackItemType type : StackItemType.values())
            assertEquals(new LLStackItem(type), new LLStackItem(type));
    }

    @Test
    void test_emptyItemsWithDifferentTypesAreEqual() {
        for (StackItemType left_type : StackItemType.values())
            for (StackItemType right_type : StackItemType.values()) {
                if (left_type == right_type) continue;
                assertNotEquals(new LLStackItem(left_type), new LLStackItem(right_type));
            }

    }

    @Test
    void test_terminalItemsWithSameTypesAreEqual() {
        StackItemType[] stackItemTypes = StackItemType.values();
        TokenType[] tokenTypes = TokenType.values();
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            LLStackItem left = new LLStackItem(stackItemTypes[i]),
                    right = new LLStackItem(stackItemTypes[i]);
            left.derive(new MockToken("abc", tokenTypes[i]));
            right.derive(new MockToken("abc", tokenTypes[i]));
            assertEquals(left, right);
        }
    }

    @Test
    void test_terminalItemsDoNotEqualEmptyItemOfSameType() {
        StackItemType[] stackItemTypes = StackItemType.values();
        TokenType[] tokenTypes = TokenType.values();
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            LLStackItem left = new LLStackItem(stackItemTypes[i]),
                    right = new LLStackItem(stackItemTypes[i]);
            left.derive(new MockToken("abc", tokenTypes[i]));
            assertNotEquals(left, right);
        }
    }

    @Test
    void test_terminalItemsWithDifferentTypesAreNotEqual() {
        StackItemType[] stackItemTypes = StackItemType.values();
        TokenType[] tokenTypes = TokenType.values();
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            LLStackItem left = new LLStackItem(stackItemTypes[i]);
            left.derive(new MockToken("abc", tokenTypes[i]));
            for (int j = i + 1; j < stackItemTypes.length && stackItemTypes[j].isTerminal(); j++) {
                LLStackItem right = new LLStackItem(stackItemTypes[j]);
                right.derive(new MockToken("abc", tokenTypes[j]));
                assertNotEquals(left, right);
            }
        }
    }

    @Test
    void test_cannotDeriveTerminalWithWrongTokenType() {
        StackItemType[] stackItemTypes = StackItemType.values();
        TokenType[] tokenTypes = TokenType.values();
        LLStackItem token;
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            for (int j = i + 1; j < stackItemTypes.length && stackItemTypes[j].isTerminal(); j++) {
                token = new LLStackItem(stackItemTypes[i]);
                try {
                    token.derive(new MockToken("abc", tokenTypes[j]));
                    fail("No warning for deriving terminal " + stackItemTypes[i] + " incorrectly as " + tokenTypes[j]);
                } catch (Exception e) {
                    // good to go
                }
            }
        }
    }

    @Test
    void test_terminalItemsWithSameChildrenAreEqual() {
        StackItemType[] stackItemTypes = StackItemType.values();
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            LLStackItem left = new LLStackItem(stackItemTypes[i]),
                    right = new LLStackItem(stackItemTypes[i]);
            left.derive(new LLStackItem(StackItemType.ID), new LLStackItem(StackItemType.CONST), new LLStackItem(stackItemTypes[i]));
            right.derive(new LLStackItem(StackItemType.ID), new LLStackItem(StackItemType.CONST), new LLStackItem(stackItemTypes[i]));
            assertEquals(left, right);
        }
    }

    @Test
    void test_terminalItemsWithDifferentChildrenAreNotEqual() {
        StackItemType[] stackItemTypes = StackItemType.values();
        for (int i = 1; i < stackItemTypes.length && stackItemTypes[i].isTerminal(); i++) {
            LLStackItem left = new LLStackItem(stackItemTypes[i]),
                    right = new LLStackItem(stackItemTypes[i]);
            left.derive(new LLStackItem(StackItemType.ID), new LLStackItem(StackItemType.CONST), new LLStackItem(stackItemTypes[i]));
            right.derive(new LLStackItem(StackItemType.ID), new LLStackItem(StackItemType.PLUS), new LLStackItem(stackItemTypes[i]));
            assertNotEquals(left, right);
        }
    }

    private static class MockToken extends Token {
        MockToken(String lexeme, TokenType tokenType) {
            super(lexeme, tokenType);
        }

        @Override
        protected String getTokenTypeString() {
            return "mock";
        }
    }
}
