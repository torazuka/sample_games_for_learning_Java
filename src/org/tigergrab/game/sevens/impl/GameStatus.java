package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.PlayerState;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;

public class GameStatus implements Status {

	/** ターンカウンタ */
	int turnCounter;

	/** 現在のプレイヤー */
	Player currentPlayer;

	/** 生存プレイヤー */
	PlayerState players;

	/** 脱落プレイヤー */
	PlayerState losers;

	/** 勝利プレイヤー */
	PlayerState gainers;

	/** ターンの記録 */
	List<Turn> gameRecord;

	View view;

	public GameStatus() {
		turnCounter = 0;

		gameRecord = new ArrayList<>();
		view = new View();

		players = new DefaultState();
		gainers = new GainerState();
		losers = new LoserState();
	}

	/**
	 * Playerのリストを作る． 1人目はユーザであるHumanPlayer、2人目以降はAIPlayer.
	 */
	@Override
	public void createPlayers(int numPlayer) {

		Player user = new HumanPlayer(0);
		players.add(user);

		for (int i = 1; i < numPlayer; i++) {
			Player p = new AIPlayer(i);
			players.add(p);
		}
	}

	/**
	 * 山札の初期配布。事前条件として、プレイヤーのリストがすでに作成されていること。
	 */
	@Override
	public void initHands() {
		Deck deck = new Deck();
		deck.init(players);
	}

	public void setLivePlayer(Player player) {
		players.add(player);
	}

	@Override
	public void moveToGainer(Player player) {
		if (players.remove(player)) {
			gainers.add(player);
			view.putDescription("{}が勝利しました。", player.getScreenName());
		}
		if (players.isGameOver() != false) {
			view.putDescription("全員の順位が決まるまで、ゲームを続行します。");
		}
	}

	@Override
	public void moveToLoser(Player player) {
		if (players.remove(player)) {
			losers.add(player);
			view.putDescription("{}がゲームから脱落しました。", player.getScreenName());
		}
	}

	@Override
	public boolean isGameOver() {
		return getPlayersNum() <= 1;
	}

	@Override
	public List<Player> getPlayers() {
		return players.getPlayers();
	}

	@Override
	public List<Player> getLosers() {
		return losers.getPlayers();
	}

	@Override
	public List<Player> getGainers() {
		return gainers.getPlayers();
	}

	@Override
	public int getPlayersNum() {
		return players.getPlayers().size();
	}

	@Override
	public int getLosersNum() {
		return losers.getPlayers().size();
	}

	@Override
	public int getGainersNum() {
		return gainers.getPlayers().size();
	}

	@Override
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

	@Override
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

	@Override
	public void viewGameResult() {
		view.putResourceDescription("game.result");
		List<Player> playerList = getPlayersRank();
		for (int i = 0; i < playerList.size(); i++) {
			view.putResourceDescription("game.rank", String.valueOf(i + 1),
					playerList.get(i).getScreenName());
		}
	}

	@Override
	public void playback() {
		for (Turn turn : gameRecord) {
			System.out.println(turn.getCurrentPlayer().getScreenName());
			view.putSpace(turn.getCurrentSpace());
		}
	}

	@Override
	public void registerRecord(Turn turn) {
		Space tmpSpace = turn.getCurrentSpace();
		Space currentSpace = new DefaultSpace();
		EnumSet<Suite> allSuites = EnumSet.allOf(Suite.class);
		for (Suite s : allSuites) {
			List<Card> cardsBySuite = tmpSpace.getCardsBySuite(s);
			if (cardsBySuite != null) {
				for (Card card : cardsBySuite) {
					currentSpace.putCard(card);
				}
			}
		}
		Turn thisTurn = new DefaultTurn(currentSpace, turn.getCurrentPlayer());
		gameRecord.add(thisTurn);
	}
}
