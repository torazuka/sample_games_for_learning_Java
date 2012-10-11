package org.tigergrab.game.conf.impl;

import org.junit.Test;
import org.tigergrab.game.conf.Configuration;

public class ConfigurationControllerTest {
	@Test
	public void testExecute() throws Exception {
		ConfigurationController controller = new ConfigurationController();
		Configuration conf = new MockConfiguration();
		controller.append(conf);
		controller.execute();
	}
}
