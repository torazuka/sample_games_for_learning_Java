package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.PlayerState;

/**
 * 勝利条件を満たしたプレイヤーを管理します．
 * 
 */
public class GainerState implements PlayerState {

	protected List<Player> gainers;

	public GainerState() {
		gainers = new ArrayList<>();
	}

	@Override
	public void add(Player player) {
		gainers.add(player);
	}

	@Override
	public List<Player> getRanking() {
		if (gainers != null && gainers.size() != 0) {
			return gainers;
		}
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		return gainers;
	}

	@Override
	public boolean remove(Player player) {
		return gainers.remove(player);
	}

	@Override
	public boolean isGameOver() {
		return false;
	}

	@Override
	public int getNum() {
		return gainers.size();
	}

}
