package com.brownian.trumpscript.parser;

import java.util.Arrays;
import java.util.Optional;

public class StackItem {
    private StackItem[] children;
    private StackItemType type;

    public StackItem(StackItemType type) {
        this.children = null;
        this.type = type;
    }

    public void derive(StackItem... children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return children != null;
    }

    public Optional<StackItem[]> getChildren() {
        return Optional.ofNullable(this.children);
    }

    public Optional<StackItem[]> backtrack() {
        Optional<StackItem[]> prevChildren = this.getChildren();
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
