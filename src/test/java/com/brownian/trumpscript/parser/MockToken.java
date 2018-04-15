package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.Token;
import com.brownian.trumpscript.tokenizer.token.TokenType;

class MockToken extends Token {
    MockToken(String lexeme, TokenType tokenType) {
        super(lexeme, tokenType);
    }

    @Override
    protected String getTokenTypeString() {
        return "mock";
    }
}
