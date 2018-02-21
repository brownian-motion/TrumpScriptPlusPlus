package com.brownian.trumpscript;

import com.brownian.trumpscript.tokenizer.token.Token;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, Token> symbolTable = new HashMap<>();

    public Token lookupToken(String lexeme){
        return symbolTable.get(lexeme.toLowerCase());
    }

    public Token setToken(String lexeme, Token token){
        return symbolTable.put(lexeme.toLowerCase(), token);
    }
}
