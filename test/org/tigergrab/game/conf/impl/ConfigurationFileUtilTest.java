package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Test;

public class ConfigurationFileUtilTest {
	@Test
	public void testStoreFile() throws Exception {
		ConfigurationFileUtil util = new ConfigurationFileUtilMock();
		Properties properties = new Properties();
		util.storeFile(properties, "ja2");

		util.readPropertyFile(properties, util.getConfigFilePath());
		assertEquals("書き込んだ値が「ja2」。", "ja2", properties.get("lang"));
	}
}
