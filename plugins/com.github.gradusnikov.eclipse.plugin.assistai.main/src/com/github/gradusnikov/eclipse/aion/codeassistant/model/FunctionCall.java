package com.github.gradusnikov.eclipse.aion.codeassistant.model;

import java.util.Map;

public record FunctionCall( String name, Map<String, String> arguments  ) {}
