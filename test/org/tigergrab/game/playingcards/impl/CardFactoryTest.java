package org.tigergrab.game.playingcards.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class CardFactoryTest {
	CardFactory factory;

	@Test
	public void testGetCardTest() throws Exception {
		factory = new CardFactory();
		List<Card> target = factory.getCardList(Suite.Spade);

		assertNotNull(target);
		assertEquals("Spadeのカード一式は、13枚。", 13, target.size());
		assertEquals("Spadeのカード一式は、S-07を含む。", true,
				target.contains(new Card(Suite.Spade, 7)));
		assertEquals("Spadeのカード一式は、H-07を含まない。", false,
				target.contains(new Card(Suite.Heart, 7)));
	}

	@Test
	public void testGetAllCard() throws Exception {
		factory = new CardFactory();
		List<Card> target = factory.getAllCard();

		assertNotNull(target);
		assertEquals("トランプ一式は、52枚。", 52, target.size());
		assertEquals("トランプ一式は、S-07を含む。", true,
				target.contains(new Card(Suite.Spade, 7)));
		assertEquals("トランプ一式は、H-07を含む。", true,
				target.contains(new Card(Suite.Heart, 7)));
		assertEquals("トランプ一式は、D-07を含む。", true,
				target.contains(new Card(Suite.Dia, 7)));
		assertEquals("トランプ一式は、C-07を含む。", true,
				target.contains(new Card(Suite.Club, 7)));
	}
}
