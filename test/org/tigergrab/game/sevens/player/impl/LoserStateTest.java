package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.player.Player;

public class LoserStateTest {
	LoserState loser;

	@Test
	public void testGetRanking() throws Exception {
		loser = new LoserState();
		setThreePlayers();

		List<Player> ranking = loser.getRanking();
		assertEquals("3人分の順位が取得できる。", 3, ranking.size());

		Player resultPlayer0 = ranking.get(0);
		assertEquals("LIFOになる。最初はid2のプレイヤー。", 2, resultPlayer0.getId());
		Player resultPlayer2 = ranking.get(2);
		assertEquals("LIFOになる。最後はid0のプレイヤー。", 0, resultPlayer2.getId());
	}

	protected void setThreePlayers() {
		Player player0 = new MockPlayer(0);
		Player player1 = new MockPlayer(1);
		Player player2 = new MockPlayer(2);
		loser.add(player0);
		loser.add(player1);
		loser.add(player2);
	}

}
