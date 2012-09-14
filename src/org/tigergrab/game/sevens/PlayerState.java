package org.tigergrab.game.sevens;

import java.util.List;

public interface PlayerState {
	void add(Player player);

	List<Player> getPlayers();

	List<Player> getRanking();

	boolean remove(Player player);

	boolean isGameOver();
}
