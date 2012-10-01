package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.impl.DefaultView;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.PlayerState;

/**
 * プレイヤーを管理します．各プレイヤーは、生存、脱落、勝利のいずれかの状態に属します．
 * 
 */
public class PlayerManager {

	/** 生存プレイヤー */
	protected PlayerState players;

	/** 脱落プレイヤー */
	protected PlayerState losers;

	/** 勝利プレイヤー */
	protected PlayerState gainers;

	DefaultView view;

	public PlayerManager(DefaultView view) {
		this.view = view;

		players = new DefaultState();
		gainers = new GainerState();
		losers = new LoserState();
	}

	/**
	 * Playerを作る． 1人目はユーザであるHumanPlayer、2人目以降はAIPlayer.
	 */
	public void createPlayers(int num) {
		Player user = new HumanPlayer(0);
		players.add(user);

		for (int i = 1; i < num; i++) {
			Player p = new AIPlayer(i);
			players.add(p);
		}
	}

	public Player getLivePlayer(int id) {
		List<Player> playerList = players.getPlayers();
		if (playerList != null) {
			for (Player player : playerList) {
				if (player.getId() == id) {
					return player;
				}
			}
		}
		return null;
	}

	public int getLivePlayerNum() {
		return players.getNum();
	}

	public int getGainerNum() {
		return gainers.getNum();
	}

	public int getLoserNum() {
		return losers.getNum();
	}

	public List<Player> getPlayersRank() {
		List<Player> result = new ArrayList<>();

		List<Player> gainerRanking = gainers.getRanking();
		if (gainerRanking != null) {
			for (Player player : gainerRanking) {
				result.add(player);
			}
		}

		List<Player> playerRanking = players.getRanking();
		if (playerRanking != null) {
			for (Player player : playerRanking) {
				result.add(player);
			}
		}

		List<Player> loserRanking = losers.getRanking();
		if (loserRanking != null) {
			for (Player player : loserRanking) {
				result.add(player);
			}
		}
		return result;
	}

	public void moveToGainer(Player player) {
		if (players.remove(player)) {
			gainers.add(player);
			view.putDescription("info.gainer", player.getScreenName());
		}
		if (players.isGameOver() != false) {
			view.putDescription("info.gamecontinue");
		}
	}

	public void moveToLoser(Player player) {
		if (players.remove(player)) {
			losers.add(player);
			view.putDescription("info.loser", player.getScreenName());
		}
	}

	public Player getNextPlayer(Player currentPlayer) {
		PlayerState ps = getPlayers();
		List<Player> players = ps.getPlayers();

		int index = players.indexOf(currentPlayer);
		if (1 < getLivePlayerNum()) {
			if (index < getLivePlayerNum() - 1) {
				index++;
			} else {
				index = 0;
			}
		} else {
			index = 0;
		}
		return players.get(index);
	}

	public PlayerState getPlayers() {
		return players;
	}

	public PlayerState getGainers() {
		return gainers;
	}

	public PlayerState getLosers() {
		return losers;
	}

	public void addPlayers(Player player) {
		players.add(player);
	}
}
