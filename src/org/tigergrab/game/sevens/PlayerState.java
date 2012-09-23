package org.tigergrab.game.sevens;

import java.util.List;

import org.tigergrab.game.sevens.player.Player;

public interface PlayerState {
	void add(Player player);

	List<Player> getPlayers();

	List<Player> getRanking();

	boolean remove(Player player);

	boolean isGameOver();
}
