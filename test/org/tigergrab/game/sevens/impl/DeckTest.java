package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;

public class DeckTest {

	@Test
	public void testDeck() throws Exception {
		Deck deck = new Deck();

		assertEquals(52, deck.cards.size());

		Iterator<Card> iterator = deck.cards.iterator();
		int spadeNum = 0;
		int heartNum = 0;
		int diaNum = 0;
		int clubNum = 0;
		for (; iterator.hasNext();) {
			Card tmp = iterator.next();
			if (tmp.filter(Suite.Spade)) {
				spadeNum++;
			} else if (tmp.filter(Suite.Heart)) {
				heartNum++;
			} else if (tmp.filter(Suite.Dia)) {
				diaNum++;
			} else if (tmp.filter(Suite.Club)) {
				clubNum++;
			} else {
				fail("SPADEでもHEARTでもDIAでもCLUBでもないSuiteは存在しない。");
			}
		}

		assertEquals("SPADEは13枚ある。", 13, spadeNum);
		assertEquals("HEARTは13枚ある。", 13, heartNum);
		assertEquals("DIAは13枚ある。", 13, diaNum);
		assertEquals("CLUBは13枚ある。", 13, clubNum);
	}

	@Test
	public void testGetRandomIndex() throws Exception {
		Deck cards = new Deck();

		int maxNum = 10;
		int randomIndex = cards.getRandomIndex(maxNum);

		assertEquals("0以上、引数未満の値を返す。", true, -1 < randomIndex
				&& randomIndex < maxNum);
	}

	@Test
	public void testGetRandomIndexForSeedFix() throws Exception {
		Deck deck = new Deck() {
			@Override
			protected Random getRandom() {
				return new Random(10);
			}
		};
		assertEquals("Seedを10に固定した場合、戻り値は41．", 41, deck.getRandomIndex(52));
	}

	@Test
	public void testGetPlayerIndex() throws Exception {
		Deck deck = new Deck();

		int index = deck.getPlayerIndex(3, 0);
		assertEquals("プレイヤーが3人の場合、1枚目を配る相手は1番目のプレイヤー。", 0, index);
		index = deck.getPlayerIndex(3, 50);
		assertEquals("プレイヤーが3人の場合、51枚目を配る相手は3番目のプレイヤー。", 2, index);
		index = deck.getPlayerIndex(3, 51);
		assertEquals("プレイヤーが3人の場合、52枚目を配る相手は1番目のプレイヤー。", 0, index);
	}

	@Test
	public void testdevideCards() throws Exception {
		Deck deck1 = new Deck();
		List<List<Card>> dealed = deck1.divideCards(4);
		assertEquals("52枚を4人に配ったら、1人（目）の手札は13枚。", 13, dealed.get(0).size());

		Deck deck2 = new Deck();
		List<List<Card>> dealed1 = deck2.divideCards(5);
		assertEquals("52枚を5人に配ったら、1人目の手札は11枚。", 11, dealed1.get(0).size());
	}

}
