package com.brownian.trumpscript.parser;

import java.util.Arrays;
import java.util.Optional;

public class LLStackItem {
    private LLStackItem[] children;
    private StackItemType type;

    public LLStackItem(StackItemType type) {
        this.children = null;
        this.type = type;
    }

    public void derive(LLStackItem... children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null;
    }

    public Optional<LLStackItem[]> getChildren() {
        return Optional.ofNullable(this.children);
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
                String.format("%s", this.type.name())
                : String.format("%10s children: %s", this.type.name(), Arrays.toString(children)));
    }
}
