package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

public class LangConfigurationMockForExecute extends
		LangConfiguration {

	public LangConfigurationMockForExecute() {
		super();
	}

	@Override
	protected boolean setConfigrationByInput() {
		assertEquals(0, 0);
		return true;
	}
}
