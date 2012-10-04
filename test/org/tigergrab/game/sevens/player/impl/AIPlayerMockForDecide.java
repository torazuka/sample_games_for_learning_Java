package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;

public class AIPlayerMockForDecide extends AIPlayer {

	public AIPlayerMockForDecide(int i) {
		super(i);
	}

	@Override
	public List<Card> getLeadableCards(Space space) {
		List<Card> result = new ArrayList<>();
		result.add(new Card(Suite.Spade, 1));
		result.add(new Card(Suite.Dia, 1));
		return result;
	}

	@Override
	public void leadCard(Space space, Card card) {
		assertEquals("getLeadableCards戻り値のリストの1枚目。", new Card(Suite.Spade, 1),
				card);
	}

	@Override
	public void pass(Status status) {
		fail("呼び出されたら失敗。");
	}

}
