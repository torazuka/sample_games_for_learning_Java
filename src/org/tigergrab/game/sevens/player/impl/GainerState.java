package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.player.Player;

/**
 * 勝利条件を満たしたプレイヤーを管理します．
 * 
 */
public class GainerState extends DefaultState {

	public GainerState() {
		players = new ArrayList<>();
	}

	@Override
	public List<Player> getRanking() {
		if (players != null && players.size() != 0) {
			return players;
		}
		return null;
	}

}
