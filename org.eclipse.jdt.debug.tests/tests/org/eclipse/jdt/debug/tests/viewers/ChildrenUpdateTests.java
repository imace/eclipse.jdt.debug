/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.debug.tests.viewers;

import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.viewers.model.ChildrenUpdate;
import org.eclipse.debug.internal.ui.viewers.model.ILabelUpdateListener;
import org.eclipse.debug.internal.ui.viewers.model.ITreeModelContentProviderTarget;
import org.eclipse.debug.internal.ui.viewers.model.TreeModelContentProvider;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelChangedListener;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IModelDelta;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IPresentationContext;
import org.eclipse.debug.internal.ui.viewers.model.provisional.IViewerUpdateListener;
import org.eclipse.debug.internal.ui.viewers.model.provisional.ModelDelta;
import org.eclipse.jdt.debug.tests.AbstractDebugTest;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.widgets.Display;

/**
 * Tests coalescing of children update requests.
 * 
 * @since 3.3
 */
public class ChildrenUpdateTests extends AbstractDebugTest {
	
	class BogusModelContentProvider extends TreeModelContentProvider {
		
		/* (non-Javadoc)
		 * @see org.eclipse.debug.internal.ui.viewers.model.ModelContentProvider#getViewer()
		 */
		protected ITreeModelContentProviderTarget getViewer() {
			return new ITreeModelContentProviderTarget(){
			
				public void setSelection(ISelection selection) {
				}
			
				public void removeSelectionChangedListener(ISelectionChangedListener listener) {
				}
			
				public void addSelectionChangedListener(ISelectionChangedListener listener) {
				}
			
				public void updateViewer(IModelDelta delta) {
				}
			
				public void setSelection(ISelection selection, boolean reveal) {
				}
			
				public void setInput(Object object) {
				}
			
				public void setAutoExpandLevel(int level) {
				}
			
				public void saveElementState(TreePath path, ModelDelta delta) {					
				}
			
				public void removeViewerUpdateListener(IViewerUpdateListener listener) {
				}
			
				public void removeModelChangedListener(IModelChangedListener listener) {
				}
			
				public void removeLabelUpdateListener(ILabelUpdateListener listener) {
				}
			
				public ISelection getSelection() {
					return null;
				}
			
				public IPresentationContext getPresentationContext() {
					return null;
				}
			
				public Object getInput() {
					return null;
				}
			
				public ViewerLabel getElementLabel(TreePath path, String columnId) {
					return null;
				}
			
				public Display getDisplay() {
					return DebugUIPlugin.getStandardDisplay();
				}
			
				public int getAutoExpandLevel() {
					return 0;
				}
			
				public void addViewerUpdateListener(IViewerUpdateListener listener) {
				}
			
				public void addModelChangedListener(IModelChangedListener listener) {
				}
			
				public void addLabelUpdateListener(ILabelUpdateListener listener) {
				}
			
				public void update(Object element) {
				}
			
				public void setHasChildren(Object elementOrTreePath, boolean hasChildren) {
				}
			
				public void setExpandedState(Object elementOrTreePath, boolean expanded) {
				}
			
				public void setChildCount(Object elementOrTreePath, int count) {
				}
			
				public void reveal(TreePath path, int index) {
				}
			
				public void replace(Object parentOrTreePath, int index, Object element) {
				}
			
				public void remove(Object parentOrTreePath, int index) {
				}
			
				public void remove(Object elementOrTreePath) {
				}
			
				public void refresh() {
				}
			
				public void refresh(Object element) {
				}
			
				public boolean overrideSelection(ISelection current, ISelection candidate) {
					return false;
				}
			
				public void insert(Object parentOrTreePath, Object element, int position) {
				}
			
				public TreePath getTopElementPath() {
					return null;
				}
			
				public ViewerFilter[] getFilters() {
					return null;
				}
			
				public boolean getExpandedState(Object elementOrTreePath) {
					return false;
				}
			
				public Object getChildElement(TreePath path, int index) {
					return null;
				}
			
				public int getChildCount(TreePath path) {
					return 0;
				}
			
				public int findElementIndex(TreePath parentPath, Object element) {
					return 0;
				}
			
				public void expandToLevel(Object elementOrTreePath, int level) {
				}
			
				public void autoExpand(TreePath elementPath) {
				}
			};
		}
	}
	
	/**
	 * @param name
	 */
	public ChildrenUpdateTests(String name) {
		super(name);
	}
	
	protected TreeModelContentProvider getContentProvider() {
		return new BogusModelContentProvider();
	}
	
	/**
	 * Tests coalescing of requests
	 */
	public void testCoalesce () {
		Object element = new Object();
		TreeModelContentProvider cp = getContentProvider();
		ChildrenUpdate update1 = new ChildrenUpdate(cp, element, TreePath.EMPTY, element, 1, null, null);
		ChildrenUpdate update2 = new ChildrenUpdate(cp, element, TreePath.EMPTY, element, 2, null, null);
		assertTrue("Should coalesce", update1.coalesce(update2));
		assertEquals("Wrong offset", 1, update1.getOffset());
		assertEquals("Wrong length", 2, update1.getLength());
		
		update2 = new ChildrenUpdate(cp, element, TreePath.EMPTY, element, 3, null, null);
		assertTrue("Should coalesce", update1.coalesce(update2));
		assertEquals("Wrong offset", 1, update1.getOffset());
		assertEquals("Wrong length", 3, update1.getLength());
		
		update2 = new ChildrenUpdate(cp, element, TreePath.EMPTY, element, 2, null, null);
		assertTrue("Should coalesce", update1.coalesce(update2));
		assertEquals("Wrong offset", 1, update1.getOffset());
		assertEquals("Wrong length", 3, update1.getLength());		
		
		update2 = new ChildrenUpdate(cp, element, TreePath.EMPTY, element, 5, null, null);
		assertFalse("Should not coalesce", update1.coalesce(update2));
		assertEquals("Wrong offset", 1, update1.getOffset());
		assertEquals("Wrong length", 3, update1.getLength());
	}
}
