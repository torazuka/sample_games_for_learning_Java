package org.tigergrab.game.sevens.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.tigergrab.game.sevens.impl.DefaultView;
import org.tigergrab.game.sevens.player.impl.AIPlayer;
import org.tigergrab.game.sevens.player.impl.HumanPlayer;

public class PlayerTest {
	@Test
	public void testGetScreenName() throws Exception {
		Player humanPlayer = new HumanPlayer(new DefaultView(), 0);
		assertEquals("あなた", humanPlayer.getScreenName());

		Player defaultPlayer = new AIPlayer(new DefaultView(), 1);
		assertEquals("ID 1 ", defaultPlayer.getScreenName());
	}
}
