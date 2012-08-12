package org.tigergrab.game.sevens.impl;

import java.util.EnumSet;

/**
 * トランプのカード1枚ずつを表わす．このクラスで山札、場札、手札の区別はしない．
 */
public class Card implements Comparable<Card> {
	Suite suite;
	int rank;
	static final String EMPTY = "      ";

	static int CARD_MAX = 52;
	static int RANK_MAX = 13;
	static int RANK_MIN = 1;

	public Card(Suite s, int i) {
		boolean isSuite = false;
		EnumSet<Suite> allSuites = EnumSet.allOf(Suite.class);
		for (Suite suite : allSuites) {
			if (s == suite) {
				isSuite = true;
			}
		}

		boolean isRank = false;
		if (RANK_MIN - 1 < i && i < RANK_MAX + 1) {
			isRank = true;
		}

		if (isSuite && isRank) {
			suite = s;
			rank = i;
		} else {
			throw new IllegalArgumentException("[" + s + "-"
					+ String.valueOf(i) + "] は、トランプに存在しないカードです。");
		}
	}

	@Override
	public String toString() {
		String rankStr = String.valueOf(rank);
		if (rank == 1) {
			rankStr = "Ace";
		} else if (rank == 11) {
			rankStr = "Jack";
		} else if (rank == 12) {
			rankStr = "Queen";
		} else if (rank == 13) {
			rankStr = "King";
		}
		return "[" + suite.name() + "-" + rankStr + "]";
	}

	public String toShortString() {
		String tmpRank = String.valueOf(rank);
		if (rank < 10) {
			tmpRank = "0" + tmpRank;
		}
		return "[" + suite.toShortString() + "-" + tmpRank + "]";
	}

	public String toMiniString() {
		String tmpRank = String.valueOf(rank);
		if (rank < 10) {
			tmpRank = "0" + tmpRank;
		}
		return suite.toShortString() + "-" + tmpRank;
	}

	@Override
	public int compareTo(Card c) {
		int result = this.suite.compareTo(c.suite);
		// Suiteが同じ場合だけ、ランクを比較する
		if (result == 0) {
			result = Integer.compare(this.rank, c.rank);
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		result = prime * result + ((suite == null) ? 0 : suite.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card) {
			return this.compareTo((Card) obj) == 0;
		}
		return false;
	}

}
