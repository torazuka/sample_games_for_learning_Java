package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.player.Player;

public class DefaultStateTest {
	DefaultState players;

	@Test
	public void testAdd() throws Exception {
		players = new DefaultState();
		Player player = new MockPlayer(1);
		players.add(player);

		assertEquals("1人追加", 1, players.getNum());
	}

	@Test
	public void testGetRanking() throws Exception {
		players = new GainerState();
		assertNull("リストに0人の場合、nullが返る。", players.getRanking());

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

	@Test
	public void testIsGameOver() throws Exception {
		players = new DefaultState();
		setThreePlayers();

		assertEquals("3人いる状態ではゲーム続行。", false, players.isGameOver());
		Player player1 = new MockPlayer(1);
		players.remove(player1);
		assertEquals("2人いる状態でもゲーム続行。", false, players.isGameOver());
		Player player2 = new MockPlayer(2);
		players.remove(player2);
		assertEquals("1人の状態になるとゲーム終了。", true, players.isGameOver());
	}

	@Test
	public void testRemove() throws Exception {
		players = new DefaultState();
		setThreePlayers();

		assertEquals("最初は3人。", 3, players.getNum());
		Player player1 = new MockPlayer(1);
		players.remove(player1);
		assertEquals("1人削除した後は2人。", 2, players.getNum());
	}
}
