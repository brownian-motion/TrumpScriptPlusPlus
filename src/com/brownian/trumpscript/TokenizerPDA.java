package com.brownian.trumpscript;

import com.brownian.trumpscript.token.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;


public class TokenizerPDA {

    private final SymbolTable symbolTable;
    private PushbackReader characterSource;
    private char peek;
    private TokenizerPDAState currentState;
    private StringBuilder tokenBuilder;

    /**
     * @see Reader#read() The value returned when read()ing from the end of the file.
     */
    private final static int EOF = -1;

    //TODO: incorporate symbol table and error handler
    public TokenizerPDA(Reader characterSource, SymbolTable symbolTable) {
        this.characterSource = new PushbackReader(characterSource);
        this.symbolTable = symbolTable;
    }

    public Token getNextToken() throws IOException {
        tokenBuilder = new StringBuilder();
        currentState = TokenizerPDAState.INITIAL_STATE;
        while (true) {
            switch (currentState) {
                case INITIAL_STATE:
                    switch (peek = getChar()) {
                        case 'm':
                        case 'M':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_M_;
                            break;
                        case 'p':
                        case 'P':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_P_;
                            break;
                        case 'g':
                        case 'G':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_G_;
                            break;
                        case 'a':
                        case 'A':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_A_;
                            break;
                        case '"':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.STRING_LITERAL_INCOMPLETE;
                            break;
                        default:
                            if (Character.isWhitespace(peek)) {
                                break;
                            } else if (Character.isAlphabetic(peek)) {
                                tokenBuilder.append(peek);
                                currentState = TokenizerPDAState.ID;
                                break;
                            } else if (Character.isDigit(peek)) {
                                tokenBuilder.append(peek);
                                currentState = TokenizerPDAState.CONST_DIGIT_1;
                                break;
                            } else if (isSpecialCharacter(peek)) {
                                tokenBuilder.append(peek);
                                currentState = TokenizerPDAState.EMIT_SPECIAL_CHARACTER;
                                break;
                            } else {
                                tokenBuilder.append(peek);
                                currentState = TokenizerPDAState.ERR_ID;
                                break;
                            }
                    }

                case ID:
                    peek = getChar();
                    if (Character.isLetterOrDigit(peek)) {
                        tokenBuilder.append(peek);
                        break;
                    } else if (Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_ID;
                        break;
                    } else if (isSpecialCharacter(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_ID;
                        break;
                    } else {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ERR_ID;
                        break;
                    }
                case KEYWORD_M_:
                    peek = getChar();
                    if (peek == 'a') {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.KEYWORD_MA_;
                    } else if (peek == 'o') {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.KEYWORD_MO_;
                    } else if (Character.isLetterOrDigit(peek)) {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ID;
                    } else if (Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_ID;
                    } else if (isSpecialCharacter(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_ID;
                    } else {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ERR_ID;
                    }
                    break;
                case KEYWORD_MA_:
                    peek = getChar();
                    if (peek == 'k') {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.KEYWORD_MAK_;
                    } else if (Character.isLetterOrDigit(peek)) {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ID;
                    } else if (Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_ID;
                    } else if (isSpecialCharacter(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_ID;
                    } else {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ERR_ID;
                    }
                    break;
                case KEYWORD_MAK_:
                    break;
                case KEYWORD_MAKE_:
                    break;
                case KEYWORD_P_:
                    break;
                case KEYWORD_PR_:
                    break;
                case KEYWORD_PRO_:
                    break;
                case KEYWORD_PROG_:
                    break;
                case KEYWORD_PROGR_:
                    break;
                case KEYWORD_PROGRA_:
                    break;
                case KEYWORD_PROGRAM_:
                    break;
                case KEYWORD_PROGRAMM_:
                    break;
                case KEYWORD_PROGRAMMI_:
                    break;
                case KEYWORD_PROGRAMMIN_:
                    break;
                case KEYWORD_PROGRAMMING_:
                    break;
                case KEYWORD_G_:
                    break;
                case KEYWORD_GR_:
                    break;
                case KEYWORD_GRE_:
                    break;
                case KEYWORD_GREA_:
                    break;
                case KEYWORD_GREAT_:
                    break;
                case KEYWORD_A_:
                    break;
                case KEYWORD_AG_:
                    break;
                case KEYWORD_AGA_:
                    break;
                case KEYWORD_AGAI_:
                    break;
                case KEYWORD_AGAIN_:
                    break;
                case KEYWORD_PL_:
                    break;
                case KEYWORD_PLU_:
                    break;
                case KEYWORD_PLUS_:
                    break;
                case KEYWORD_MO_:
                    break;
                case KEYWORD_MOR_:
                    break;
                case KEYWORD_MORE_:
                    break;
                case KEYWORD_N_:
                    break;
                case KEYWORD_NU_:
                    break;
                case KEYWORD_NUM_:
                    break;
                case KEYWORD_NUMB_:
                    break;
                case KEYWORD_NUMBE_:
                    break;
                case KEYWORD_NUMBER_:
                    break;
                case STRING_LITERAL_INCOMPLETE:
                    break;
                case STRING_LITERAL_COMPLETE:
                    break;
                case CONST_DIGIT_1:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek) && peek != '0') {
                        currentState = TokenizerPDAState.CONST_DIGIT_2;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_2:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST_DIGIT_3;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_3:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST_DIGIT_4;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_4:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST_DIGIT_5;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_5:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST_DIGIT_6;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_6:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST:
                    peek = getChar();
                    tokenBuilder.append(peek);
                    if (Character.isDigit(peek)) {
                        currentState = TokenizerPDAState.CONST;
                    } else if (isSpecialCharacter(peek) || Character.isWhitespace(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_CONST;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case EMIT_ID:
                    Token idToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if(idToken == null) {
                        idToken = new IdToken(tokenBuilder.toString());
                        symbolTable.setToken(tokenBuilder.toString(), idToken);
                    }
                    return idToken;
                case EMIT_CONST:
                    Token constToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if(constToken == null) {
                        constToken = new IntegerConstantToken(Long.parseLong(tokenBuilder.toString()));
                        symbolTable.setToken(tokenBuilder.toString(), constToken);
                    }
                    return constToken;
                case EMIT_STRING_LITERAL:
                    Token stringLiteralToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if(stringLiteralToken == null){
                        stringLiteralToken = new StringLiteralToken(tokenBuilder.toString());
                        symbolTable.setToken(tokenBuilder.toString(), stringLiteralToken);
                    }
                    return stringLiteralToken;
                case EMIT_SPECIAL_CHARACTER:
                    return new SpecialCharacterToken(tokenBuilder.charAt(0));
                case EMIT_KEYWORD:
                    return new KeywordToken(tokenBuilder.toString());
                case ERR_ID:
                    //TODO: error handler, return token
                    break;
                case ERR_CONST:
                    //TODO: error handler, return token
                    break;
                case ERR_OTHER:
                    //TODO: error handler, return  token
                    break;
            }
        }
    }

    private boolean isSpecialCharacter(char character) {
        return character == ',' ||  character == ';' ||  character == ':' ||character == '!' || character == '?' ||  character == '(' ||  character == ')';
    }

    private char getChar() throws IOException {
        return (char) characterSource.read();
    }

    private void ungetChar(char character) throws IOException {
        characterSource.unread(character);
    }

    private enum TokenizerPDAState {
        INITIAL_STATE,

        ID,

        KEYWORD_M_, KEYWORD_MA_, KEYWORD_MAK_, KEYWORD_MAKE_,
        KEYWORD_P_, KEYWORD_PR_, KEYWORD_PRO_, KEYWORD_PROG_, KEYWORD_PROGR_, KEYWORD_PROGRA_,
        KEYWORD_PROGRAM_, KEYWORD_PROGRAMM_, KEYWORD_PROGRAMMI_, KEYWORD_PROGRAMMIN_,
        KEYWORD_PROGRAMMING_,
        KEYWORD_G_, KEYWORD_GR_, KEYWORD_GRE_, KEYWORD_GREA_, KEYWORD_GREAT_,
        KEYWORD_A_, KEYWORD_AG_, KEYWORD_AGA_, KEYWORD_AGAI_, KEYWORD_AGAIN_,
        KEYWORD_AM_, KEYWORD_AME_, KEYWORD_AMER_, KEYWORD_AMERI_, KEYWORD_AMERIC_, KEYWORD_AMERICA_,
        KEYWORD_I_, KEYWORD_IS_,
        KEYWORD_E_, KEYWORD_EL_, KEYWORD_ELS_, KEYWORD_ELSE_,
        KEYWORD_N_, KEYWORD_NU_, KEYWORD_NUM_, KEYWORD_NUMB_, KEYWORD_NUMBE_, KEYWORD_NUMBER_,
        KEYWORD_B_, KEYWORD_BO_, KEYWORD_BOO_, KEYWORD_BOOL_, KEYWORD_BOOLE_, KEYWORD_BOOLEA_,
        KEYWORD_BOOLEAN_,
        KEYWORD_IF_,
        KEYWORD_AS_,
        KEYWORD_L_, KEYWORD_LO_, KEYWORD_LON_, KEYWORD_LONG_,
        KEYWORD_T_, KEYWORD_TE_, KEYWORD_TEL_, KEYWORD_TELL_,
        KEYWORD_S_, KEYWORD_SA_, KEYWORD_SAY_,
        KEYWORD_F_, KEYWORD_FA_, KEYWORD_FAC_, KEYWORD_FACT_,
        KEYWORD_LI_, KEYWORD_LIE_,
        KEYWORD_NO_, KEYWORD_NOT_,
        KEYWORD_AN_, KEYWORD_AND_,
        KEYWORD_O_, KEYWORD_OR_,
        KEYWORD_LE_, KEYWORD_LES_, KEYWORD_LESS_,
        KEYWORD_MO_, KEYWORD_MOR_, KEYWORD_MORE_,
        KEYWORD_PL_, KEYWORD_PLU_, KEYWORD_PLUS_,
        KEYWORD_TI_, KEYWORD_TIM_, KEYWORD_TIME_, KEYWORD_TIMES_,

        STRING_LITERAL_INCOMPLETE, STRING_LITERAL_COMPLETE,

        CONST_DIGIT_1, CONST_DIGIT_2, CONST_DIGIT_3, CONST_DIGIT_4, CONST_DIGIT_5, CONST_DIGIT_6, CONST,

        EMIT_ID, EMIT_CONST, EMIT_STRING_LITERAL, EMIT_SPECIAL_CHARACTER, EMIT_KEYWORD,

        ERR_ID, ERR_CONST, ERR_OTHER
    }
}
