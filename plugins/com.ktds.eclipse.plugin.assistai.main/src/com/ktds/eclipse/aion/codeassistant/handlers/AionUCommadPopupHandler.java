package com.ktds.eclipse.aion.codeassistant.handlers;

import org.eclipse.core.runtime.ILog;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.ktds.eclipse.aion.codeassistant.part.ChatGPTPresenter;
import com.ktds.eclipse.aion.codeassistant.prompt.ChatMessageFactory;

import jakarta.inject.Inject;
import jakarta.inject.Named;

public class AionUCommadPopupHandler
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
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        InputDialog dialog = new InputDialog(window.getShell(), "Input Dialog", "Please enter something:", "", null);
        if (dialog.open() == InputDialog.OK) {
            String userInput = dialog.getValue();
        }
    }
}
