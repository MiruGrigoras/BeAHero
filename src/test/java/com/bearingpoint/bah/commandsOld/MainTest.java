package com.bearingpoint.bah.commandsOld;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MainTest {

	@Test
	public void mainPassedTest() {
		assertEquals(1, 1);
	}

	@Test
	public void mainFailedTest() {
		assertEquals(1, 2);
	}

	@Test
	public void mainExceptionTest() {
		assertEquals("nonNullString", null);
	}
}