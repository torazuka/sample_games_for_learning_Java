package org.tigergrab.game.sevens.impl;

import java.util.*;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.PlayerState;
import org.tigergrab.game.sevens.player.impl.PlayerManager;

public class GameStatus implements Status {

	PlayerManager playerManager;

	/** ターンカウンタ */
	int turnCounter;

	/** 現在のプレイヤー */
	Player currentPlayer;

	/** ターンの記録 */
	List<Turn> gameRecord;

	DefaultView view;

	public GameStatus(DefaultView view) {
		this.view = view;

		playerManager = new PlayerManager(this.view);
		turnCounter = 0;
		gameRecord = new ArrayList<>();
	}

	/**
	 * Playerを生成する.
	 */
	@Override
	public void createPlayers(int numPlayer) {
		playerManager.createPlayers(numPlayer);
	}

	/**
	 * 山札の初期配布。事前条件として、プレイヤーがすでに作成されていること。
	 */
	@Override
	public void initHands() {
		int livePlayerNum = playerManager.getLivePlayerNum();
		Deck deck = new Deck();
		List<List<Card>> cardsList = deck.divideCards(livePlayerNum);

		PlayerState ps = playerManager.getPlayers();
		List<Player> players = ps.getPlayers();
		// 分けたカードを個々のプレイヤーと関連づける
		Iterator<Player> playersIterator = players.iterator();
		Iterator<List<Card>> dealedIterator = cardsList.iterator();
		for (; playersIterator.hasNext() && dealedIterator.hasNext();) {
			Player player = playersIterator.next();
			player.setHand(dealedIterator.next());
		}
	}

	@Override
	public Player getNextPlayer(Player currentPlayer) {
		return playerManager.getNextPlayer(currentPlayer);
	}

	// TODO: テスト用暫定。
	public void setLivePlayer(Player player) {
		playerManager.addPlayers(player);
	}

	@Override
	public void moveToGainer(Player player) {
		playerManager.moveToGainer(player);
	}

	@Override
	public void moveToLoser(Player player) {
		playerManager.moveToLoser(player);
	}

	@Override
	public boolean isGameOver() {
		return getPlayersNum() <= 1;
	}

	@Override
	public List<Player> getPlayers() {
		PlayerState ps = playerManager.getPlayers();
		return ps.getPlayers();
	}

	@Override
	public int getPlayersNum() {
		return playerManager.getLivePlayerNum();
	}

	@Override
	public List<Player> getPlayersRank() {
		return playerManager.getPlayersRank();
	}

	@Override
	public void viewGameResult() {
		view.putDescription("game.result");
		List<Player> playerList = getPlayersRank();
		for (int i = 0; i < playerList.size(); i++) {
			view.putDescription("game.rank", String.valueOf(i + 1), playerList
					.get(i).getScreenName());
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
