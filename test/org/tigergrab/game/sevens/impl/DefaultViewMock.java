package org.tigergrab.game.sevens.impl;

import org.slf4j.Logger;
import org.tigergrab.game.sevens.View;

public class DefaultViewMock extends DefaultView implements View {
	private Logger logger;

	public DefaultViewMock() {
		super();
	}

	public DefaultViewMock(Logger log) {
		super();
		logger = log;
	}

}
