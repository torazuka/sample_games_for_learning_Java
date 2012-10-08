package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

public class LangConfigurationActionMockForExecute extends
		LangConfiguration {

	public LangConfigurationActionMockForExecute() {
		super();
	}

	@Override
	protected boolean setConfigrationByInput() {
		assertEquals(0, 0);
		return true;
	}
}
