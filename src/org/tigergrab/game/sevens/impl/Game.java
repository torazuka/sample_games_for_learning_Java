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
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 七並べを進行する主要なクラス．
 */
public class Game {

	private static Logger logger = LoggerFactory.getLogger(Game.class);

	Space space;
	Status status;

	DefaultView view;

	protected List<GameEventListener> listeners;
	protected Map<GameEventKinds, GameEventDispatcher> dispatchers;

	public Game() {
		view = new DefaultView();
		space = new DefaultSpace();
		status = new GameStatus(view);

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

	public boolean execute() {
		space = new DefaultSpace();
		status = new GameStatus(view);
		initGameStatus();
		Player currentPlayer = getFirstPlayer();
		leadSevens();

		int turnCounter = 0;
		for (; status.isGameOver() == false;) {
			++turnCounter;
			view.putDescription("turn.begin", String.valueOf(turnCounter),
					currentPlayer.getScreenName());

			executeTurn(currentPlayer.createTurn(space));
			currentPlayer = status.getNextPlayer(currentPlayer);
		}
		return endGame();
	}

	protected void initGameStatus() {
		status.createPlayers(getNumPlayers());
		status.initHands();
		view.putDescription("init.leads");

		if (logger.isDebugEnabled()) {
			List<Player> livePlayers = status.getPlayers();
			showHandFordebug(livePlayers);
		}
	}

	protected void showHandFordebug(List<Player> livePlayers) {
		for (Player player : livePlayers) {
			player.showHand();
		}
	}

	/**
	 * 引数のターンを実行する．
	 */
	public void executeTurn(Turn turn) {
		dispatch(new DefaultGameEvent(GameEventKinds.beginTurn, this, turn));

		turn.execute(space, status);
		view.putSpace(space);
		status.registerRecord(turn);

		dispatch(new DefaultGameEvent(GameEventKinds.endTurn, this, turn));
	}

	protected boolean endGame() {
		status.viewGameResult();

		view.putInteraction("q.replay");
		String doReplay = read();
		if (doReplay.equals("y")) {
			status.playback();
		}

		view.putInteraction("q.continue");
		String doContinue = read();
		if (doContinue.equals("y")) {
			return true;
		}
		return false;
	}

	/**
	 * ダイヤの7を手札に持つプレイヤーを、最初のプレイヤーとして返す．ゲームの最初に一度だけ行う．
	 */
	protected Player getFirstPlayer() {
		Player result = null;
		List<Player> livePlayers = status.getPlayers();
		for (Player player : livePlayers) {
			result = (player.hasCard(new Card(Suite.Dia, 7))) ? player : result;
		}
		view.putDescription("info.firstplayer", result.getScreenName());
		return result;
	}

	/**
	 * 各プレイヤーの手札を調べ、ランク7のカードを場に出す．ゲームの最初に一度だけ行う．
	 */
	protected void leadSevens() {
		view.putDescription("info.makedeck");
		List<Player> livePlayers = status.getPlayers();
		for (Player player : livePlayers) {
			lead(player, new Card(Suite.Spade, 7));
			lead(player, new Card(Suite.Heart, 7));
			lead(player, new Card(Suite.Dia, 7));
			lead(player, new Card(Suite.Club, 7));
		}
		view.putSpace(space);
	}

	protected void lead(Player player, Card card) {
		player.leadCard(space, card);
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	/**
	 * @return プレイ人数を返す。
	 */
	protected int getNumPlayers() {
		int result = 0;
		view.putInteraction("q.numplayers");
		for (;;) {
			String inputNum = read();

			if (inputNum.equals("")) {
				result = getDefaultPlayerNum();
				break;
			}

			int initNumPlayer = 0;
			try {
				initNumPlayer = Integer.valueOf(inputNum);
			} catch (NumberFormatException e) {
				view.putInteraction("q.numplayers");
				logger.warn("warn: {}", inputNum);
				continue;
			}

			if (initNumPlayer < 2 || 10 < initNumPlayer) {
				view.putInteraction("q.numplayers");
				continue;
			} else {
				result = initNumPlayer;
				break;
			}
		}
		view.putDescription("info.numplayers", String.valueOf(result));
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
