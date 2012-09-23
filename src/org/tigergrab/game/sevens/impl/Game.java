package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.GameEvent;
import org.tigergrab.game.sevens.GameEventDispatcher;
import org.tigergrab.game.sevens.GameEventListener;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 七並べを進行する主要なクラス．
 */
public class Game {

	private static Logger logger = LoggerFactory.getLogger(Game.class);

	/** 場札 */
	Space space;

	/** ゲームの状態 */
	Status status;

	View view;

	protected List<GameEventListener> listeners;
	protected Map<GameEventKinds, GameEventDispatcher> dispatchers;

	public Game() {
		space = new DefaultSpace();
		status = new GameStatus();

		view = new View();

		listeners = new ArrayList<>();
		dispatchers = new HashMap<>();
		setUpDispatchers();
	}

	protected void addListener(GameEventListener listener) {
		listeners.add(listener);
	}

	protected int getDefaultPlayerNum() {
		return 2;
	}

	public void execute() {

		initGameStatus();
		Player currentPlayer = getFirstPlayer();

		int turnCounter = 0;
		for (; status.isGameOver() == false;) {
			++turnCounter;
			view.putDescription("[{}ターン目] {}のターンを開始します。",
					String.valueOf(turnCounter), currentPlayer.getScreenName());

			executeTurn(currentPlayer.createTurn(space));

			currentPlayer = getNextPlayer(currentPlayer);
		}

		endGame();
	}

	protected void initGameStatus() {

		status.createPlayers(getNumPlayers());
		status.initHands();

		view.putResourceDescription("init.leads");

		if (logger.isDebugEnabled()) {
			List<Player> livePlayers = status.getPlayers();
			for (Player player : livePlayers) {
				player.showHand();
			}
		}
	}

	public void executeTurn(Turn turn) {
		dispatch(new DefaultGameEvent(GameEventKinds.beginTurn, this, turn));

		turn.execute(space, status);
		status.registerRecord(turn);

		dispatch(new DefaultGameEvent(GameEventKinds.endTurn, this, turn));
	}

	protected void endGame() {
		status.viewGameResult();

		view.putInteraction("ゲームの様子を再生しますか？ (y: する)");
		String doReplay = read();
		if (doReplay.equals("y")) {
			status.playback();
		}
	}

	/**
	 * ゲームの最初にランク7のカードを場に出す。 ダイヤの7を出したプレイヤーを、最初のプレイヤーとして返す。
	 */
	public Player getFirstPlayer() {

		Player result = null;

		view.putDescription("すべてのプレイヤーは、7のカードが手札にある場合、場に出します。");

		List<Player> livePlayers = status.getPlayers();
		for (Player player : livePlayers) {

			Card spade7 = new Card(Suite.Spade, 7);
			if (player.hasCard(spade7)) {
				player.leadCard(space, status, spade7);
			}

			Card heart7 = new Card(Suite.Heart, 7);
			if (player.hasCard(heart7)) {
				player.leadCard(space, status, heart7);
			}

			Card dia7 = new Card(Suite.Dia, 7);
			if (player.hasCard(dia7)) {
				player.leadCard(space, status, dia7);

				result = player;
			}

			Card club7 = new Card(Suite.Club, 7);
			if (player.hasCard(club7)) {
				player.leadCard(space, status, club7);
			}
		}

		view.putSpace(space);
		view.putDescription("最初の手番は、ダイヤの7を出した{}です。", result.getScreenName());

		return result;
	}

	protected Player getNextPlayer(Player current) {
		int id = current.getId();
		if (id < status.getPlayersNum() - 1) {
			id++;
		} else {
			id = 0;
		}
		return status.getLivePlayer(id);
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	/**
	 * @return プレイ人数を返す。
	 */
	protected int getNumPlayers() {
		int result = 0;
		view.putResourceInteraction("get.numplayers");
		for (;;) {
			String inputNum = read();

			if (inputNum == null || inputNum.equals("")) {
				result = getDefaultPlayerNum();
				break;
			}

			int initNumPlayer = 0;
			try {
				initNumPlayer = Integer.valueOf(inputNum);
			} catch (NumberFormatException e) {
				view.putResourceInteraction("get.numplayers");
				logger.warn("warn: {}", inputNum);
				continue;
			}

			if (initNumPlayer < 2 || 10 < initNumPlayer) {
				view.putResourceInteraction("get.numplayers");
				continue;
			} else {
				result = initNumPlayer;
				break;
			}
		}
		// TODO ユーザへの説明表示が混じっているので何とかする
		view.putResourceDescription("view.numplayers", String.valueOf(result));
		return result;
	}

	protected void setUpDispatchers() {

		dispatchers.put(GameEventKinds.beginTurn, new GameEventDispatcher() {
			@Override
			public void dispatch(GameEventListener listener, GameEvent event) {
				listener.beginTurn(event);
			}
		});

		dispatchers.put(GameEventKinds.endTurn, new GameEventDispatcher() {
			@Override
			public void dispatch(GameEventListener listener, GameEvent event) {
				listener.endTurn(event);
			}
		});

		dispatchers.put(GameEventKinds.viewMessage, new GameEventDispatcher() {
			@Override
			public void dispatch(GameEventListener listener, GameEvent event) {
				// TODO Viewの処理がロジックの処理に入りこんでいるが、本来はロギングを全てdispatchしたい。
			}
		});
	}

	protected void dispatch(GameEvent event) {
		logger.debug("dispatch {}", event);
		GameEventDispatcher dispatcher = dispatchers.get(event.getKind());
		if (dispatcher != null) {
			for (GameEventListener listener : listeners) {
				dispatcher.dispatch(listener, event);
			}
		}
	}
}
