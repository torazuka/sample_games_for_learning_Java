package org.tigergrab.game.sevens.impl;

import org.slf4j.Logger;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.GameEvent;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;

public class DefaultGameEvent implements GameEvent {

	GameEventKinds kind;

	Game game;
	Turn turn;
	Player player;
	Card card;
	String message;
	DefaultView view;
	Status status;
	Logger logger;

	public DefaultGameEvent(GameEventKinds k) {
		kind = k;
	}

	public DefaultGameEvent(GameEventKinds k, DefaultView v, Status s, Logger l) {
		kind = k;
		view = v;
		status = s;
		logger = l;
	}

	public DefaultGameEvent(GameEventKinds k, Game g, Turn t) {
		kind = k;
		game = g;
		turn = t;
	}

	public DefaultGameEvent(GameEventKinds k, String m) {
		kind = k;
		message = m;
	}

	@Override
	public Game getGame() {
		return game;
	}

	@Override
	public Turn getTurn() {
		return turn;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public Card getCard() {
		return card;
	}

	@Override
	public GameEventKinds getKind() {
		return kind;
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public DefaultView getView() {
		return view;
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
