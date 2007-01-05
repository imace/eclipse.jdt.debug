/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.debug.tests.targets;

/**
 * ClassOne 
 */
public class ClassOne {
	
	public void method1() {
		method2();
	}
	
	public void method2() {
		method3();
	}
	
	public void method3() {
		System.out.println("ClassOne, method3");
	}

}