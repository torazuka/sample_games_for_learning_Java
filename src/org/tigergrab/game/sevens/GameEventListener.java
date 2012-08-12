package org.tigergrab.game.sevens;

public interface GameEventListener {

	void beginTurn(GameEvent event);

	void endTurn(GameEvent event);

	void viewMessage(GameEvent event);
}
