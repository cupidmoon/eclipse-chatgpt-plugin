package com.ktds.eclipse.aion.codeassistant.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.ktds.eclipse.aion.codeassistant.part.ChatGPTPresenter;
import com.ktds.eclipse.aion.codeassistant.part.PartAccessor;
import com.ktds.eclipse.aion.codeassistant.prompt.Prompts;

import jakarta.inject.Inject;
import jakarta.inject.Named;

public class AionUCommadChatHandler // extends AionUHandlerTemplate
{

	@Inject
    private ChatGPTPresenter presenter;

	@Inject
	private PartAccessor     partAccessor;

//    public AionUCommadChatHandler()
//    {
//        super( Prompts.DISCUSS_SELECTED );
//    }
//
    @Execute
    public void execute( @Named( IServiceConstants.ACTIVE_SHELL ) Shell s )
    {
    	runPrompt();
    }
    
    public void runPrompt()
    {
        var activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        var activeEditor = activePage.getActiveEditor();


        // Check if it is a text editor
        if (activeEditor instanceof ITextEditor)
        {
            ITextEditor textEditor = (ITextEditor) activeEditor;

            // Retrieve the document and text selection
            ITextSelection textSelection = (ITextSelection) textEditor.getSelectionProvider().getSelection();
            var selectedText = textSelection.getText();

            if(selectedText.length() > 0)
            {
//                var message = presenter.InsertInputMessageBlock();
                var message = presenter.InsertInputMessageBlock();
                message.append("```\n" + selectedText + "\n```\n\n");
                presenter.updateMessageFromUI( message );
            }
            
            partAccessor.findMessageView().ifPresent( messageView -> {
            	messageView.setFocus();
            });

        }
    }
}
