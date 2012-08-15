package org.tigergrab.game.sevens.impl;

import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.TurnAction;

/**
 * Turnインタフェースのデフォルト実装．
 */
public class DefaultTurn implements Turn {

	Player currentPlayer;
	Space currentSpace;
	View view;

	public DefaultTurn(Space space, Player player) {
		view = new View();

		currentPlayer = player;
		currentSpace = space;
	}

	@Override
	public void execute(Space space, Status status) {
		TurnAction action = currentPlayer.decide(space, status);
		action.execute();
		view.putSpace(space);
	}

	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	@Override
	public Space getCurrentSpace() {
		return currentSpace;
	}

}
