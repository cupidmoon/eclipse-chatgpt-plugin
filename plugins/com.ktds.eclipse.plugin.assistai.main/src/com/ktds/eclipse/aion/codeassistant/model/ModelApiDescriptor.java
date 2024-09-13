package com.ktds.eclipse.aion.codeassistant.model;

/**
 * 
 */
public record ModelApiDescriptor(
         String uid,
         String apiType,
         String apiUrl,
         String apiKey,
         String modelName,
         int temperature,
         boolean vision,
         boolean functionCalling
         ) {} 