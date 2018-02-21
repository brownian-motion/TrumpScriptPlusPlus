package com.brownian.trumpscript;

import com.brownian.trumpscript.tokenizer.TokenizerDFA;
import com.brownian.trumpscript.tokenizer.token.Token;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains unique mappings from a lexeme (case-insensitive) to its associated {@link Token}.
 * This is used by the {@link TokenizerDFA} to maintain a small collection of tokens, and to
 * make sure all equivalent lexemes refer to the same thing.
 */
public class SymbolTable {

    private Map<String, Token> symbolTable = new HashMap<>();

    /**
     * Returns the {@link Token} associated with the given lexeme (case-insensitive), if any.
     *
     * @param lexeme the text referring to a token
     * @return the {@link Token} associated with that lexeme (case-insensitively), or null if none.
     */
    public Token lookupToken(String lexeme){
        return symbolTable.get(lexeme.toLowerCase());
    }

    /**
     * Associates the given lexeme with the given token
     * @param lexeme the text referring to the given token (case-insensitive)
     * @param token the {@link Token} value for this lexeme
     * @return the previously-stored {@link Token} associated with the given lexeme, if any (null in none)
     */
    public Token setToken(String lexeme, Token token){
        return symbolTable.put(lexeme.toLowerCase(), token);
    }

    /**
     * Returns the entries in this {@link SymbolTable} as a {@link Map}, for easy traversal.
     * @return A {@link Map} containing the {@link String} ↦ {@link Token} entries in this {@link SymbolTable}
     */
    public Map<String, Token> getEntries(){
        return Collections.unmodifiableMap(symbolTable);
    }
}
