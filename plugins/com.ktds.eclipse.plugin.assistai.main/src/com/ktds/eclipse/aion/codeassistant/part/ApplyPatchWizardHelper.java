package com.ktds.eclipse.aion.codeassistant.part;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.patch.ApplyPatchOperation;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.DiffTreeViewer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;


/**
 * A helper class for displaying the apply patch wizard dialog in the Eclipse IDE.
 * Allows users to apply a patch to a specified target path.
 */
@Creatable
@Singleton
public class ApplyPatchWizardHelper
{
    @Inject
    private ILog logger;
    
    
    public void changeToCompareEditorWithChanges( String codeBlock ) {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorPart editor = page.getActiveEditor();
        
        IFile file = (IFile) editor.getEditorInput().getAdapter(IFile.class);
        
        if (file != null) {
            // 현재 에디터의 내용 가져오기
            IDocument rightDocument = new Document(codeBlock);
            
            // Compare 에디터 설정
            CompareConfiguration config = new CompareConfiguration();
            config.setLeftLabel(file.getName());
            config.setRightLabel("Updated Code");
            config.setLeftEditable(true);
            config.setRightEditable(false);
            config.setProperty(CompareConfiguration.IGNORE_WHITESPACE, true);
            
            
            CompareEditorInput input = new CompareEditorInput(config) {

            	@Override
                protected Object prepareInput(IProgressMonitor monitor) {
                    return new DiffNode(
                            new FileCompareInput(file),
                            new TextCompareInput(rightDocument));
                }

                @Override
                public Viewer createDiffViewer(Composite parent) {
                	// TODO Auto-generated method stub
                	logger.info("Craet TextMergeViewer");
                	return new TextMergeViewer(parent, config);
                }
                
            };
            
            input.setTitle("Patch: " + file.getName());
            
            // Compare 에디터 열기
            CompareUI.openCompareEditorOnPage(input, page);
        }
    }
    
    /**
     * Displays the apply patch wizard dialog to the user, allowing them to apply a patch to a specified target path.
     *
     * @param patch The patch content as a string.
     * @param targetPath The target path where the patch will be applied.
     */
    public void showApplyPatchWizardDialog(String patch, String targetPath) {
        // Create an InputStream from the patch string
        try ( var patchInputStream = new ByteArrayInputStream(patch.getBytes(StandardCharsets.UTF_8) ) )
        {
			// Create an IStorage object to wrap the InputStream
			var patchStorage = new PatchStorage( patch );
			var window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			var part = window.getActivePage().getActivePart();
			// TODO: for the moment assume the target is associated with the project of currently opened editor
			// which may not be true
		    var target = getProjectOfCurrentlyOpenedEditor();
		    
		    ApplyPatchOperation operation = new ApplyPatchOperation( part, patchStorage, target, new CompareConfiguration() );
		
		    // Create and open the WizardDialog
			operation.openWizard();
        } 
        catch (Exception e) 
        {
            logger.error( e.getLocalizedMessage(), e );
        }
    }
    

    /**
     * Returns the {@link IProject} of the project associated with the currently opened file in the text editor.
     *
     * @return The {@link IProject} of the project, or null if the active editor is not a text editor.
     */
    private IResource getProjectOfCurrentlyOpenedEditor() 
    {
        var window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        var activePage = window.getActivePage();
        var activeEditor = activePage.getActiveEditor();
        if ( activeEditor instanceof ITextEditor )
        {
            ITextEditor textEditor = (ITextEditor) activeEditor;
            IFile file = textEditor.getEditorInput().getAdapter(IFile.class);
            IProject project = file.getProject();
            return project;
//            return file
        }
        else
        {
            return null;
        }
    }
    private static class PatchStorage implements IStorage
    {
        private final String patch;
        public PatchStorage(String patch)
        {
            this.patch = patch;
        }
        @Override
        public <T> T getAdapter( Class<T> arg0 )
        {
            return null;
        }

        @Override
        public InputStream getContents() throws CoreException
        {
            return new ByteArrayInputStream(patch.getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public IPath getFullPath()
        {
            return null;
        }

        @Override
        public String getName()
        {
            return "patch";
        }

        @Override
        public boolean isReadOnly()
        {
            return true;
        }
        
    }
    
}
