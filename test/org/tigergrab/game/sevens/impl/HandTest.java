package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;

public class HandTest {

	@Test
	public void testLead() throws Exception {
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Spade, 1));
		Hand hand0 = new Hand(list0);

		assertEquals("ハートの1を出せない。", false, hand0.lead(new Card(Suite.Heart, 1)));
		assertEquals("スペードの1を出せる。", true, hand0.lead(new Card(Suite.Spade, 1)));
		assertEquals("スペードの1を2回出すことはできない。", false,
				hand0.lead(new Card(Suite.Spade, 1)));
	}

	@Test
	public void testHas() throws Exception {
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Spade, 1));
		Hand hand0 = new Hand(list0);

		assertEquals("手札にスペードの1がある。", true, hand0.has(new Card(Suite.Spade, 1)));
		assertEquals("手札にハートの1はない。", false, hand0.has(new Card(Suite.Heart, 1)));
	}

	@Test
	public void testHasRest() throws Exception {
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Spade, 1));
		Hand hand0 = new Hand(list0);

		assertEquals("手札に残りがある。", true, hand0.hasRest());

		hand0.lead(new Card(Suite.Spade, 1));
		assertEquals("手札に残りがない。", false, hand0.hasRest());

		Hand hand1 = new Hand();
		assertEquals("初期状態では手札がない。", false, hand1.hasRest());
	}

	@Test
	public void testGetHandNum() throws Exception {
		List<Card> list0 = new ArrayList<>();
		list0.add(new Card(Suite.Spade, 1));
		Hand hand0 = new Hand(list0);

		assertEquals("手札は1枚。", 1, hand0.getHandNum());

		hand0.lead(new Card(Suite.Spade, 1));
		assertEquals("手札を出したら、残りは0枚。", 0, hand0.getHandNum());
	}
}
