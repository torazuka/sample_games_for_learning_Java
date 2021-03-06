package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.impl.GameStatus;
import org.tigergrab.game.sevens.player.Player;

public class DefaultPlayerTest {
	@Test
	public void testHasCard() throws Exception {
		Player player = new AIPlayer(1);

		List<Card> hand = new ArrayList<>();
		hand.add(new Card(Suite.Spade, 7));
		player.setHand(hand);
		assertEquals("手札が[S-07]のとき、[H-07]は手札にない。", false,
				player.hasCard(new Card(Suite.Heart, 7)));

		hand.add(new Card(Suite.Heart, 7));
		player.setHand(hand);
		assertEquals("手札が[S-07][H-07]のとき、[H-07]は手札にある。", true,
				player.hasCard(new Card(Suite.Heart, 7)));
	}

	@Test
	public void testPass() throws Exception {
		Player player = new AIPlayer(1);
		Status status = mock(GameStatus.class);

		player.pass(status);
		verify(status, times(0)).moveToLoser(player);
		player.pass(status);
		verify(status, times(0)).moveToLoser(player);
		player.pass(status);
		verify(status, times(0)).moveToLoser(player);

		// 4回目のパスでリタイアになる
		player.pass(status);
		verify(status, times(1)).moveToLoser(player);
	}

	@Test
	public void testGetRanking() throws Exception {

	}
}
