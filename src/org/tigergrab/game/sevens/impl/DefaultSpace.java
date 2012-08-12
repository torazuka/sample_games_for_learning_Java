package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.sevens.Space;

public class DefaultSpace implements Space {

	private final static Logger logger = LoggerFactory
			.getLogger(DefaultSpace.class);

	Map<Suite, List<Card>> space;

	protected View view;

	public DefaultSpace() {
		view = new View();
		space = new HashMap<Suite, List<Card>>();

		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite s : allSuite) {
			List<Card> cardList = new ArrayList<>();
			for (int i = 0; i < Card.RANK_MAX; i++) {
				cardList.add(null);
			}
			space.put(s, cardList);
		}
	}

	@Override
	public void viewCurrentSpace() {
		view.putSpace(this);
	}

	@Override
	public void putCard(Card card) {
		if (searchCard(card) == null) {
			List<Card> list = space.get(card.suite);
			list.set(card.rank - 1, card);
			space.put(card.suite, list);
		} else {
			logger.warn("{} は、すでに場に出ている。", card.toShortString());
		}
	}

	/**
	 * 引数で与えたカードが場に出ていれば返す。出ていなければnullを返す。
	 */
	@Override
	public Card searchCard(Card card) {
		List<Card> cardList = this.getCardsBySuite(card.suite);
		if (cardList != null) {
			Card target = cardList.get(card.rank - 1);
			if (target != null) {
				return target;
			}
		}
		return null;
	}

	@Override
	public List<Card> getCardsBySuite(Suite suite) {
		return space.get(suite);
	}

	@Override
	public boolean canLead(Card card) {
		boolean result = false;

		Card right = null;
		Card left = null;
		if (card.rank != Card.RANK_MAX) {
			right = searchCard(new Card(card.suite, card.rank + 1));
		}
		if (card.rank != Card.RANK_MIN) {
			left = searchCard(new Card(card.suite, card.rank - 1));
		}
		if (right != null || left != null) {
			result = true;
		}
		return result;
	}
}
