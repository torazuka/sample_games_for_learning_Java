package org.tigergrab.game.sevens.impl;

import java.util.EnumSet;

/**
 * トランプのカード1枚ずつを表わす．このクラスで山札、場札、手札の区別はしない．
 */
public class Card implements Comparable<Card> {
	Suite suite;
	Rank rank;
	static final String EMPTY = "      ";

	static int CARD_MAX = 52;

	public Card(Suite s, int i) {
		boolean isSuite = false;
		EnumSet<Suite> allSuites = EnumSet.allOf(Suite.class);
		for (Suite suite : allSuites) {
			if (s == suite) {
				isSuite = true;
			}
		}

		if (isSuite) {
			suite = s;
			rank = new Rank(i);
		} else {
			throw new IllegalArgumentException("[" + s + "-"
					+ String.valueOf(i) + "] は、トランプに存在しないカードです。");
		}
	}

	@Override
	public String toString() {
		return "[" + suite.name() + "-" + rank.toLongString() + "]";
	}

	public String toShortString() {
		return "[" + suite.toShortString() + "-" + rank.toShortString() + "]";
	}

	public String toMiniString() {
		return suite.toShortString() + "-" + rank.toShortString();
	}

	@Override
	public int compareTo(Card c) {
		int result = this.suite.compareTo(c.suite);
		// Suiteが同じ場合だけ、ランクを比較する
		if (result == 0) {
			result = rank.compareTo(c.rank);
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suite == null) ? 0 : suite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		if (obj instanceof Card) {
			return this.compareTo((Card) obj) == 0;
		}
		return true;
	}

}
