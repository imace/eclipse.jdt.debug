/*******************************************************************************
 * Copyright (c) 2007, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.debug.tests.launching;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTabGroup;
import org.eclipse.jdt.debug.testplugin.JavaAlernateModeTab;
import org.eclipse.jdt.debug.testplugin.launching.ContributedTestTab1;
import org.eclipse.jdt.debug.testplugin.launching.ContributedTestTab2;
import org.eclipse.jdt.debug.testplugin.launching.ContributedTestTab3;
import org.eclipse.jdt.debug.tests.AbstractDebugTest;

/**
 * This test class provides test methods for contributing tabs to a given (existing)
 * tab group and their relative placement (if applicable)
 * 
 * @since 3.3
 */
public class ContributedTabTests extends AbstractDebugTest {

	/**
	 * Constructor
	 * @param name the name of the test
	 */
	public ContributedTabTests(String name) {
		super(name);
	}
	
	/**
	 * Checks to make sure that all of the contributed tabs to the java tab group are present 
	 * @throws CoreException
	 */
	public void testContributedTab() throws CoreException {
		ILaunchConfigurationTabGroup javagroup = getJavaLaunchGroup();
		assertNotNull("Java tab group cannot be null", javagroup); //$NON-NLS-1$
		javagroup.createTabs(getLaunchConfigurationDialog(IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP), ILaunchManager.DEBUG_MODE);
		ILaunchConfigurationTab[] tabs = javagroup.getTabs();
		assertEquals("Wrong number of tabs", 11, tabs.length); //$NON-NLS-1$
		Set<Class<? extends ILaunchConfigurationTab>> tabset = new HashSet<Class<? extends ILaunchConfigurationTab>>();
		for(int i = 0; i < tabs.length; i++) {
			tabset.add(tabs[i].getClass());
		}
		Set<Class<? extends ILaunchConfigurationTab>> contribs = new HashSet<Class<? extends ILaunchConfigurationTab>>();
		contribs.add(ContributedTestTab1.class);
		contribs.add(JavaAlernateModeTab.class);
		assertTrue("java tab group should contain all contributed tabs", tabset.containsAll(contribs)); //$NON-NLS-1$
	}
	
	/**
	 * Checks to make sure that a contributed tab with a relative placement to an existing tab is in the correct place
	 * @throws CoreException
	 */
	public void testContributedTabRelativePlacement() throws CoreException {
		ILaunchConfigurationTabGroup javagroup = getJavaLaunchGroup();
		assertNotNull("java tab group cannot be null", javagroup); //$NON-NLS-1$
		javagroup.createTabs(getLaunchConfigurationDialog(IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP), ILaunchManager.DEBUG_MODE);
		ILaunchConfigurationTab[] tabs = javagroup.getTabs();
		assertTrue("The second tab must be TestTab1", tabs[1].getClass().equals(ContributedTestTab1.class)); //$NON-NLS-1$
	}
	
	/**
	 * Checks to see that if a contributor supplies a bad tab id as a placement id that the tab will be added
	 * to the end of the group
	 * @throws CoreException
	 */
	public void testContributedTabPlacementTabNotFound() throws CoreException {
		ILaunchConfigurationTabGroup javagroup = getJavaLaunchGroup();
		assertNotNull("java tab group cannot be null", javagroup); //$NON-NLS-1$
		javagroup.createTabs(getLaunchConfigurationDialog(IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP), ILaunchManager.DEBUG_MODE);
		ILaunchConfigurationTab[] tabs = javagroup.getTabs();
		assertTrue("Alternate tab should be the last tab in the group", tabs[tabs.length-1].getClass().equals(JavaAlernateModeTab.class)); //$NON-NLS-1$
	}
	
	/**
	 * Checks to see that the two tabs following the arguments tab are TestTab2, and TestTab3. They can be in either order depending 
	 * on when they were loaded, so this test only checks that they are the next two following tabs not their order
	 * @throws CoreException
	 */
	public void testContributedTabsPlacement() throws CoreException {
		ILaunchConfigurationTabGroup javagroup = getJavaLaunchGroup();
		assertNotNull("java tab group cannot be null", javagroup); //$NON-NLS-1$
		javagroup.createTabs(getLaunchConfigurationDialog(IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP), ILaunchManager.DEBUG_MODE);
		ILaunchConfigurationTab[] tabs = javagroup.getTabs();
		HashSet<Class<? extends ILaunchConfigurationTab>> tabset = new HashSet<Class<? extends ILaunchConfigurationTab>>();
		tabset.add(tabs[3].getClass());
		tabset.add(tabs[4].getClass());
		HashSet<Class<? extends ILaunchConfigurationTab>> contribs = new HashSet<Class<? extends ILaunchConfigurationTab>>();
		contribs.add(ContributedTestTab2.class);
		contribs.add(ContributedTestTab3.class);
		assertTrue("the tab set must only contain test tab 2 and test tab 3", tabset.containsAll(contribs)); //$NON-NLS-1$
	}
}
