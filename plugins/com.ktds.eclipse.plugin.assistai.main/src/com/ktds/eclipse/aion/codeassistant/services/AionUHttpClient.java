package com.ktds.eclipse.aion.codeassistant.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ILog;
import org.eclipse.e4.core.di.annotations.Creatable;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktds.eclipse.aion.codeassistant.model.AionUModelDescriptor;
import com.ktds.eclipse.aion.codeassistant.preferences.PreferenceConstants;

import jakarta.inject.Inject;

@Creatable
public class AionUHttpClient {

    @Inject
    private ILog logger;

    @Inject
    private AionUClientConfiguration configuration;
	
	private HttpClient getHttpClient()
	{
	    return HttpClient.newBuilder()
                .connectTimeout( Duration.ofSeconds(configuration.getConnectionTimoutSeconds()) )
                .build();
	}
	
	public List<AionUModelDescriptor> getAionUModelList()
	{
		HttpClient httpClient = getHttpClient();
		
		String modelApiUrl = configuration.getAionUBaseUrl() + configuration.getAionUModelApiPath(); 

	    List<AionUModelDescriptor> models = new ArrayList<>();
	    
		try
		{
			URI	uri = URI.create(modelApiUrl);
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(uri)
				    .timeout( Duration.ofSeconds( configuration.getRequestTimoutSeconds() ) )
	                .version(HttpClient.Version.HTTP_1_1)
					.header("Authorization", configuration.getAionUApiKey())
					.header("Content-Type", "application/json")
					.build();
		
			HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());

		    ObjectMapper mapper = new ObjectMapper();

		    JsonNode rootNode = mapper.readTree(response.body());
		    logger.info(rootNode.toString());
		    logger.info("Array Size = " + rootNode.size());
		    
		    for(int i=0; i < rootNode.size(); i++)
		    {
		    	JsonNode modelNode = rootNode.get(i);
			    logger.info(modelNode.toString());
		    	
		    	models.add(
		    			new AionUModelDescriptor(
		    					modelNode.get("apiBase") == null || modelNode.get("apiBase").asText().isEmpty() ?
		    							configuration.getAionUApiBaseUrl() : modelNode.get("apiBase").asText(),
				    			modelNode.get("model").asText(),
		    					modelNode.get("apiKey").asText(),
		    					modelNode.get("provider").asText(),
		    					modelNode.get("title").asText(),
		    					false,
		    					false)
		    			);
		    }
		}
		catch (IOException e) {}
		catch (InterruptedException e) {}
		catch (IllegalArgumentException e) {}		
		
		return models;
	}
}
