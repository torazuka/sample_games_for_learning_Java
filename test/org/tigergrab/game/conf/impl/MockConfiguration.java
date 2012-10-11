package org.tigergrab.game.conf.impl;

import static org.junit.Assert.assertEquals;

import org.tigergrab.game.conf.Configuration;

public class MockConfiguration implements Configuration {

	@Override
	public void execute() {
		assertEquals("ここに到達すれば成功。", 1, 1);
	}

}
