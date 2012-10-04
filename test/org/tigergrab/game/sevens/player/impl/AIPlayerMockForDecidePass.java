package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;

public class AIPlayerMockForDecidePass extends AIPlayer {

	public AIPlayerMockForDecidePass(int i) {
		super(i);
	}

	@Override
	public List<Card> getLeadableCards(Space space) {
		// 場に出せるカードが1枚もない状態
		List<Card> result = new ArrayList<>();
		return result;
	}

	@Override
	public void leadCard(Space space, Card card) {
		fail("呼び出されたら失敗。");
	}

	@Override
	public void pass(Status status) {
		assertEquals("呼び出されたら成功。", 0, 0);
	}
}
