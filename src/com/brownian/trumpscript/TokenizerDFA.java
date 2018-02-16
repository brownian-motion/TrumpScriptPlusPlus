package com.brownian.trumpscript;

import com.brownian.trumpscript.token.*;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;


public class TokenizerDFA {

    private final SymbolTable symbolTable;
    private PushbackReader characterSource;
    private char peek;
    private boolean isAtEOF = false;
    private TokenizerPDAState currentState;
    private StringBuilder tokenBuilder;

    /**
     * @see Reader#read() The value returned when read()ing from the end of the file.
     */
    private final static int EOF = -1;

    //TODO: incorporate symbol table and error handler
    public TokenizerDFA(Reader characterSource, SymbolTable symbolTable) {
        this.characterSource = new PushbackReader(characterSource);
        this.symbolTable = symbolTable;
    }

    public Token getNextToken() throws IOException {
        tokenBuilder = new StringBuilder();
        currentState = TokenizerPDAState.INITIAL_STATE;
        while (true) {
            switch (currentState) {
                case INITIAL_STATE:
                    advancePeek();
                    switch (peek) {
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
                        case 'i':
                        case 'I':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_I_;
                            break;
                        case 'e':
                        case 'E':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_E_;
                            break;
                        case 'b':
                        case 'B':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_B_;
                            break;
                        case 'l':
                        case 'L':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_L_;
                            break;
                        case 't':
                        case 'T':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_T_;
                            break;
                        case 's':
                        case 'S':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_S_;
                            break;
                        case 'f':
                        case 'F':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_F_;
                            break;
                        case 'o':
                        case 'O':
                            tokenBuilder.append(peek);
                            currentState = TokenizerPDAState.KEYWORD_O_;
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
                    advancePeek();
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
                    advancePeek();
                    if (!tryToGoToKeywordStateWith('a', TokenizerPDAState.KEYWORD_MA_)
                            && !tryToGoToKeywordStateWith('o', TokenizerPDAState.KEYWORD_MO_)) {
                        handleKeywordMismatch();
                    }
                    break;
                case KEYWORD_MA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('k', TokenizerPDAState.KEYWORD_MAK_);
                    break;
                case KEYWORD_MAK_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_POSSIBLE_MATCH:
                    advancePeek();
                    if (isSpecialCharacter(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_KEYWORD;
                    } else if (Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_KEYWORD;
                    } else {
                        handleKeywordMismatch();
                    }
                    break;
                case KEYWORD_P_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_PR_);
                    break;
                case KEYWORD_PR_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('o', TokenizerPDAState.KEYWORD_PRO_);
                    break;
                case KEYWORD_PRO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('g', TokenizerPDAState.KEYWORD_PROG_);
                    break;
                case KEYWORD_PROG_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_PROGR_);
                    break;
                case KEYWORD_PROGR_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_PROGRA_);
                    break;
                case KEYWORD_PROGRA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('m', TokenizerPDAState.KEYWORD_PROGRAM_);
                    break;
                case KEYWORD_PROGRAM_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('m', TokenizerPDAState.KEYWORD_PROGRAMM_);
                    break;
                case KEYWORD_PROGRAMM_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('i', TokenizerPDAState.KEYWORD_PROGRAMMI_);
                    break;
                case KEYWORD_PROGRAMMI_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('n', TokenizerPDAState.KEYWORD_PROGRAMMIN_);
                    break;
                case KEYWORD_PROGRAMMIN_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('g', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
//                case KEYWORD_PROGRAMMING_:
//                    break;
                case KEYWORD_G_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_GR_);
                    break;
                case KEYWORD_GR_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_GRE_);
                    break;
                case KEYWORD_GRE_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_GREA_);
                    break;
                case KEYWORD_GREA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('t', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
//                case KEYWORD_GREAT_:
//                    break;
                case KEYWORD_A_:
                    advancePeek();
                    if (!tryToGoToKeywordStateWith('s', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH)
                            && !tryToGoToKeywordStateWith('n', TokenizerPDAState.KEYWORD_AN_)
                            && !tryToGoToKeywordStateWith('m', TokenizerPDAState.KEYWORD_AM_)) {
                        tryToGoToKeywordStateWithCharElseHandleMismatch('g', TokenizerPDAState.KEYWORD_AG_);
                    }
                    break;
                case KEYWORD_AG_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_AGA_);
                    break;
                case KEYWORD_AGA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('i', TokenizerPDAState.KEYWORD_AGAI_);
                    break;
                case KEYWORD_AGAI_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('n', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
//                case KEYWORD_AGAIN_:
//                    break;
                case KEYWORD_AN_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('d', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                case KEYWORD_PL_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('u', TokenizerPDAState.KEYWORD_PLU_);
                    break;
                case KEYWORD_PLU_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
//                case KEYWORD_PLUS_:
//                    break;
                case KEYWORD_MO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_MOR_);
                    break;
                case KEYWORD_MOR_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
//                case KEYWORD_MORE_:
//                    break;
                case KEYWORD_AM_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_AME_);
                    break;
                case KEYWORD_AME_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_AMER_);
                    break;
                case KEYWORD_AMER_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('i', TokenizerPDAState.KEYWORD_AMERI_);
                    break;
                case KEYWORD_AMERI_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('c', TokenizerPDAState.KEYWORD_AMERIC_);
                    break;
                case KEYWORD_AMERIC_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_I_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_E_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('l', TokenizerPDAState.KEYWORD_EL_);
                    break;
                case KEYWORD_EL_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_ELS_);
                    break;
                case KEYWORD_ELS_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_N_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('u', TokenizerPDAState.KEYWORD_NU_);
                    break;
                case KEYWORD_NU_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('m', TokenizerPDAState.KEYWORD_NUM_);
                    break;
                case KEYWORD_NUM_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('b', TokenizerPDAState.KEYWORD_NUMB_);
                    break;
                case KEYWORD_NUMB_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_NUMBE_);
                    break;
                case KEYWORD_NUMBE_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case STRING_LITERAL_INCOMPLETE:
                    advancePeek();
                    if (isAtEOF()) {
                        currentState = TokenizerPDAState.ERR_OTHER;
                    } else if (peek == '"') {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.STRING_LITERAL_COMPLETE;
                    } else { //TODO: limit characters possible
                        tokenBuilder.append(peek); //and stay on this state
                    }
                    break;
                case STRING_LITERAL_COMPLETE:
                    advancePeek();
                    if (isAtEOF() || Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_STRING_LITERAL;
                    } else {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ERR_OTHER;
                    }
                    break;
                case CONST_DIGIT_1:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST_DIGIT_2);
                    break;
                case CONST_DIGIT_2:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST_DIGIT_3);
                    break;
                case CONST_DIGIT_3:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST_DIGIT_4);
                    break;
                case CONST_DIGIT_4:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST_DIGIT_5);
                    break;
                case CONST_DIGIT_5:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST_DIGIT_6);
                    break;
                case CONST_DIGIT_6:
                    advancePeek();
                    tryToAdvanceConstToState(TokenizerPDAState.CONST);
                    break;
                case CONST:
                    advancePeek();
                    if (isAtEOF()) {
                        currentState = TokenizerPDAState.EMIT_CONST;
                    } else if (Character.isDigit(peek)) {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.CONST;
                    } else if (isSpecialCharacter(peek)) {
                        ungetChar(peek);
                        currentState = TokenizerPDAState.EMIT_CONST;
                    } else if (Character.isWhitespace(peek)) {
                        currentState = TokenizerPDAState.EMIT_CONST;
                    } else {
                        tokenBuilder.append(peek);
                        currentState = TokenizerPDAState.ERR_CONST;
                    }
                    break;
                case EMIT_ID:
                    Token idToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if (idToken == null) {
                        idToken = new IdToken(tokenBuilder.toString());
                        symbolTable.setToken(tokenBuilder.toString(), idToken);
                    }
                    return idToken;
                case EMIT_CONST:
                    Token constToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if (constToken == null) {
                        constToken = new IntegerConstantToken(Long.parseLong(tokenBuilder.toString()));
                        symbolTable.setToken(tokenBuilder.toString(), constToken);
                    }
                    return constToken;
                case EMIT_STRING_LITERAL:
                    Token stringLiteralToken = symbolTable.lookupToken(tokenBuilder.toString());
                    if (stringLiteralToken == null) {
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
                case KEYWORD_B_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('o', TokenizerPDAState.KEYWORD_BO_);
                    break;
                case KEYWORD_BO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('o', TokenizerPDAState.KEYWORD_BOO_);
                    break;
                case KEYWORD_BOO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('l', TokenizerPDAState.KEYWORD_BOOL_);
                    break;
                case KEYWORD_BOOL_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_BOOLE_);
                    break;
                case KEYWORD_BOOLE_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_BOOLEA_);
                    break;
                case KEYWORD_BOOLEA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('n', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_L_:
                    advancePeek();
                    if (!tryToGoToKeywordStateWith('i', TokenizerPDAState.KEYWORD_LI_)
                            && !tryToGoToKeywordStateWith('e', TokenizerPDAState.KEYWORD_LE_)) {
                        tryToGoToKeywordStateWithCharElseHandleMismatch('o', TokenizerPDAState.KEYWORD_LO_);
                    }
                    break;
                case KEYWORD_LO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('n', TokenizerPDAState.KEYWORD_LON_);
                    break;
                case KEYWORD_LON_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('g', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_T_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_TE_);
                    break;
                case KEYWORD_TE_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('l', TokenizerPDAState.KEYWORD_TEL_);
                    break;
                case KEYWORD_TEL_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('l', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_S_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_SA_);
                    break;
                case KEYWORD_SA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('y', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_F_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('a', TokenizerPDAState.KEYWORD_FA_);
                    break;
                case KEYWORD_FA_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('c', TokenizerPDAState.KEYWORD_FAC_);
                    break;
                case KEYWORD_FAC_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('t', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_LI_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_NO_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('t', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_O_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('r', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_LE_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_LES_);
                    break;
                case KEYWORD_LES_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
                case KEYWORD_TI_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('m', TokenizerPDAState.KEYWORD_TIM_);
                    break;
                case KEYWORD_TIM_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('e', TokenizerPDAState.KEYWORD_TIME_);
                    break;
                case KEYWORD_TIME_:
                    advancePeek();
                    tryToGoToKeywordStateWithCharElseHandleMismatch('s', TokenizerPDAState.KEYWORD_POSSIBLE_MATCH);
                    break;
            }
        }
    }

    private void tryToGoToKeywordStateWithCharElseHandleMismatch(char expectedCharacter, TokenizerPDAState nextStateIfMatch) throws IOException {
        if (!tryToGoToKeywordStateWith(expectedCharacter, nextStateIfMatch))
            handleKeywordMismatch();
    }

    private boolean tryToGoToKeywordStateWith(char expectedCharacter, TokenizerPDAState nextStateOnMatch) throws IOException {
        if (Character.toLowerCase(peek) == expectedCharacter) {
            tokenBuilder.append(peek);
            currentState = nextStateOnMatch;
            return true;
        } else return false;
    }

    private void tryToAdvanceConstToState(TokenizerPDAState constDigit6) throws IOException {
        if (isAtEOF()) {
            currentState = TokenizerPDAState.ERR_CONST;
        } else if (Character.isDigit(peek)) {
            tokenBuilder.append(peek);
            currentState = constDigit6;
        } else handleConstBadCharacter();
    }

    private void handleConstBadCharacter() throws IOException {
        if (isSpecialCharacter(peek)) {
            ungetChar(peek);
            currentState = TokenizerPDAState.ERR_CONST;
        } else if (Character.isWhitespace(peek)) {
            currentState = TokenizerPDAState.ERR_CONST;
        } else {
            tokenBuilder.append(peek);
            currentState = TokenizerPDAState.ERR_CONST;
        }
    }

    private void advancePeek() throws IOException {
        peek = getChar();
    }

    /**
     * Called when partially through a keyword, but reading a character
     * that doesn't match the next in line for that keyword.
     * <p>
     * E.g., in state "M A K", reading an "E" would confirm "make", but reading
     * anything else would suggest an ID or something - that's when this is called.
     *
     * @throws IOException
     */
    private void handleKeywordMismatch() throws IOException {
        if (isAtEOF() || Character.isWhitespace(peek)) {
            currentState = TokenizerPDAState.EMIT_ID;
        } else if (Character.isLetterOrDigit(peek)) {
            tokenBuilder.append(peek);
            currentState = TokenizerPDAState.ID;
        } else if (isSpecialCharacter(peek)) {
            ungetChar(peek);
            currentState = TokenizerPDAState.EMIT_ID;
        } else {
            tokenBuilder.append(peek);
            currentState = TokenizerPDAState.ERR_ID;
        }
    }

    private boolean isSpecialCharacter(char character) {
        return character == ',' || character == ';' || character == ':' || character == '!' || character == '?' || character == '(' || character == ')';
    }

    private char getChar() throws IOException {
        int out = characterSource.read();
        isAtEOF = (out == -1);
        return (char) out;
    }

    private void ungetChar(char character) throws IOException {
        characterSource.unread(character);
    }

    public boolean isAtEOF() {
        return isAtEOF;
    }

    private enum TokenizerPDAState {
        INITIAL_STATE,

        ID,

        KEYWORD_M_, KEYWORD_MA_, KEYWORD_MAK_,
        KEYWORD_P_, KEYWORD_PR_, KEYWORD_PRO_, KEYWORD_PROG_, KEYWORD_PROGR_, KEYWORD_PROGRA_,
        KEYWORD_PROGRAM_, KEYWORD_PROGRAMM_, KEYWORD_PROGRAMMI_, KEYWORD_PROGRAMMIN_,
        KEYWORD_G_, KEYWORD_GR_, KEYWORD_GRE_, KEYWORD_GREA_,
        KEYWORD_A_, KEYWORD_AG_, KEYWORD_AGA_, KEYWORD_AGAI_,
        KEYWORD_AM_, KEYWORD_AME_, KEYWORD_AMER_, KEYWORD_AMERI_, KEYWORD_AMERIC_,
        KEYWORD_I_,
        KEYWORD_E_, KEYWORD_EL_, KEYWORD_ELS_,
        KEYWORD_N_, KEYWORD_NU_, KEYWORD_NUM_, KEYWORD_NUMB_, KEYWORD_NUMBE_,
        KEYWORD_B_, KEYWORD_BO_, KEYWORD_BOO_, KEYWORD_BOOL_, KEYWORD_BOOLE_, KEYWORD_BOOLEA_,
        KEYWORD_L_, KEYWORD_LO_, KEYWORD_LON_,
        KEYWORD_T_, KEYWORD_TE_, KEYWORD_TEL_,
        KEYWORD_S_, KEYWORD_SA_,
        KEYWORD_F_, KEYWORD_FA_, KEYWORD_FAC_,
        KEYWORD_LI_,
        KEYWORD_NO_,
        KEYWORD_AN_,
        KEYWORD_O_,
        KEYWORD_LE_, KEYWORD_LES_,
        KEYWORD_MO_, KEYWORD_MOR_,
        KEYWORD_PL_, KEYWORD_PLU_,
        KEYWORD_TI_, KEYWORD_TIM_, KEYWORD_TIME_,
        KEYWORD_POSSIBLE_MATCH,

        STRING_LITERAL_INCOMPLETE, STRING_LITERAL_COMPLETE,

        CONST_DIGIT_1, CONST_DIGIT_2, CONST_DIGIT_3, CONST_DIGIT_4, CONST_DIGIT_5, CONST_DIGIT_6, CONST,

        EMIT_ID, EMIT_CONST, EMIT_STRING_LITERAL, EMIT_SPECIAL_CHARACTER, EMIT_KEYWORD,

        ERR_ID, ERR_CONST, ERR_OTHER
    }
}
