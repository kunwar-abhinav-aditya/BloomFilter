package com.task.bloomfilter.exception;

import lombok.Data;

@Data
public class SpellCheckException extends RuntimeException {
    private static final long serialVersionUID = 7718828512143293558L;

    private int errorCode;
    private String message;
    private String details;
    private String hint;

    protected SpellCheckException() {}

    public SpellCheckException(
            int errorCode, String message, String details, String hint) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.hint = hint;
    }
}
