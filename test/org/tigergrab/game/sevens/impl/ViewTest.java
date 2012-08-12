package org.tigergrab.game.sevens.impl;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.tigergrab.game.sevens.Player;

public class ViewTest {

	@Test
	public void testPutAlert() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putAlert("test");

		verify(logger).info("> ******** test ******** ");
	}

	@Test
	public void testPutInteraction() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putInteraction("test");

		verify(logger).info("> test");
	}

	@Test
	public void testPutDescription() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putDescription("test");

		verify(logger).info("> test");
	}

	@Test
	public void testPutDescriptionArg1() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putDescription("test {}", "hoge");

		verify(logger).info("> test {}", "hoge");
	}

	@Test
	public void testPutDescriptionArg2() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putDescription("test {} {}", "hoge", "moga");

		verify(logger).info("> test {} {}", "hoge", "moga");
	}

	@Test
	public void testPutResourceDescription2() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceDescription("test.description2", "hoge", "moga");

		verify(logger).info("> desdes {} {}", "hoge", "moga");
	}

	@Test
	public void testPutSpace() throws Exception {
		Game game = new Game();

		game.space.putCard(new Card(Suite.Spade, 5));
		game.space.putCard(new Card(Suite.Heart, 2));
		game.space.putCard(new Card(Suite.Dia, 4));
		game.space.putCard(new Card(Suite.Club, 11));

		Logger logger = mock(Logger.class);
		View view = new View(logger);
		view.putSpace(game.space);

		verify(logger, times(1)).info("> 現在の場札");
		verify(logger, times(1))
				.info(">                         [S-05]                                                \n"
						+ ">       [H-02]                                                                  \n"
						+ ">                   [D-04]                                                      \n"
						+ ">                                                             [C-11]            \n");
	}

	@Test
	public void testPutHand() throws Exception {
		Logger logger = mock(Logger.class);
		View view = new View(logger);

		List<Player> players = new ArrayList<Player>();
		Player p0 = new HumanPlayer(0);
		players.add(p0);
		Player p1 = new AIPlayer(1);
		players.add(p1);
		setDataForPutHand(players);

		view.putHand(p0);
		verify(logger).info("> [S-01] [H-01]");

		view.putHand(p1);
		verify(logger).info("> プレイヤーID 1 さんの手札はもうありません。");
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
