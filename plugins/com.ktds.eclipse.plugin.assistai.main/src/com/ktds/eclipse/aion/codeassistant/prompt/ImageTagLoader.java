package com.ktds.eclipse.aion.codeassistant.prompt;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

import org.eclipse.core.runtime.FileLocator;

import jakarta.inject.Singleton;

@Singleton
public class ImageTagLoader {
	static private ImageTagLoader instance;
	static private String[] iconFiles = {
		 "text_compare_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png",
		 "content_copy_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png",
		 "text_select_move_forward_character_24dp_5F6368_FILL0_wght400_GRAD0_opsz24.png"
	};
	
	private HashMap<String, String> iconImages = new HashMap<>();
	
    public static ImageTagLoader getInstance() {
        if (instance == null) {
            instance = new ImageTagLoader();
        }
        return instance;
    }	
    
    ImageTagLoader()
    {
        try
        {
        	for(var iconFile :iconFiles)
        	{
        		iconImages.put(iconFile,
        				Base64.getEncoder().encodeToString(FileLocator.toFileURL(
        						new URL( "platform:/plugin/com.ktds.eclipse.plugin.aion.codeassistant.main/img/" + iconFile))
        						.openStream()
        						.readAllBytes()));
        	}
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }
	}
	
    public String getImageTag(String imageName, int size)
    {
		 return String.format("<img height=\"%d\" src=\"data:image/%s;base64,%s\" alt=\"Embedded Image\" title=\"Apply to current file\">", 
                 size, "png", iconImages.get(imageName));
    }
}

