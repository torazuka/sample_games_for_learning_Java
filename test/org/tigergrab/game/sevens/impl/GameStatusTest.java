package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.impl.AIPlayer;
import org.tigergrab.game.sevens.player.impl.HumanPlayer;

public class GameStatusTest {

	GameStatus status;

	@Test
	public void testCreatePlayer() throws Exception {
		status = new GameStatus(new DefaultView());
		status.createPlayers(3);

		assertEquals("生成されたユーザリストの要素は3個。", 3, status.getPlayersNum());

		List<Player> livePlayers = status.getPlayers();
		Player player0 = livePlayers.get(0);
		assertEquals("1人目のプレイヤーは、ユーザ。", true, player0 instanceof HumanPlayer);
		Player player1 = livePlayers.get(1);
		assertEquals("2人目のプレイヤーは、コンピュータ。", true, player1 instanceof AIPlayer);
		Player player2 = livePlayers.get(2);
		assertEquals("3人目のプレイヤーも、コンピュータ。", true, player2 instanceof AIPlayer);
	}

	@Test
	public void testInitHands() throws Exception {
		status = new GameStatus(new DefaultView());
		status.createPlayers(2);

		status.initHands();
		assertEquals("手札リストは2つ。", 2, status.getPlayersNum());

		List<Player> livePlayers = status.getPlayers();
		for (Player p : livePlayers) {
			assertEquals("個々の手札は26枚。", 26, p.getRestHand());
		}
	}

	@Test
	public void testIsGameOver() throws Exception {
		status = new GameStatus(new DefaultView());

		Player player0 = new HumanPlayer(new DefaultView(), 0);
		Player player1 = new AIPlayer(new DefaultView(), 1);
		status.setLivePlayer(player0);
		status.setLivePlayer(player1);
		assertEquals("生存プレイヤーリストに2人いるとき、ゲームはまだ終了しない。", false,
				status.isGameOver());

		status.moveToLoser(player0);
		assertEquals("生存プレイヤーリストに1人しかいないとき、ゲーム終了する。", true, status.isGameOver());
	}

	@Test
	public void testGetPlayerRank() throws Exception {
		status = new GameStatus(new DefaultView());
		status.createPlayers(3);

		status.moveToLoser(new HumanPlayer(new DefaultView(), 0));
		status.moveToLoser(new AIPlayer(new DefaultView(), 2));

		List<Player> playerRank = status.getPlayersRank();
		assertEquals(3, playerRank.size());
		assertEquals("ID 1 ", playerRank.get(0).getScreenName());
		assertEquals("ID 2 ", playerRank.get(1).getScreenName());
		assertEquals("あなた", playerRank.get(2).getScreenName());
	}

	@Test
	public void testGetNextPlayer() throws Exception {
		status = new GameStatus(new DefaultView());
		status.createPlayers(3);
		List<Player> livePlayers = status.getPlayers();

		assertEquals("id==0のユーザの次のユーザは、id==1。", 1,
				status.getNextPlayer(livePlayers.get(0)).getId());
		assertEquals("id==2のユーザの次のユーザは、id==0。", 0,
				status.getNextPlayer(livePlayers.get(2)).getId());

		// TODO: プレイヤーが脱落していたり勝利していたりする場合のテストケースを追加。
	}
}
