package com.ktds.eclipse.aion.codeassistant.model;

public record Incoming( Type type, String payload )
{
    public enum Type
    {
        CONTENT,
        FUNCTION_CALL
    }
}
