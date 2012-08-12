package org.tigergrab.game.sevens.impl;

public class SuiteLimit {
	int max;
	int min;

	public SuiteLimit(int mi, int ma) {
		min = mi;
		max = ma;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}
}
