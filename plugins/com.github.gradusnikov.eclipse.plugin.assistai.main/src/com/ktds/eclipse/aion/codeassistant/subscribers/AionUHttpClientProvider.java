package com.ktds.eclipse.aion.codeassistant.subscribers;


import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;

import com.ktds.eclipse.aion.codeassistant.services.AionUStreamJavaHttpClient;

@Creatable
@Singleton
public class AionUHttpClientProvider
{
    @Inject
    private Provider<AionUStreamJavaHttpClient> clientProvider;
    @Inject
    private AppendMessageToViewSubscriber appendMessageToViewSubscriber;
    @Inject
    private FunctionCallSubscriber functionCallSubscriber;
    @Inject
    private PrintMessageSubscriber printMessageSubscriber;
    
    public AionUStreamJavaHttpClient get()
    {
        AionUStreamJavaHttpClient client = clientProvider.get();
        client.subscribe( printMessageSubscriber );
        client.subscribe( appendMessageToViewSubscriber );
        client.subscribe( functionCallSubscriber );
        return client;
    }
}
