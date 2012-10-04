package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

public class LangConfigurationActionMockForExecute extends
		LangConfigurationAction {

	public LangConfigurationActionMockForExecute() {
		super();
	}

	@Override
	protected boolean setConfigrationByInput() {
		assertEquals(0, 0);
		return true;
	}
}
