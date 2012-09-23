package org.tigergrab.game.playingcards.impl;


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
