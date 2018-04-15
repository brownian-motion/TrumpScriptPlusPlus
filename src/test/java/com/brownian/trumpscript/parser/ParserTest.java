package com.brownian.trumpscript.parser;

import com.brownian.trumpscript.BOOKKEEPER;
import com.brownian.trumpscript.ERRORHANDLER;
import com.brownian.trumpscript.tokenizer.SCANNER;
import com.brownian.trumpscript.tokenizer.token.TokenType;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    void test_canParseHelloWorld() {
        Reader reader = new StringReader("Make programming great again\nsay \"Hello, world!\";\nAmerica is great");
        BOOKKEEPER bookkeeper = new BOOKKEEPER();
        ERRORHANDLER errorhandler = new ERRORHANDLER(System.err);
        SCANNER scanner = new SCANNER(reader, bookkeeper, errorhandler);
        PARSER parser = new PARSER(scanner);

        LLStackItem program = new LLStackItem(StackItemType.TRUMP);
        {
            LLStackItem first = new LLStackItem(StackItemType.FIRST);
            {
                LLStackItem make = new LLStackItem(StackItemType.MAKE),
                        programming = new LLStackItem(StackItemType.PROGRAMMING),
                        great = new LLStackItem(StackItemType.GREAT),
                        again = new LLStackItem(StackItemType.AGAIN);
                make.derive(new MockToken("make", TokenType.MAKE));
                programming.derive(new MockToken("programming", TokenType.PROGRAMMING));
                great.derive(new MockToken("great", TokenType.GREAT));
                again.derive(new MockToken("again", TokenType.AGAIN));

                first.derive(make, programming, great, again);
            }

            LLStackItem stmts = new LLStackItem(StackItemType.STMTS);
            {
                LLStackItem stmt = new LLStackItem(StackItemType.STMT);
                {
                    LLStackItem output = new LLStackItem(StackItemType.OUTPUT);
                    {
                        LLStackItem say = new LLStackItem(StackItemType.SAY),
                                string = new LLStackItem(StackItemType.STRING);
                        say.derive(new MockToken("say", TokenType.SAY));
                        string.derive(new MockToken("\"Hello, world!\"", TokenType.STRING));
                        output.derive(say, string);
                    }
                    stmt.derive(output);
                }
                LLStackItem semicolon = new LLStackItem(StackItemType.SEMICOLON);
                semicolon.derive(new MockToken(";", TokenType.SEMICOLON));

                LLStackItem more = new LLStackItem(StackItemType.MORE_STMTS);
                more.derive();

                stmts.derive(stmt, semicolon, more);
            }

            LLStackItem last = new LLStackItem(StackItemType.LAST);
            {
                LLStackItem america = new LLStackItem(StackItemType.AMERICA),
                        is = new LLStackItem(StackItemType.IS),
                        great = new LLStackItem(StackItemType.GREAT);
                america.derive(new MockToken("america", TokenType.AMERICA));
                is.derive(new MockToken("is", TokenType.IS));
                great.derive(new MockToken("great", TokenType.GREAT));

                last.derive(america, is, great);
            }

            program.derive(first, stmts, last);
        }

        assertEquals(program, parser.parse());
    }
}
