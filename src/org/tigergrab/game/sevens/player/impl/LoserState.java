package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.tigergrab.game.sevens.player.Player;

/**
 * 脱落したプレイヤーを管理します．
 * 
 */
public class LoserState extends DefaultState {

	public LoserState() {
		players = new ArrayList<>();
	}

	@Override
	public List<Player> getRanking() {
		if (players != null && 0 < players.size()) {
			Collections.reverse(players);
			List<Player> result = new ArrayList<>();
			ListIterator<Player> iterator = players.listIterator();
			for (; iterator.hasNext();) {
				result.add(iterator.next());
			}
			Collections.reverse(players);
			return result;
		}
		return null;
	}

}
