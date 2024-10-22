package com.ktds.eclipse.aion.codeassistant.prompt;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;

import org.eclipse.core.runtime.FileLocator;

import jakarta.inject.Singleton;

@Singleton
public class ImageTagLoader {
	private String applyPatch;
	private String copyClipboard;
	private String copyToCursor;
	static private ImageTagLoader instance;

    public static ImageTagLoader getInstance() {
        if (instance == null) {
            instance = new ImageTagLoader();
        }
        return instance;
    }	
    
    public ImageTagLoader()
    {
        try
        {
            applyPatch = Base64.getEncoder().encodeToString(FileLocator.toFileURL(
       			 new URL( "platform:/plugin/com.ktds.eclipse.plugin.aion.codeassistant.main/img/" + 
    					 "text_compare_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png" ) )
                 .openStream()
                 .readAllBytes());
            
            copyClipboard = Base64.getEncoder().encodeToString(FileLocator.toFileURL(
       			 new URL( "platform:/plugin/com.ktds.eclipse.plugin.aion.codeassistant.main/img/" + 
    					 "content_copy_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png" ) )
                 .openStream()
                 .readAllBytes());
            
            copyToCursor = Base64.getEncoder().encodeToString(FileLocator.toFileURL(
       			 new URL( "platform:/plugin/com.ktds.eclipse.plugin.aion.codeassistant.main/img/" + 
    					 "text_select_move_forward_character_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png" ) )
                 .openStream()
                 .readAllBytes());
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
	}
	
	public String getApplyPatch(int size)
	{
		 return String.format("<img height=\"%d\" src=\"data:image/%s;base64,%s\" alt=\"Embedded Image\" title=\"Apply to current file\">", 
                 size, "png", applyPatch);
	}

	public String getCopyClipboard(int size)
	{
		 return String.format("<img height=\"%d\" src=\"data:image/%s;base64,%s\" alt=\"Embedded Image\" title=\"Copy codes\">", 
                size, "png", copyClipboard);
	}

	public String getCopyToCursor(int size)
	{
		 return String.format("<img height=\"%d\" src=\"data:image/%s;base64,%s\" alt=\"Embedded Image\" title=\"Insert at cursor\">", 
                size, "png", copyToCursor);
	}
}

