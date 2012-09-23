package org.tigergrab.game.playingcards.impl;

import java.util.List;

public class SuiteLimit {
	Card maxCard;
	Card minCard;

	public SuiteLimit(Card min, Card max) {
		minCard = min;
		maxCard = max;
	}

	public SuiteLimit(List<Card> cards) {
		maxCard = null;
		minCard = null;
		if (cards != null && 0 < cards.size()) {
			for (Card card : cards) {
				if (maxCard == null && minCard == null) {
					maxCard = card;
					minCard = card;
				}
				if (card.compareTo(minCard) < 0) {
					minCard = card;
				}
				if (0 < card.compareTo(maxCard)) {
					maxCard = card;
				}
			}
		}
	}

	public Card getMax() {
		return maxCard;
	}

	public Card getMin() {
		return minCard;
	}
}
