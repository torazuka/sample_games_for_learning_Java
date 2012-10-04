package org.tigergrab.game.conf.impl;

/**
 * 利用可能な言語．
 */
public enum Lang {
	ja("1"), en("2");

	private String key;

	private Lang(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
