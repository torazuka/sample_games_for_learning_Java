package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.PlayerState;

public class DefaultState implements PlayerState {

	List<Player> players;

	public DefaultState() {
		players = new ArrayList<>();
	}

	@Override
	public void add(Player player) {
		players.add(player);
	}

	@Override
	public List<Player> getRanking() {
		if (players != null && 0 < players.size()) {
			return players;
		}
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean remove(Player player) {
		return players.remove(player);
	}

	@Override
	public boolean isGameOver() {
		return 1 < players.size() ? false : true;
	}
}
