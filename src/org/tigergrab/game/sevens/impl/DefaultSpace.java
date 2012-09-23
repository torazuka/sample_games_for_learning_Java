package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;

public class DefaultSpace implements Space {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultSpace.class);

	protected List<Card> space;

	protected View view;

	public DefaultSpace() {
		view = new View();
		space = new ArrayList<>();
	}

	@Override
	public void viewCurrentSpace() {
		view.putSpace(this);
	}

	@Override
	public void putCard(Card card) {
		if (searchCard(card) != null) {
			logger.warn("{} は、すでに場に出ている。", card.toShortString());
			return;
		}
		space.add(card);
	}

	/**
	 * 引数で与えたカードが場に出ていれば返す。出ていなければnullを返す。
	 */
	@Override
	public Card searchCard(Card card) {
		if (space.contains(card)) {
			return card;
		}
		return null;
	}

	@Override
	public List<Card> getCardsBySuite(Suite suite) {
		if (space != null && 0 < space.size()) {
			List<Card> result = new ArrayList<>();
			for (Card card : space) {
				if (card.filter(suite)) {
					result.add(card);
				}
			}
			return result;
		}
		return null;
	}

	@Override
	public boolean canLead(Card card) {
		if (space.contains(card)) {
			return false;
		}

		Card right = card.getNextBig();
		Card left = card.getNextSmall();
		if ((right != null && space.contains(right))
				|| (left != null && space.contains(left))) {
			return true;
		}
		return false;
	}

}
