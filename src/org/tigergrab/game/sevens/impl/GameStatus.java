package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;

public class GameStatus implements Status {

	/** ターンカウンタ */
	int turnCounter;

	/** 現在のプレイヤー */
	Player currentPlayer;

	/** 生存プレイヤー */
	List<Player> livePlayers;

	/** 脱落プレイヤー */
	List<Player> deadPlayers;

	/** 勝利プレイヤー */
	List<Player> winners;

	/** ターンの記録 */
	List<Turn> gameRecord;

	View view;

	public GameStatus() {
		turnCounter = 0;
		livePlayers = new ArrayList<>();
		deadPlayers = new ArrayList<>();
		winners = new ArrayList<>();

		gameRecord = new ArrayList<>(); // TODO どう使うか未定
		view = new View();
	}

	/**
	 * Playerのリストを作る． 1人目はユーザであるHumanPlayer、2人目以降はAIPlayer.
	 */
	@Override
	public void createPlayers(int numPlayer) {

		Player user = new HumanPlayer(0);
		setLivePlayer(user);

		for (int i = 1; i < numPlayer; i++) {
			Player p = new AIPlayer(i);
			setLivePlayer(p);
		}
	}

	/**
	 * カードをプレイヤーに均等に配る。事前条件として、プレイヤーのリストがすでに作成されていること。
	 */
	@Override
	public void initHands() {
		Deck deck = new Deck();

		// 山札をプレイヤー人数に分ける
		List<List<Card>> dealed = deck.initDeal(getLivePlayersNum());

		// 分けたカードを個々のプレイヤーと関連づける
		Iterator<Player> playersIte = getLivePlayers().iterator();
		Iterator<List<Card>> dealedIte = dealed.iterator();
		for (; playersIte.hasNext() && dealedIte.hasNext();) {
			Player player = playersIte.next();
			player.setHand(dealedIte.next());
		}
	}

	@Override
	public void setLivePlayers(List<Player> players) {
		if (players != null) {
			for (Player p : players) {
				livePlayers.add(p);
			}
		}
	}

	public void setLivePlayer(Player player) {
		if (player != null) {
			livePlayers.add(player);
		}
	}

	@Override
	public void moveToWinner(Player player) {
		if (livePlayers.remove(player)) {
			winners.add(player);
			view.putDescription("{}が勝利しました。", player.getScreenName());
		}
		if (1 < livePlayers.size()) {
			view.putDescription("全員の順位が決まるまで、ゲームを続行します。");
		}
	}

	@Override
	public void moveToDead(Player player) {
		if (livePlayers.remove(player)) {
			deadPlayers.add(player);
			view.putDescription("{}がゲームから脱落しました。", player.getScreenName());
		}
	}

	@Override
	public boolean isGameOver() {
		return getLivePlayersNum() <= 1;
	}

	@Override
	public List<Player> getLivePlayers() {
		return livePlayers;
	}

	@Override
	public List<Player> getDeadPlayers() {
		return deadPlayers;
	}

	@Override
	public List<Player> getWinners() {
		return winners;
	}

	@Override
	public int getLivePlayersNum() {
		return livePlayers.size();
	}

	@Override
	public int getDeadPlayersNum() {
		return deadPlayers.size();
	}

	@Override
	public int getWinnersNum() {
		return winners.size();
	}

	@Override
	public Player getLivePlayer(int id) {
		if (livePlayers != null) {
			for (Player player : livePlayers) {
				if (player.getId() == id) {
					return player;
				}
			}
		}
		return null;
	}

	@Override
	public List<Player> getPlayersRank() {
		List<Player> result = new ArrayList<>();

		if (winners != null && 0 < winners.size()) {
			for (int i = 0; i < winners.size(); i++) {
				Player one = winners.get(i);
				result.add(one);
			}
		}

		if (livePlayers != null && 0 < livePlayers.size()) {
			for (int i = 0; i < livePlayers.size(); i++) {
				Player one = livePlayers.get(i);
				result.add(one);
			}
		}

		// 脱落リストは逆順に辿る
		if (deadPlayers != null && 0 < deadPlayers.size()) {
			for (int i = deadPlayers.size() - 1; -1 < i; i--) {
				Player one = deadPlayers.get(i);
				result.add(one);
			}
		}
		return result;
	}

	@Override
	public void viewGameResult() {
		view.putResourceDescription("game.result");
		List<Player> playerList = getPlayersRank();
		for (int i = 0; i < playerList.size(); i++) {
			view.putResourceDescription("game.rank", String.valueOf(i + 1),
					playerList.get(i).getScreenName());
		}
	}

}
