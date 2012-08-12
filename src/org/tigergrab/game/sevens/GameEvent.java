package org.tigergrab.game.sevens;

import org.slf4j.Logger;
import org.tigergrab.game.sevens.impl.Card;
import org.tigergrab.game.sevens.impl.Game;
import org.tigergrab.game.sevens.impl.GameEventKinds;
import org.tigergrab.game.sevens.impl.View;

public interface GameEvent {

	GameEventKinds getKind();

	Game getGame();

	Turn getTurn();

	Player getPlayer();

	Card getCard();

	Status getStatus();

	String getMessage();

	View getView();

	Logger getLogger();
}
