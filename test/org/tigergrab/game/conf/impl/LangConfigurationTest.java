package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LangConfigurationTest {
	LangConfiguration langConf;

	@Test
	public void testExecute() throws Exception {
		langConf = new LangConfigurationMockForExecute();
		langConf.execute();
	}

	@Test
	public void testIsValidConfiguration() throws Exception {
		langConf = new LangConfiguration();
		assertEquals("nullならfalse", false, langConf.isValidConfiguration(null));
		assertEquals("空文字ならfalse", false, langConf.isValidConfiguration(""));
		assertEquals("jaならtrue", true, langConf.isValidConfiguration("ja"));
		assertEquals("enならtrue", true, langConf.isValidConfiguration("en"));
	}

	@Test
	public void testConvertToLang() throws Exception {
		langConf = new LangConfiguration();
		assertEquals("1ならja", "ja", langConf.convertToLang("1"));
		assertEquals("2ならen", "en", langConf.convertToLang("2"));
		assertEquals("1でも2でもない値（A）なら空文字", "", langConf.convertToLang("A"));
	}

	@Test
	public void testReadConfigurationFromInput() throws Exception {

	}
}
