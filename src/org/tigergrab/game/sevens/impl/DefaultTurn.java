package org.tigergrab.game.sevens.impl;

import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.turnaction.TurnAction;

/**
 * Turnインタフェースのデフォルト実装．
 */
public class DefaultTurn implements Turn {

	Player currentPlayer;
	Space currentSpace;

	public DefaultTurn(Space space, Player player) {
		currentPlayer = player;
		currentSpace = space;
	}

	@Override
	public void execute(Space space, Status status) {
		TurnAction action = currentPlayer.decide(space, status);
		action.execute();

		// 手札がなくなった場合、プレイヤーを勝利者リストに移動
		if (currentPlayer.hasRestHand() == false) {
			status.moveToGainer(currentPlayer);
		}
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
