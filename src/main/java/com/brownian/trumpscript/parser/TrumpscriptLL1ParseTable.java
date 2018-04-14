package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.tokenizer.token.TokenType;

import java.util.HashMap;
import java.util.Map;

import static com.brownian.trumpscript.parser.StackItemType.*;

/**
 * Defines an LL(1) parse table for fast, deterministic LL parsing.
 * <p>
 * Using the static methods in this class, an LL parser can determine
 * which production rule to use when parsing just using its top stack
 * value and a single lookahead element.
 */
public class TrumpscriptLL1ParseTable {

    private final static StackItemType[]
            TRUMP_PROD = {FIRST, STMTS, LAST},
            FIRST_PROD = {MAKE, PROGRAMMING, GREAT, AGAIN},
            LAST_PROD = {AMERICA, IS, GREAT},
            STMTS_PROD = {STMT, SEMICOLON, MORE_STMTS},
            MORE_STMTS_PROD_RECURSIVE = {STMT, SEMICOLON, MORE_STMTS},
            MORE_STMTS_PROD_TERMINAL = {},
            STMT_PROD_DECL = {DECL},
            STMT_PROD_ASMT = {ASMT},
            STMT_PROD_COND = {COND},
            STMT_PROD_LOOP = {LOOP},
            STMT_PROD_OUTPUT = {OUTPUT},
            DECL_PROD = {MAKE, IDS, TYPE},
            TYPE_PROD_NUM = {NUMBER},
            TYPE_PROD_BOOL = {BOOLEAN},
            ASMT_PROD = {ID, IS, EXPR},
            COND_PROD = {IF, COMMA, BOOLEAN, SEMICOLON, COLON, STMTS, EXCLAMATION_MARK, ELSE, COLON, STMTS, EXCLAMATION_MARK},
            LOOP_PROD = {AS, LONG, AS, COMMA, BOOL, SEMICOLON, COLON, STMTS, EXCLAMATION_MARK},
            OUTPUT_PROD_TELL = {TELL, IDS},
            OUTPUT_PROD_SAY = {SAY, STRING},
            IDS_PROD = {ID, MORE_IDS},
            MORE_IDS_PROD_RECURSIVE = {ID, MORE_IDS},
            MORE_IDS_PROD_TERMINAL = {},
            EXPR_PROD_BOOL = {BOOL},
            EXPR_PROD_ARITH = {ARITH},
            BOOL_PROD_FACT = {FACT, BOOL_TAIL},
            BOOL_PROD_LIE = {LIE, BOOL_TAIL},
            BOOL_PROD_NOT = {NOT, BOOL},
            BOOL_PROD_TEST = {TEST, ARITH, ARITH, QUESTION_MARK},
            BOOL_TAIL_PROD_AND = {AND, BOOL},
            BOOL_TAIL_PROD_OR = {OR, BOOL},
            BOOL_TAIL_PROD_TERMINAL = {},
            TEST_PROD_LESS = {LESS},
            TEST_PROD_IS = {IS},
            TEST_PROD_MORE = {MORE},
            ARITH_PROD_ID = {ID, ARITH_TAIL},
            ARITH_PROD_CONST = {CONST, ARITH_TAIL},
            ARITH_PROD_PAREN = {LEFT_PAREN, ARITH, RIGHT_PAREN, ARITH_TAIL},
            ARITH_TAIL_PROD_PLUS = {PLUS, ARITH},
            ARITH_TAIL_PROD_TIMES = {TIMES, ARITH},
            ARITH_TAIL_PROD_TERMINAL = {};

    private static final Map<HandleLookaheadPair, StackItemType[]> handleLookaheadPairMap = new HashMap<>();

    /*
     * This static initializer registers each production rule
     <nonterminal> → {<stack item>, <stack item>, ...}
     * for quick lookup by handle + lookahead.
      */
    static {
        registerProduction(TRUMP, TRUMP_PROD, TokenType.MAKE);

        registerProduction(FIRST, FIRST_PROD, TokenType.MAKE);

        registerProduction(LAST, LAST_PROD, TokenType.AMERICA);

        registerProduction(STMTS, STMTS_PROD, TokenType.MAKE, TokenType.ID, TokenType.IF, TokenType.AS, TokenType.TELL, TokenType.SAY);

        registerProduction(MORE_STMTS, MORE_STMTS_PROD_RECURSIVE, TokenType.MAKE, TokenType.ID, TokenType.IF, TokenType.AS, TokenType.TELL, TokenType.SAY);
        registerProduction(MORE_STMTS, MORE_STMTS_PROD_TERMINAL, TokenType.AMERICA, TokenType.EXCLAMATION_MARK);

        registerProduction(STMT, STMT_PROD_DECL, TokenType.MAKE);
        registerProduction(STMT, STMT_PROD_ASMT, TokenType.ID);
        registerProduction(STMT, STMT_PROD_COND, TokenType.IF);
        registerProduction(STMT, STMT_PROD_LOOP, TokenType.AS);
        registerProduction(STMT, STMT_PROD_OUTPUT, TokenType.TELL, TokenType.SAY);

        registerProduction(DECL, DECL_PROD, TokenType.MAKE);

        registerProduction(TYPE, TYPE_PROD_NUM, TokenType.NUMBER);
        registerProduction(TYPE, TYPE_PROD_BOOL, TokenType.BOOLEAN);

        registerProduction(ASMT, ASMT_PROD, TokenType.ID);

        registerProduction(COND, COND_PROD, TokenType.IF);

        registerProduction(LOOP, LOOP_PROD, TokenType.AS);

        registerProduction(OUTPUT, OUTPUT_PROD_TELL, TokenType.TELL);
        registerProduction(OUTPUT, OUTPUT_PROD_SAY, TokenType.SAY);

        registerProduction(IDS, IDS_PROD, TokenType.ID);

        registerProduction(MORE_IDS, MORE_IDS_PROD_RECURSIVE, TokenType.ID);
        registerProduction(MORE_IDS, MORE_IDS_PROD_TERMINAL, TokenType.NUMBER, TokenType.BOOLEAN, TokenType.SEMICOLON);

        registerProduction(EXPR, EXPR_PROD_BOOL, TokenType.FACT, TokenType.LIE, TokenType.NOT, TokenType.LESS, TokenType.IS, TokenType.MORE);
        registerProduction(EXPR, EXPR_PROD_ARITH, TokenType.ID, TokenType.CONST, TokenType.LEFT_PAREN);

        registerProduction(BOOL, BOOL_PROD_FACT, TokenType.FACT);
        registerProduction(BOOL, BOOL_PROD_LIE, TokenType.LIE);
        registerProduction(BOOL, BOOL_PROD_NOT, TokenType.NOT);
        registerProduction(BOOL, BOOL_PROD_TEST, TokenType.LESS, TokenType.IS, TokenType.MORE);

        registerProduction(BOOL_TAIL, BOOL_TAIL_PROD_AND, TokenType.AND);
        registerProduction(BOOL_TAIL, BOOL_TAIL_PROD_OR, TokenType.OR);
        registerProduction(BOOL_TAIL, BOOL_TAIL_PROD_TERMINAL, TokenType.SEMICOLON);

        registerProduction(TEST, TEST_PROD_LESS, TokenType.LESS);
        registerProduction(TEST, TEST_PROD_IS, TokenType.IS);
        registerProduction(TEST, TEST_PROD_MORE, TokenType.MORE);

        registerProduction(ARITH, ARITH_PROD_ID, TokenType.ID);
        registerProduction(ARITH, ARITH_PROD_CONST, TokenType.CONST);
        registerProduction(ARITH, ARITH_PROD_PAREN, TokenType.LEFT_PAREN);

        registerProduction(ARITH_TAIL, ARITH_TAIL_PROD_PLUS, TokenType.PLUS);
        registerProduction(ARITH_TAIL, ARITH_TAIL_PROD_TIMES, TokenType.TIMES);
        registerProduction(ARITH_TAIL, ARITH_TAIL_PROD_TERMINAL, TokenType.SEMICOLON, TokenType.ID, TokenType.CONST, TokenType.LEFT_PAREN, TokenType.QUESTION_MARK, TokenType.RIGHT_PAREN);
    }

    private static void registerProduction(StackItemType production, StackItemType[] components, TokenType... lookaheadSet) {
        if (lookaheadSet == null || lookaheadSet.length == 0) {
            throw new IllegalArgumentException("Cannot register a production with no lookahead!");
        }
        for (TokenType lookahead : lookaheadSet) {
            handleLookaheadPairMap.put(new HandleLookaheadPair(production, lookahead), components);
        }
    }


    public static StackItemType[] deriveChildren(StackItemType handle, TokenType lookahead) {
        HandleLookaheadPair key = new HandleLookaheadPair(handle, lookahead);
        if (!handleLookaheadPairMap.containsKey(key))
            throw new InvalidLookaheadException(handle, lookahead);
        return handleLookaheadPairMap.get(key);
    }

    /**
     * A simple tuple struct used to use look up
     * production rules in a {@link HashMap} using
     * a handle and a lookahead as a pair.
     */
    private static class HandleLookaheadPair {

        private final StackItemType handle;
        private final TokenType lookahead;

        HandleLookaheadPair(StackItemType handle, TokenType lookahead) {
            this.handle = handle;
            this.lookahead = lookahead;
        }

        /**
         * Determines if this object is equal to another.
         * Re-implemented to make this object useable as a key in a {@link Map}.
         *
         * @param other some other object, possible a {@link HandleLookaheadPair}
         * @return true if other is a {@link HandleLookaheadPair} with the same lookahead and handle values, and false otherwise.
         */
        @Override
        public boolean equals(Object other) {
            if (!(other instanceof HandleLookaheadPair)) return false;
            HandleLookaheadPair other_pair = (HandleLookaheadPair) other;
            return other_pair.handle == this.handle && other_pair.lookahead == this.lookahead;
        }

        /**
         * Makes each {@link HandleLookaheadPair} hashable so it can be used as a key in a {@link HashMap}.
         *
         * @return a hash code with 1-to-1 association to this object's handle & lookahead pair.
         */
        @Override
        public int hashCode() {
            return handle.hashCode() * 53 /* some prime */ + lookahead.hashCode();
        }
    }

    /**
     * Thrown when trying to look up a production rule
     * for a handle+lookahead pair that is unknown, and therefore invalid.
     *
     * @see #deriveChildren(StackItemType, TokenType)
     */
    public static class InvalidLookaheadException extends IllegalArgumentException {
        private final StackItemType handle;
        private final TokenType lookahead;

        InvalidLookaheadException(StackItemType handle, TokenType lookahead) {
            super(String.format("No production for %s with lookahead %s", handle, lookahead));
            this.handle = handle;
            this.lookahead = lookahead;
        }

        public StackItemType getHandle() {
            return handle;
        }

        public TokenType getLookahead() {
            return lookahead;
        }
    }
}
