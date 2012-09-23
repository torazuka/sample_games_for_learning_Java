package org.tigergrab.game.playingcards.impl;

import java.util.ArrayList;
import java.util.List;

public class CardFactory {
	public CardFactory() {

	}

	public List<Card> getCardList(Suite suite) {
		List<Card> result = new ArrayList<>();
		for (int i = Rank.MIN; i <= Rank.MAX; i++) {
			result.add(new Card(suite, i));
		}
		return result;
	}
}
