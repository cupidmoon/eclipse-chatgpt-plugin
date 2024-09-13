package com.ktds.eclipse.aion.codeassistant.preferences;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.ktds.eclipse.aion.codeassistant.Activator;


public class ModelPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage
{
    
    private UISynchronize uiSync;
    private IPropertyChangeListener apiKeyListener = e -> {
        if( PreferenceConstants.AION_BASE_URL.equals( e.getProperty() ) ||
        		PreferenceConstants.AION_GET_MODEL_API_PATH.equals( e.getProperty() ) ||
        		PreferenceConstants.AION_API_BASE_URL.equals( e.getProperty() ) ||
        		PreferenceConstants.AION_API_KEY.equals( e.getProperty() ) 
        		)
        {
            uiSync.asyncExec( () -> {

            });        	
        }
    };
    
    public ModelPreferencePage()
    {
        super( GRID );
        setPreferenceStore( Activator.getDefault().getPreferenceStore() );
        setDescription( "Model API settings" );
        
        getPreferenceStore().addPropertyChangeListener( apiKeyListener ); 
    }


    /**
     * Creates the field editors. Field editors are abstractions of the common
     * GUI blocks needed to manipulate various types of preferences. Each field
     * editor knows how to save and restore itself.
     */
    @Override
    public void createFieldEditors()
    {
        addField( new StringFieldEditor(
        		PreferenceConstants.AION_API_BASE_URL, "&AION-U Chat App Url:", getFieldEditorParent()));

        addField( new StringFieldEditor(
        		PreferenceConstants.AION_BASE_URL, "&AION-U Base Url:", getFieldEditorParent()));

        addField( new StringFieldEditor(
        		PreferenceConstants.AION_GET_MODEL_API_PATH, "&AION-U Model API Path:", getFieldEditorParent()));
        
        addField( new StringFieldEditor(
        		PreferenceConstants.AION_API_KEY, "&AION-U API Key:", getFieldEditorParent()));
        
    }
    
    
    
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init( IWorkbench workbench )
    {
        // workaroud to get UISynchronize as PreferencePage does not seem to
        // be handled by the eclipse context
        IEclipseContext eclipseContext = workbench.getService( IEclipseContext.class );
        uiSync = eclipseContext.get( UISynchronize.class );
    }
    
    @Override
    public void dispose()
    {
        getPreferenceStore().removePropertyChangeListener( apiKeyListener );
        super.dispose();
    }

}