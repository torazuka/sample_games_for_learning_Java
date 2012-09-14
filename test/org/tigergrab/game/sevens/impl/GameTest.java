package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.Player;

public class GameTest {

	@Test
	public void testGetNumPlayer() throws Exception {
		Game game0 = new Game() {
			public String read() {
				return null;
			}
		};
		int numPlayers = game0.getNumPlayers();
		assertEquals("入力がない場合は、デフォルトのプレイヤー人数。", game0.getDefaultPlayerNum(),
				numPlayers);

		Game game1 = new Game() {
			public String read() {
				return "3";
			}
		};
		numPlayers = game1.getNumPlayers();
		assertEquals("「3」が読み込まれた場合は、3人プレイ。", 3, numPlayers);
	}

	@Test
	public void testGetFirstPlayer() throws Exception {
		Game game = new Game();
		game.status.createPlayers(2);

		Player player0 = new AIPlayer(0);
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Dia, 7));
		player0.setHand(list0);
		game.status.setLivePlayer(player0);

		Player player1 = new AIPlayer(1);
		List<Card> list1 = new ArrayList<>();
		list1.add(new Card(Suite.Spade, 7));
		player1.setHand(list1);
		game.status.setLivePlayer(player1);

		assertEquals("ユーザ0の手札にD-07があるとき、最初の手番はユーザ0。", 0, game.getFirstPlayer()
				.getId());
	}

	@Test
	public void testLeadSevens() throws Exception {
		Game game = new Game();
		game.status.createPlayers(2);
		game.status.initHands();

		// これが7を場に出す動作になる。
		game.getFirstPlayer();

		List<Card> spadeList = game.space.getCardsBySuite(Suite.Spade);
		assertEquals(true, spadeList.get(5) == null);
		assertEquals(true, spadeList.get(6) != null);
		assertEquals(true, spadeList.get(7) == null);

		List<Card> heartList = game.space.getCardsBySuite(Suite.Heart);
		assertEquals(true, heartList.get(5) == null);
		assertEquals(true, heartList.get(6) != null);
		assertEquals(true, heartList.get(7) == null);

		List<Card> diaList = game.space.getCardsBySuite(Suite.Heart);
		assertEquals(true, diaList.get(5) == null);
		assertEquals(true, diaList.get(6) != null);
		assertEquals(true, diaList.get(7) == null);

		List<Card> clubList = game.space.getCardsBySuite(Suite.Heart);
		assertEquals(true, clubList.get(5) == null);
		assertEquals(true, clubList.get(6) != null);
		assertEquals(true, clubList.get(7) == null);

		Card spadeSeven = new Card(Suite.Spade, 7);
		Card heartSeven = new Card(Suite.Heart, 7);
		Card diaSeven = new Card(Suite.Dia, 7);
		Card clubSeven = new Card(Suite.Club, 7);
		List<Player> livePlayers = game.status.getPlayers();
		for (Player player : livePlayers) {
			List<Card> list = player.getHand();
			assertEquals("手札にSpadeの7は残っていない。", -1, list.indexOf(spadeSeven));
			assertEquals("手札にHeartの7は残っていない。", -1, list.indexOf(heartSeven));
			assertEquals("手札にDiaの7は残っていない。", -1, list.indexOf(diaSeven));
			assertEquals("手札にClubの7は残っていない。", -1, list.indexOf(clubSeven));
		}
	}

	@Test
	public void testGetNextPlayer() throws Exception {
		Game game = new Game();
		game.status.createPlayers(3);
		List<Player> livePlayers = game.status.getPlayers();

		assertEquals("id==0のユーザの次のユーザは、id==1。", 1,
				game.getNextPlayer(livePlayers.get(0)).getId());
		assertEquals("id==2のユーザの次のユーザは、id==0。", 0,
				game.getNextPlayer(livePlayers.get(2)).getId());
	}

}
