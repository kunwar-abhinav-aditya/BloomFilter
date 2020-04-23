package com.task.bloomfilter.exception;

import lombok.Data;

@Data
public class SpellCheckExceptionSchema {
    private String message;
    private String details;
    private String hint;

    protected SpellCheckExceptionSchema() {}

    public SpellCheckExceptionSchema(
            String message, String details, String hint) {
        this.message = message;
        this.details = details;
        this.hint = hint;
    }
}
