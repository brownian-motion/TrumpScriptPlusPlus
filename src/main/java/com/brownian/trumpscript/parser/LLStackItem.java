package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.Token;

import java.util.Arrays;
import java.util.Optional;

public class LLStackItem {
    private LLStackItem[] children;
    private StackItemType type;
    private Token token;

    public LLStackItem(StackItemType type) {
        this.children = null;
        this.type = type;
    }

    public void derive(LLStackItem... children) {
        this.children = children;
    }

    public void derive(Token terminal) {
        if (!this.type.isTerminal()) {
            throw new IllegalArgumentException("Cannot derive a nonterminal stack item to a token");
        }
        if (this.type.ordinal() != terminal.getType().ordinal()) {
            throw new IllegalArgumentException("Cannot derive terminal stack item " + this.type + " to a token of a different type " + terminal.getType());
        }

        this.token = terminal;
    }

    public boolean hasChildren() {
        return children != null;
    }

    public Optional<LLStackItem[]> getChildren() {
        return Optional.ofNullable(this.children);
    }

    public boolean hasTerminalToken() {
        return token != null;
    }

    public Optional<Token> getToken() {
        return Optional.ofNullable(this.token);
    }

    public Optional<LLStackItem[]> backtrack() {
        Optional<LLStackItem[]> prevChildren = this.getChildren();
        this.children = null;
        return prevChildren;
    }

    public StackItemType getType() {
        return this.type;
    }

    public String toString() {
        return (this.children == null ?
                this.token == null ? String.format("%s", this.type) : String.format("%s: \"%s\"", this.type, this.token)
                : String.format("%10s children: %s", this.type, Arrays.toString(children)));
    }
}
