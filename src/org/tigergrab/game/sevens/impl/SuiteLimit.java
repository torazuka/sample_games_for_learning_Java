package org.tigergrab.game.sevens.impl;

import org.tigergrab.game.playingcards.impl.Card;

public class SuiteLimit {
	Card maxCard;
	Card minCard;

	public SuiteLimit(Card min, Card max) {
		minCard = min;
		maxCard = max;
	}

	public Card getMax() {
		return maxCard;
	}

	public Card getMin() {
		return minCard;
	}
}
