package org.tigergrab.game.sevens.turnaction.impl;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.turnaction.TurnAction;

public class LeadAction implements TurnAction {

	Player player;
	Space space;
	Card card;

	public LeadAction(Player player, Space space, Card card) {
		this.player = player;
		this.space = space;
		this.card = card;
	}

	@Override
	public void execute() {
		player.leadCard(space, card);
	}

}
