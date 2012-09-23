package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.tigergrab.game.sevens.PlayerState;
import org.tigergrab.game.sevens.player.Player;

public class LoserState implements PlayerState {

	protected List<Player> losers;

	public LoserState() {
		losers = new ArrayList<>();
	}

	@Override
	public void add(Player player) {
		losers.add(player);
	}

	@Override
	public List<Player> getRanking() {
		if (losers != null && 0 < losers.size()) {
			Collections.reverse(losers);
			List<Player> result = new ArrayList<>();
			ListIterator<Player> iterator = losers.listIterator();
			for (; iterator.hasNext();) {
				result.add(iterator.next());
			}
			Collections.reverse(losers);
			return result;
		}
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		return losers;
	}

	@Override
	public boolean remove(Player player) {
		return losers.remove(player);
	}

	@Override
	public boolean isGameOver() {
		return false;
	}

}
