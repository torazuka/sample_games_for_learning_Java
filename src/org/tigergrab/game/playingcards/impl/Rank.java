package org.tigergrab.game.playingcards.impl;

public class Rank implements Comparable<Rank> {

	protected final int rank;

	public static int MAX = 13;
	public static int MIN = 1;

	public Rank(int num) {
		if (isRank(num) == false) {
			throw new IllegalArgumentException("[" + String.valueOf(num)
					+ "] は、トランプのカードに存在しないランクです。");
		}
		rank = num;
	}

	public int getRank() {
		return rank;
	}

	public boolean isRank(int num) {
		if (MIN <= num && num <= MAX) {
			return true;
		}
		return false;
	}

	public Rank getNextBig() {
		if (rank < MAX) {
			return new Rank(rank + 1);
		}
		return null;
	}

	public Rank getNextSmall() {
		if (MIN < rank) {
			return new Rank(rank - 1);
		}
		return null;
	}

	public String toShortString() {
		String result = String.valueOf(rank);
		if (rank < 10) {
			result = "0" + result;
		}
		return result;
	}

	public String toLongString() {
		if (rank == 1) {
			return "Ace";
		} else if (rank == 11) {
			return "Jack";
		} else if (rank == 12) {
			return "Queen";
		} else if (rank == 13) {
			return "King";
		} else {
			return String.valueOf(rank);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Rank that = (Rank) obj;
		if (rank != that.rank) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rank;
		return result;
	}

	@Override
	public int compareTo(Rank that) {
		return Integer.compare(rank, that.getRank());
	}

}
