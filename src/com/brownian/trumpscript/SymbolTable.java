package com.brownian.trumpscript;

import com.brownian.trumpscript.tokenizer.token.Token;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, Token> symbolTable = new HashMap<>();

    public Token lookupToken(String tokenText){
        return symbolTable.get(tokenText);
    }

    public Token setToken(String tokenText, Token token){
        return symbolTable.put(tokenText, token);
    }
}
