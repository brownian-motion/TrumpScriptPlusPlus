package com.brownian.trumpscript;

import com.brownian.trumpscript.tokenizer.TrumpscriptError;

import java.io.PrintStream;

public class ErrorReporter {
    private PrintStream outputStreamWriter;

    public ErrorReporter(PrintStream outputStreamWriter) {
        this.outputStreamWriter = outputStreamWriter;
    }

    public void logError(TrumpscriptError error){
        outputStreamWriter.println(error.getMessage());
    }
}
