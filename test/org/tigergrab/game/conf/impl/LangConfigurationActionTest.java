package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;

public class LangConfigurationActionTest {
	LangConfigurationAction action;

	@Ignore("assert考え中")
	@Test
	public void testConstructor() throws Exception {

	}

	@Test
	public void testExecute() throws Exception {
		action = new LangConfigurationActionMockForExecute(PKG.ROOT);
		action.execute();
	}

	@Test
	public void testIsValidConfiguration() throws Exception {
		action = new LangConfigurationActionMock();
		assertEquals("nullならfalse", false, action.isValidConfiguration(null));
		assertEquals("空文字ならfalse", false, action.isValidConfiguration(""));
		assertEquals("jaならtrue", true, action.isValidConfiguration("ja"));
		assertEquals("enならtrue", true, action.isValidConfiguration("en"));
	}

	@Test
	public void testConvertToLang() throws Exception {
		action = new LangConfigurationActionMock();
		assertEquals("1ならja", "ja", action.convertToLang("1"));
		assertEquals("2ならen", "en", action.convertToLang("2"));
		assertEquals("1でも2でもない値（A）なら0", "0", action.convertToLang("A"));
	}

	@Ignore("assert考え中")
	@Test
	public void testReadPropertyFile() throws Exception {
		action = new LangConfigurationActionMock();

	}
}
