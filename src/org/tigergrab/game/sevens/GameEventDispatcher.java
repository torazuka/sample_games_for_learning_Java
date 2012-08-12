package org.tigergrab.game.sevens;

public interface GameEventDispatcher {

	void dispatch(GameEventListener listener, GameEvent event);

}
