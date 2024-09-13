package com.ktds.eclipse.aion.codeassistant.model;

public record AionUModelDescriptor(
		String baseUri,
		String model,
		String apiKey,
		String providfer,
		String title,
        boolean vision,
        boolean functionCalling
		) {}
