package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Status;

public class GameStatusTest {

	@Test
	public void testCreatePlayer() throws Exception {
		Status status = new GameStatus();
		status.createPlayers(3);

		assertEquals("生成されたユーザリストの要素は3個。", 3, status.getLivePlayersNum());

		List<Player> livePlayers = status.getLivePlayers();
		Player player0 = livePlayers.get(0);
		assertEquals("1人目のプレイヤーは、ユーザ。", true, player0 instanceof HumanPlayer);
		Player player1 = livePlayers.get(1);
		assertEquals("2人目のプレイヤーは、コンピュータ。", true, player1 instanceof AIPlayer);
		Player player2 = livePlayers.get(2);
		assertEquals("3人目のプレイヤーも、コンピュータ。", true, player2 instanceof AIPlayer);
	}

	@Test
	public void testInitHands() throws Exception {
		Status status = new GameStatus();
		status.createPlayers(2);

		status.initHands();
		assertEquals("手札リストは2つ。", 2, status.getLivePlayersNum());

		List<Player> livePlayers = status.getLivePlayers();
		for (Player p : livePlayers) {
			List<Card> list = p.getHand();
			assertEquals("個々の手札は26枚。", 26, list.size());
		}
	}

	@Test
	public void testIsGameOver() throws Exception {
		Status status = new GameStatus();

		Player player0 = new HumanPlayer(0);
		Player player1 = new AIPlayer(1);
		status.setLivePlayer(player0);
		status.setLivePlayer(player1);
		assertEquals("生存プレイヤーリストに2人いるとき、ゲームはまだ終了しない。", false,
				status.isGameOver());

		status.moveToDead(player0);
		assertEquals("生存プレイヤーリストに1人しかいないとき、ゲーム終了する。", true, status.isGameOver());
	}
}
