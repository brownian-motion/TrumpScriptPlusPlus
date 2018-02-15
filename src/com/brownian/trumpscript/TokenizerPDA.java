package com.brownian.trumpscript;

import com.brownian.trumpscript.token.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;


public class TokenizerPDA {

    private PushbackReader characterSource;

    /**
     * @see Reader#read() The value returned when read()ing from the end of the file.
     */
    private final static int EOF = -1;

    //TODO: incorporate symbol table and error handler
    public TokenizerPDA(Reader characterSource) {
        this.characterSource = new PushbackReader(characterSource);
    }

    public Token getNextToken() throws IOException {
        StringBuilder tokenBuilder = new StringBuilder();
        char currentCharacter;
        TokenizerPDAState currentState = TokenizerPDAState.INITIAL_STATE;
        while (true) {
            switch (currentState) {
                case INITIAL_STATE:
                    switch (currentCharacter = getChar()) {
                        case 'm':
                        case 'M':
                            tokenBuilder.append(currentCharacter);
                            currentState = TokenizerPDAState.KEYWORD_M_;
                            break;
                        case 'p':
                        case 'P':
                            tokenBuilder.append(currentCharacter);
                            currentState = TokenizerPDAState.KEYWORD_P_;
                            break;
                        case 'g':
                        case 'G':
                            tokenBuilder.append(currentCharacter);
                            currentState = TokenizerPDAState.KEYWORD_G_;
                            break;
                        case 'a':
                        case 'A':
                            tokenBuilder.append(currentCharacter);
                            currentState = TokenizerPDAState.KEYWORD_A_;
                            break;
                        case '"':
                            tokenBuilder.append(currentCharacter);
                            currentState = TokenizerPDAState.STRING_LITERAL_INCOMPLETE;
                            break;
                        default:
                            if (Character.isWhitespace(currentCharacter)) {
                                break;
                            } else if (Character.isAlphabetic(currentCharacter)) {
                                tokenBuilder.append(currentCharacter);
                                currentState = TokenizerPDAState.ID;
                                break;
                            } else if (Character.isDigit(currentCharacter)) {
                                tokenBuilder.append(currentCharacter);
                                currentState = TokenizerPDAState.CONST_DIGIT_1;
                                break;
                            } else if (isSpecialCharacter(currentCharacter)) {
                                tokenBuilder.append(currentCharacter);
                                currentState = TokenizerPDAState.EMIT_SPECIAL_CHARACTER;
                                break;
                            } else {
                                tokenBuilder.append(currentCharacter);
                                currentState = TokenizerPDAState.ERR_ID;
                                break;
                            }
                    }

                case ID:

                    break;
                case KEYWORD_M_:
                    break;
                case KEYWORD_MA_:
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
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter) && currentCharacter != '0'){
                        currentState = TokenizerPDAState.CONST_DIGIT_2;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_2:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST_DIGIT_3;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_3:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST_DIGIT_4;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_4:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST_DIGIT_5;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_5:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST_DIGIT_6;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST_DIGIT_6:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case CONST:
                    currentCharacter = getChar();
                    tokenBuilder.append(currentCharacter);
                    if(Character.isDigit(currentCharacter)){
                        currentState = TokenizerPDAState.CONST;
                    } else if(isSpecialCharacter(currentCharacter) || Character.isWhitespace(currentCharacter)) {
                        ungetChar(currentCharacter);
                        currentState = TokenizerPDAState.EMIT_CONST;
                    } else {
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case EMIT_ID:
                    return new IdToken(tokenBuilder.toString());
                case EMIT_CONST:
                    return new IntegerConstantToken(Long.parseLong(tokenBuilder.toString()));
                case EMIT_STRING_LITERAL:
                    return new StringLiteralToken(tokenBuilder.toString());
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

    private boolean isSpecialCharacter(char character){
        return character == '.' || character == '?' || character == '!' || character == ';'; //TODO: check this is complete
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
        KEYWORD_PL_, KEYWORD_PLU_, KEYWORD_PLUS_,
        KEYWORD_MO_, KEYWORD_MOR_, KEYWORD_MORE_,
        KEYWORD_N_, KEYWORD_NU_, KEYWORD_NUM_, KEYWORD_NUMB_, KEYWORD_NUMBE_, KEYWORD_NUMBER_,

        STRING_LITERAL_INCOMPLETE, STRING_LITERAL_COMPLETE,

        CONST_DIGIT_1, CONST_DIGIT_2, CONST_DIGIT_3, CONST_DIGIT_4, CONST_DIGIT_5, CONST_DIGIT_6, CONST,

        EMIT_ID, EMIT_CONST, EMIT_STRING_LITERAL, EMIT_SPECIAL_CHARACTER, EMIT_KEYWORD,

        ERR_ID, ERR_CONST, ERR_OTHER
    }
}
