package org.tigergrab.game.playingcards.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CardFactory {
	public CardFactory() {

	}

	/**
	 * 引数のSuiteのカード一式を作成して返す．
	 * 
	 * @param suite
	 * @return
	 */
	public List<Card> getCardList(Suite suite) {
		List<Card> result = new ArrayList<>();
		for (int i = Rank.MIN; i <= Rank.MAX; i++) {
			result.add(new Card(suite, i));
		}
		return result;
	}

	/**
	 * トランプ一式を作成してListで返す．
	 * 
	 * @return
	 */
	public List<Card> getAllCard() {
		List<Card> result = new ArrayList<>();
		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite s : allSuite) {
			result.addAll(this.getCardList(s));
		}
		return result;
	}
}
