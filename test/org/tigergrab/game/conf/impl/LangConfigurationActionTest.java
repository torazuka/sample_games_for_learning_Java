package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LangConfigurationActionTest {
	LangConfiguration action;

	@Test
	public void testExecute() throws Exception {
		action = new LangConfigurationActionMockForExecute();
		action.execute();
	}

	@Test
	public void testIsValidConfiguration() throws Exception {
		action = new LangConfiguration();
		assertEquals("nullならfalse", false, action.isValidConfiguration(null));
		assertEquals("空文字ならfalse", false, action.isValidConfiguration(""));
		assertEquals("jaならtrue", true, action.isValidConfiguration("ja"));
		assertEquals("enならtrue", true, action.isValidConfiguration("en"));
	}

	@Test
	public void testConvertToLang() throws Exception {
		action = new LangConfiguration();
		assertEquals("1ならja", "ja", action.convertToLang("1"));
		assertEquals("2ならen", "en", action.convertToLang("2"));
		assertEquals("1でも2でもない値（A）なら空文字", "", action.convertToLang("A"));
	}
}
