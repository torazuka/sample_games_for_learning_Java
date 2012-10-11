package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.turnaction.TurnAction;

public class HumanPlayerMockForDecidePass extends HumanPlayer {

	public HumanPlayerMockForDecidePass(int i) {
		super(i);
	}

	@Override
	protected String read() {
		return "pass";
	}

	@Override
	protected TurnAction createLeadAction(Space space, Card card) {
		fail("ここに到達すれば失敗。");
		return super.createLeadAction(space, card);
	}

	@Override
	protected TurnAction createPassAction(Status status) {
		assertEquals("ここに到達すれば成功", 1, 1);
		return super.createPassAction(status);
	}

}
