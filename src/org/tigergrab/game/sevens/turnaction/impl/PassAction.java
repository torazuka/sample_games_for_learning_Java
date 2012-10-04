package org.tigergrab.game.sevens.turnaction.impl;

import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.turnaction.TurnAction;

public class PassAction implements TurnAction {

	Player player;
	Status status;

	public PassAction(Player player, Status status) {
		this.player = player;
		this.status = status;
	}

	@Override
	public void execute() {
		player.pass(status);
	}

}
