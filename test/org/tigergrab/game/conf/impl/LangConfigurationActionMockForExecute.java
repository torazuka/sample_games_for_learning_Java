package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import org.tigergrab.game.conf.impl.ResourceFactory.PKG;

public class LangConfigurationActionMockForExecute extends
		LangConfigurationAction {

	public LangConfigurationActionMockForExecute(PKG pkg) {
		super();
	}

	@Override
	protected boolean setConfigrationByInput() {
		assertEquals(0, 0);
		return true;
	}
}
