package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;

public class AIPlayerTest {
	@Test
	public void testDecide() throws Exception {

		// TODO target戻り値のインスタンスをtarget内で骨格実装している場合、どうやって評価するのか？
	}

	@Test
	public void testGetLeadableCards() throws Exception {
		AIPlayer player = new AIPlayer(1);

		List<Card> cardList = new ArrayList<>();
		cardList.add(new Card(Suite.Spade, 4));
		cardList.add(new Card(Suite.Spade, 5));
		cardList.add(new Card(Suite.Spade, 6));
		player.setHand(cardList);

		Space space0 = new DefaultSpace();
		space0.putCard(new Card(Suite.Spade, 7));
		List<Card> leadableCards = player.getLeadableCards(space0);

		assertEquals("場札:S-07, 手札:S-04, S-05, S-06のとき、出せる札は1枚。", 1,
				leadableCards.size());
	}

	@Test
	public void testGetLeadableCardsNo() throws Exception {
		AIPlayer player = new AIPlayer(1);

		List<Card> cardList = new ArrayList<>();
		cardList.add(new Card(Suite.Spade, 3));
		cardList.add(new Card(Suite.Spade, 4));
		cardList.add(new Card(Suite.Spade, 5));
		player.setHand(cardList);

		Space space0 = new DefaultSpace();
		space0.putCard(new Card(Suite.Spade, 7));
		List<Card> leadableCards = player.getLeadableCards(space0);

		assertEquals("場札:S-07, 手札:S-03, S-04, S-05のとき、出せる札は0枚。", 0,
				leadableCards.size());
	}

	@Test
	public void testGetNextCards() throws Exception {
		AIPlayer player = new AIPlayer(1);

		Space space0 = new DefaultSpace();
		space0.putCard(new Card(Suite.Spade, 7));
		List<Card> nextCards = player.getNextCards(space0);
		assertEquals("Spadeのランク7のカードだけが場に出ているとき、出せる札は2枚。", 2, nextCards.size());

		Space space1 = new DefaultSpace();
		space1.putCard(new Card(Suite.Spade, 7));
		space1.putCard(new Card(Suite.Heart, 7));
		space1.putCard(new Card(Suite.Dia, 7));
		space1.putCard(new Card(Suite.Club, 7));
		nextCards = player.getNextCards(space1);
		assertEquals("各Suiteのランク7のカードが場に出ているとき、出せる札は8枚。", 8, nextCards.size());

		Space space2 = new DefaultSpace();
		nextCards = player.getNextCards(space2);
		assertEquals("どのカードも場に出ていないとき、出せる札は0枚。（起こりえない状況）", 0, nextCards.size());
	}

	@Test
	public void testGetSuiteLimit() throws Exception {

		Space space = new DefaultSpace();
		space.putCard(new Card(Suite.Spade, 7));
		space.putCard(new Card(Suite.Heart, 7));
		space.putCard(new Card(Suite.Dia, 7));
		space.putCard(new Card(Suite.Club, 7));

		AIPlayer ai = new AIPlayer(1);

		Map<Suite, SuiteLimit> suiteLimit = ai.getSuiteLimit(space);
		SuiteLimit spadeLimit = suiteLimit.get(Suite.Spade);
		assertEquals("場にランク7の札しかない状態では、Spadeの最大は7。", 7, spadeLimit.getMax());
		assertEquals("場にランク7の札しかない状態では、Spadeの最小は7。", 7, spadeLimit.getMin());

		SuiteLimit heartLimit = suiteLimit.get(Suite.Heart);
		assertEquals("場にランク7の札しかない状態では、Heartの最大は7。", 7, heartLimit.getMax());
		assertEquals("場にランク7の札しかない状態では、Heartの最小は7。", 7, heartLimit.getMin());

		SuiteLimit diaLimit = suiteLimit.get(Suite.Heart);
		assertEquals("場にランク7の札しかない状態では、Diaの最大は7。", 7, diaLimit.getMax());
		assertEquals("場にランク7の札しかない状態では、Diaの最小は7。", 7, diaLimit.getMin());

		SuiteLimit clubLimit = suiteLimit.get(Suite.Heart);
		assertEquals("場にランク7の札しかない状態では、Clubの最大は7。", 7, clubLimit.getMax());
		assertEquals("場にランク7の札しかない状態では、Clubの最小は7。", 7, clubLimit.getMin());
	}

	@Test
	public void testGetSuiteLimitForOneCard() throws Exception {
		Space space = new DefaultSpace();
		space.putCard(new Card(Suite.Spade, 7));

		AIPlayer ai = new AIPlayer(1);

		Map<Suite, SuiteLimit> suiteLimit = ai.getSuiteLimit(space);
		assertEquals("場にSpadeの7だけ出ているときのSuiteLimitは1つだけ。", 1, suiteLimit.size());
	}

	@Test
	public void testGetSuiteLimitNone() throws Exception {
		Space space = new DefaultSpace();

		AIPlayer ai = new AIPlayer(1);

		Map<Suite, SuiteLimit> suiteLimit = ai.getSuiteLimit(space);
		assertEquals("場にカードが出ていないときのSuiteLimitはなし。（起こりえない状況）", 0,
				suiteLimit.size());
	}

	@Test
	public void testCreateLimitsForRange() throws Exception {

		Space space = new DefaultSpace();

		space.putCard(new Card(Suite.Spade, 7));
		space.putCard(new Card(Suite.Spade, 8));

		space.putCard(new Card(Suite.Heart, 1));
		space.putCard(new Card(Suite.Heart, 7));

		space.putCard(new Card(Suite.Dia, 5));
		space.putCard(new Card(Suite.Dia, 13));

		space.putCard(new Card(Suite.Club, 7));

		AIPlayer ai = new AIPlayer(1);

		Map<Suite, SuiteLimit> suiteLimit = ai.getSuiteLimit(space);
		SuiteLimit spadeLimit = suiteLimit.get(Suite.Spade);
		assertEquals("場にSpadeの7～8の札が出ているとき、Spadeの最大は8。", 8, spadeLimit.getMax());
		assertEquals("場にSpadeの7～8の札が出ているとき、Spadeの最小は7。", 7, spadeLimit.getMin());

		SuiteLimit heartLimit = suiteLimit.get(Suite.Heart);
		assertEquals("場にHeartの1～7の札が出ているとき、Heartの最大は7。", 7, heartLimit.getMax());
		assertEquals("場にHeartの1～7の札が出ているとき、Heartの最小は1。", 1, heartLimit.getMin());

		SuiteLimit diaLimit = suiteLimit.get(Suite.Dia);
		assertEquals("場にDiaの5～13の札が出ているとき、Diaの最大は13。", 13, diaLimit.getMax());
		assertEquals("場にDiaの5～13の札が出ているとき、Diaの最小は5。", 5, diaLimit.getMin());

		SuiteLimit clubLimit = suiteLimit.get(Suite.Club);
		assertEquals("場にClubの7の札が出ているとき、Clubの最大は7。", 7, clubLimit.getMax());
		assertEquals("場にClubの7の札が出ているとき、Clubの最小は7。", 7, clubLimit.getMin());
	}

	@Test
	public void testGetScreenName() throws Exception {
		AIPlayer player = new AIPlayer(1);
		assertEquals("PlayerID1のAIPlayerの表示名は、「プレイヤーID 1 さん」", "プレイヤーID 1 さん",
				player.getScreenName());
	}

	@Test
	public void testShowHand() throws Exception {

		List<Player> players = new ArrayList<Player>();
		HumanPlayer p0 = new HumanPlayer(0);
		players.add(p0);

		Logger logger1 = mock(Logger.class);
		AIPlayer p1 = new AIPlayer(1, logger1);
		players.add(p1);
		setDataForPutHand(players);

		Logger logger0 = mock(Logger.class);
		p0.view = new View(logger0);
		p0.showHand();
		verify(logger0).info("> {}の手札: ", "あなた");

		p1.showHand();
		verify(logger1).debug("{}の手札: ", "プレイヤーID 1 さん");
		verify(logger1).debug(" もうありません。");
	}

	protected void setDataForPutHand(List<Player> players) {

		List<Card> list0 = new ArrayList<Card>();
		list0.add(new Card(Suite.Spade, 1));
		list0.add(new Card(Suite.Heart, 1));

		// no hand
		List<Card> list1 = new ArrayList<Card>();
		list1.clear();

		players.get(0).setHand(list0);
		players.get(1).setHand(list1);
	}
}
