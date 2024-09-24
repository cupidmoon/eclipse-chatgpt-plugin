package com.ktds.eclipse.aion.codeassistant.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.ktds.eclipse.aion.codeassistant.part.ChatGPTPresenter;
import com.ktds.eclipse.aion.codeassistant.prompt.ChatMessageFactory;
import com.ktds.eclipse.aion.codeassistant.prompt.Prompts;

import jakarta.inject.Inject;
import jakarta.inject.Named;

public class AionUJavaUpgradeHandler
{
    @Inject
    private ILog logger;
    @Inject
    private ChatMessageFactory chatMessageFactory;
    @Inject
    private ChatGPTPresenter viewPresenter;
    
    @Execute
    public void execute( @Named( IServiceConstants.ACTIVE_SHELL ) Shell s )
    {
        var activeFile = "";
        var filePath = "";
        var ext = "";
        var fileContents = "";
        
        // Get the active workbench window
        var workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

        // Get the active editor's input file
        var activeEditor = workbenchWindow.getActivePage().getActiveEditor();
        
        if (activeEditor instanceof ITextEditor)
        {
            ITextEditor textEditor = (ITextEditor) activeEditor;
            activeFile = textEditor.getEditorInput().getName();
            // Read the content from the file
            // this fixes skipped empty lines issue
            IFile file = (IFile) textEditor.getEditorInput().getAdapter(IFile.class);
            try  
            {
                fileContents = new String( Files.readAllBytes( file.getLocation().toFile().toPath() ), StandardCharsets.UTF_8 );
            } 
            catch (IOException e) 
            {
                throw new RuntimeException(e);
            }
            filePath     = file.getProjectRelativePath().toString(); // use project relative path
            ext          = activeFile.substring( activeFile.lastIndexOf( "." )+1 );
        }
        
        logger.info("filePath = " + filePath);
        
	    var context = new Context( filePath, fileContents, "", "", "", ext );
	    var message = chatMessageFactory.createUserChatMessage( Prompts.JAVA_UPGRADE, context );
	    viewPresenter.onSendPredefinedPrompt( Prompts.JAVA_UPGRADE, message );
    }
}
