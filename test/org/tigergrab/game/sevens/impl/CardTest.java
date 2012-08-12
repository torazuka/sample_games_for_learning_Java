package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class CardTest {

	@Test
	public void testCardException() throws Exception {
		try {
			new Card(Suite.Spade, 0);
			fail("IllegalArgumentExceptionを期待。");
		} catch (IllegalArgumentException expected) {
			assertEquals(true, expected instanceof IllegalArgumentException);
		}

		try {
			new Card(Suite.Spade, 14);
			fail("IllegalArgumentExceptionを期待。");
		} catch (IllegalArgumentException expected) {
			assertEquals(true, expected instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testToString() throws Exception {
		Card card = new Card(Suite.Spade, 1);
		assertEquals("toStringの書式は[Suiteの完全な名前-Rankの完全な名前（1=Ace）]",
				"[Spade-Ace]", card.toString());
	}

	@Test
	public void testToShortString() throws Exception {
		Card card = new Card(Suite.Spade, 1);
		assertEquals("toShortStringの書式は[Suiteの英大文字1文字目-ランク数字（1桁の場合は前に0をつける）]",
				"[S-01]", card.toShortString());
	}

	@Test
	public void testCompareTo() throws Exception {
		Card card1 = new Card(Suite.Heart, 3);
		Card card2 = new Card(Suite.Dia, 1);
		int i = card1.compareTo(card2);
		assertEquals("card1のSuiteの方が強い場合", true, i < 0);

		card2 = new Card(Suite.Spade, 1);
		i = card1.compareTo(card2);
		assertEquals("card1のSuiteの方が弱い場合", true, 0 < i);

		card2 = new Card(Suite.Heart, 5);
		i = card1.compareTo(card2);
		assertEquals("Suiteが同じでcard1のRankの方が前の場合", true, i < 0);

		card2 = new Card(Suite.Heart, 1);
		i = card1.compareTo(card2);
		assertEquals("Suiteが同じでcard1のRankの方が後ろの場合", true, 0 < i);

		card2 = new Card(Suite.Heart, 3);
		i = card1.compareTo(card2);
		assertEquals("SuiteもRankも同じ場合", true, i == 0);
	}

	@Test
	public void testToMiniString() throws Exception {
		Card card0 = new Card(Suite.Spade, 7);
		assertEquals("スペード7のミニマム表記はS-07。", "S-07", card0.toMiniString());

		Card card1 = new Card(Suite.Spade, 13);
		assertEquals("スペード13のミニマム表記はS-13。", "S-13", card1.toMiniString());
	}

}
