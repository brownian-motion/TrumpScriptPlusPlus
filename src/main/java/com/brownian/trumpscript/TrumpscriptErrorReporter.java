package com.brownian.trumpscript;

import com.brownian.trumpscript.tokenizer.error.TrumpscriptError;

import java.io.PrintStream;

public class TrumpscriptErrorReporter {
    private PrintStream outputStreamWriter;

    public TrumpscriptErrorReporter(PrintStream outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    public void logError(TrumpscriptError error){
        outputStreamWriter.println(error.getMessage());
    }
}
