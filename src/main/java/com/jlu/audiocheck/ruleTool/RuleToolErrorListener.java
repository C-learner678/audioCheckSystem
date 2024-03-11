package com.jlu.audiocheck.ruleTool;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.Collections;
import java.util.List;

public class RuleToolErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws RuntimeException{
        StringBuilder message = new StringBuilder();
        List<String> stack = ((Parser)recognizer).getRuleInvocationStack();
        Collections.reverse(stack);
        message.append("\n").append("rule stack: ").append(stack).append("\n");
        message.append("line").append(line).append(":").append(charPositionInLine).append(" at ").append(offendingSymbol).append(": ").append(msg);
        throw new RuleException(String.valueOf(message));
    }
}
