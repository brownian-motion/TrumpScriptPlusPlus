package com.brownian.trumpscript;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class SingleCharacterLookaheadReader extends PushbackReader {

    public SingleCharacterLookaheadReader(Reader reader) {
        super(reader);
    }

    /**
     * Peeks ahead at the next character in this reader, and returns it,
     * or returns -1 if the end of the stream has been reached.
     * @return the next character to be read() from the provided Reader, without modifying it.
     * @see #read()
     * @see #unread(int)
     * @throws IOException if an I/O error occurs
     */
    public int peek() throws IOException{
        int out = read();
        unread(out);
        return out;
    }
}
