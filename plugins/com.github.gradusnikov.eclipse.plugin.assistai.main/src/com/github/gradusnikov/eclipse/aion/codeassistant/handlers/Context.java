package com.github.gradusnikov.eclipse.aion.codeassistant.handlers;

public record Context(
    String fileName,
    String fileContents,
    String selectedContent,
    String selectedItem,
    String selectedItemType,
    String lang) {}
