package org.eclipse.debug.jdi.tests;

/**********************************************************************
Copyright (c) 2000, 2002 IBM Corp.  All rights reserved.
This file is made available under the terms of the Common Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/cpl-v10.html
**********************************************************************/

import com.sun.jdi.ReferenceType;
import com.sun.jdi.event.ExceptionEvent;

/**
 * Tests for JDI com.sun.jdi.event.ExceptionEvent.
 */
public class ExceptionEventTest extends AbstractJDITest {

	private ExceptionEvent fEvent;
	/**
	 * Creates a new test.
	 */
	public ExceptionEventTest() {
		super();
	}
	/**
	 * Init the fields that are used by this test only.
	 */
	public void localSetUp() {
		// Trigger an exception event
		fEvent =
			(ExceptionEvent) triggerAndWait(getExceptionRequest(),
				"ExceptionEvent",
				false);
	}
	/**
	 * Make sure the test leaves the VM in the same state it found it.
	 */
	public void localTearDown() {
		// The test has interrupted the VM, so let it go
		fVM.resume();

		// The test has resumed the test thread, so suspend it
		waitUntilReady();
	}
	/**
	 * Run all tests and output to standard output.
	 */
	public static void main(java.lang.String[] args) {
		new ExceptionEventTest().runSuite(args);
	}
	/**
	 * Gets the name of the test case.
	 */
	public String getName() {
		return "com.sun.jdi.event.ExceptionEvent";
	}
	/**
	 * Test JDI catchLocation().
	 */
	public void testJDICatchLocation() {
		// Uncaught exception
		assertTrue("1", fEvent.catchLocation() == null);

		// TO DO: Caught exception
	}
	/**
	 * Test JDI exception().
	 */
	public void testJDIException() {
		ReferenceType expected =
			(ReferenceType) fVM.classesByName("java.lang.Error").get(0);
		assertEquals("1", expected, fEvent.exception().referenceType());
	}
	/**
	 * Test JDI thread().
	 */
	public void testJDIThread() {
		assertEquals("1", "Test Exception Event", fEvent.thread().name());
	}
}
