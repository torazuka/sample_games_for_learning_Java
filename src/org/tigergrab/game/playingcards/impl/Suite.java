package org.tigergrab.game.playingcards.impl;

/**
 * トランプの4つのSuite（Spade, Heart, Dia, Club）を表わす．
 */
public enum Suite {

	Spade("S", 0), Heart("H", 1), Dia("D", 2), Club("C", 3);

	private String name;
	private String shortName;
	private int level;

	private Suite(String sn, int i) {
		shortName = sn;
		level = i;
	}

	private Suite(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * 昇順で、Spade < Heart < Dia < Club とする
	 */
	public int getLevel() {
		return this.level;
	}

	public String toShortString() {
		return this.shortName;
	}

}