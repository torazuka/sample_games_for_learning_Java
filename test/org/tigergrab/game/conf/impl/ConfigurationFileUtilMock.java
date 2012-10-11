package org.tigergrab.game.conf.impl;

import java.io.File;

public class ConfigurationFileUtilMock extends ConfigurationFileUtil {
	@Override
	public String getConfigFilePath() {
		String parent = new File(".").getAbsoluteFile().getParent();
		return parent + File.separator + "test.ini";
	}
}
