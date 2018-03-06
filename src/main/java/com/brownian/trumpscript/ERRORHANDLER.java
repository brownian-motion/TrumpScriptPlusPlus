package com.brownian.trumpscript;

import com.brownian.trumpscript.error.TrumpscriptError;

import java.io.PrintStream;

public class ERRORHANDLER {
    private PrintStream outputStreamWriter;

    public ERRORHANDLER(PrintStream outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    public void logError(TrumpscriptError error){
        outputStreamWriter.println(error.getMessage());
    }
}
