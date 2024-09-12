package com.ktds.eclipse.aion.codeassistant.services;

import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.preference.IPreferenceStore;

import com.ktds.eclipse.aion.codeassistant.Activator;
import com.ktds.eclipse.aion.codeassistant.model.AionUModelDescriptor;
import com.ktds.eclipse.aion.codeassistant.preferences.PreferenceConstants;

@Creatable
@Singleton
public class AionUClientConfiguration 
{
	private List<AionUModelDescriptor> modelList;

	public void setModelList(List<AionUModelDescriptor> modelList)
	{
		this.modelList = modelList;
	}
	
	public Optional<AionUModelDescriptor> getModelDescriptor(String modelName)
	{
		return modelList.stream().filter(t -> t.title().equals(modelName)).findFirst();
	}
	
    public Optional<String> getSelectedModel()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return Optional.of(prefernceStore.getString( PreferenceConstants.AION_SELECTED_MODEL));
    }
    
    public void setSelectedModel(String model)
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        prefernceStore.setValue(PreferenceConstants.AION_SELECTED_MODEL, model);
    }

    public String getAionUApiKey()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return prefernceStore.getString( PreferenceConstants.AION_API_KEY );
    }
    
    public String getAionUBaseUrl()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return prefernceStore.getString( PreferenceConstants.AION_BASE_URL );
    }
    
    public String getAionUApiBaseUrl()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return prefernceStore.getString( PreferenceConstants.AION_API_BASE_URL );
    }
    
    public String getAionUModelApiPath()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return prefernceStore.getString( PreferenceConstants.AION_GET_MODEL_API_PATH );
    }
    
    public int getConnectionTimoutSeconds()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return Integer.parseInt( prefernceStore.getString(PreferenceConstants.AION_CONNECTION_TIMEOUT_SECONDS) );
    }
    
    public int getRequestTimoutSeconds()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return Integer.parseInt( prefernceStore.getString(PreferenceConstants.AION_REQUEST_TIMEOUT_SECONDS) );
    }
    
    public Optional<String> getConversationId()
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        return Optional.of(prefernceStore.getString(PreferenceConstants.AION_CONVERSATION_ID));
    }
    
    public void setConversationId(String conversationId)
    {
        IPreferenceStore prefernceStore = Activator.getDefault().getPreferenceStore();
        prefernceStore.setValue(PreferenceConstants.AION_CONVERSATION_ID, conversationId);
    }
}
