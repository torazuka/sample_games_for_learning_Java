package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.PlayerState;

/**
 * 生存状態のプレイヤーを管理します．
 * 
 */
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

	public boolean remove(Player player) {
		return players.remove(player);
	}

	public boolean isGameOver() {
		return 1 < players.size() ? false : true;
	}

	@Override
	public int getNum() {
		return players.size();
	}
}
