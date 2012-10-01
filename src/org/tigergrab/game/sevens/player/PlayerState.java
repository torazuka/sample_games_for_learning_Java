package org.tigergrab.game.sevens.player;

import java.util.List;

public interface PlayerState {
	void add(Player player);

	List<Player> getPlayers();

	List<Player> getRanking();

	int getNum();
}
