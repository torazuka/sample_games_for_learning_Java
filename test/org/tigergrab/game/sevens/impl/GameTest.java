package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.impl.AIPlayer;

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

		Player player0 = new AIPlayer(new DefaultView(), 0);
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Dia, 7));
		player0.setHand(list0);
		game.status.setLivePlayer(player0);

		Player player1 = new AIPlayer(new DefaultView(), 1);
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

		game.leadSevens();

		List<Card> spadeList = game.space.getCardsBySuite(Suite.Spade);
		assertEquals("[S-06]は場にない。", false,
				spadeList.contains(new Card(Suite.Spade, 6)));
		assertEquals("[S-07]は場にある。", true,
				spadeList.contains(new Card(Suite.Spade, 7)));
		assertEquals("[S-08]は場にない。", false,
				spadeList.contains(new Card(Suite.Spade, 8)));

		List<Card> heartList = game.space.getCardsBySuite(Suite.Heart);
		assertEquals("[H-06]は場にない。", false,
				heartList.contains(new Card(Suite.Heart, 6)));
		assertEquals("[H-07]は場にある。", true,
				heartList.contains(new Card(Suite.Heart, 7)));
		assertEquals("[H-08]は場にない。", false,
				heartList.contains(new Card(Suite.Heart, 8)));

		List<Card> diaList = game.space.getCardsBySuite(Suite.Dia);
		assertEquals("[D-06]は場にない。", false,
				diaList.contains(new Card(Suite.Dia, 6)));
		assertEquals("[D-07]は場にある。", true,
				diaList.contains(new Card(Suite.Dia, 7)));
		assertEquals("[D-08]は場にない。", false,
				diaList.contains(new Card(Suite.Dia, 8)));

		List<Card> clubList = game.space.getCardsBySuite(Suite.Club);
		assertEquals("[C-06]は場にない。", false,
				clubList.contains(new Card(Suite.Club, 6)));
		assertEquals("[C-07]は場にない。", true,
				clubList.contains(new Card(Suite.Club, 7)));
		assertEquals("[C-08]は場にない。", false,
				clubList.contains(new Card(Suite.Club, 8)));

		Card spadeSeven = new Card(Suite.Spade, 7);
		Card heartSeven = new Card(Suite.Heart, 7);
		Card diaSeven = new Card(Suite.Dia, 7);
		Card clubSeven = new Card(Suite.Club, 7);
		List<Player> livePlayers = game.status.getPlayers();
		for (Player player : livePlayers) {
			assertEquals("手札にSpadeの7は残っていない。", false,
					player.hasCard(spadeSeven));
			assertEquals("手札にHeartの7は残っていない。", false,
					player.hasCard(heartSeven));
			assertEquals("手札にDiaの7は残っていない。", false, player.hasCard(diaSeven));
			assertEquals("手札にClubの7は残っていない。", false, player.hasCard(clubSeven));
		}
	}
}
