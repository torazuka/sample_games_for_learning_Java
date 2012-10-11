package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.turnaction.TurnAction;

public class HumanPlayerMockForDecide extends HumanPlayer {

	public HumanPlayerMockForDecide(int i) {
		super(i);
	}

	@Override
	protected String read() {
		return "S-01";
	}

	@Override
	protected TurnAction createLeadAction(Space space, Card card) {
		assertEquals("ここに到達すれば成功。", 1, 1);
		return super.createLeadAction(space, card);
	}

	@Override
	protected TurnAction createPassAction(Status status) {
		fail("ここに到達すれば失敗。");
		return super.createPassAction(status);
	}

	@Override
	public boolean hasCard(Card target) {
		return true;
	}

	@Override
	public boolean checkSpace(Space space, Card card) {
		return true;
	}

	@Override
	protected boolean confirm() {
		return true;
	}
}
