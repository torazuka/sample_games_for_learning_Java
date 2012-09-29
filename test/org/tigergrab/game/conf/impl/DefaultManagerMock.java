package org.tigergrab.game.conf.impl;

import java.io.File;

import org.tigergrab.game.conf.ConfigurationManager;

public class DefaultManagerMock extends DefaultManager implements
		ConfigurationManager {
	@Override
	public String getConfigFileName() {
		String parent = new File(".").getAbsoluteFile().getParent();
		return parent + File.separator + "test.ini";
	}
}
