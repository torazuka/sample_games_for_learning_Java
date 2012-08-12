package org.tigergrab.game.sevens;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.tigergrab.game.sevens.impl.AIPlayer;
import org.tigergrab.game.sevens.impl.HumanPlayer;

public class PlayerTest {
	@Test
	public void testGetScreenName() throws Exception {
		Player humanPlayer = new HumanPlayer(0);
		assertEquals("あなた", humanPlayer.getScreenName());

		Player defaultPlayer = new AIPlayer(1);
		assertEquals("プレイヤーID 1 さん", defaultPlayer.getScreenName());
	}
}
