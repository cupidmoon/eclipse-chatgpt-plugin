package com.ktds.eclipse.aion.codeassistant.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;

import com.ktds.eclipse.aion.codeassistant.Activator;
import com.ktds.eclipse.aion.codeassistant.prompt.Prompts;

public class AionUHanddlerInvoker {
	public static void Invoke(String command)
	{
		Invoke(Prompts.valueOf(command.replace(' ', '_').toUpperCase().substring(1)));
	}
	
	public static void Invoke(Prompts prompt)
	{
    	try {
    		switch(prompt)
    		{
    		case SYSTEM:
    			break;
    		case DISCUSS:
    			((AionUDiscussCodeHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUDiscussCodeHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case DOCUMENT:
    			((AionUJavaDocHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUJavaDocHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case FIX_ERRORS:
    			((AionUFixErrorsHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUFixErrorsHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case GIT_COMMENT:
    			((AionUGenerateGitCommentHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUGenerateGitCommentHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case REFACTOR:
    			((AionUCodeRefactorHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUCodeRefactorHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case JUNIT_TEST_CASE:
    			((AionUUnitTestHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUUnitTestHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		    // AION-U Custom
    		case UPGRADE_SOURCE:
    			((AionUJavaUpgradeHandler)ContextInjectionFactory.make(
    					Activator.getBundleContext().getBundle().loadClass(AionUJavaUpgradeHandler.class.getName()), 
    					EclipseContextFactory.getServiceContext(Activator.getBundleContext()))).runPrompt();;
    			break;
    		case DISCUSS_SELECTED:
    			break;
    		}
		} catch (ClassNotFoundException e) {
		}
		
	}
}
