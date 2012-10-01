package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.player.Player;

public class GainerStateTest {
	GainerState players;

	@Test
	public void testGetRanking() throws Exception {
		players = new GainerState();
		setThreePlayers();

		List<Player> ranking = players.getRanking();
		assertEquals("3人分の順位が取得できる。", 3, ranking.size());

		Player resultPlayer0 = ranking.get(0);
		assertEquals("FIFOになる。最初はid0のプレイヤー。", 0, resultPlayer0.getId());
		Player resultPlayer2 = ranking.get(2);
		assertEquals("FIFOになる。最後はid2のプレイヤー。", 2, resultPlayer2.getId());
	}

	protected void setThreePlayers() {
		Player player0 = new MockPlayer(0);
		Player player1 = new MockPlayer(1);
		Player player2 = new MockPlayer(2);
		players.add(player0);
		players.add(player1);
		players.add(player2);
	}

}
