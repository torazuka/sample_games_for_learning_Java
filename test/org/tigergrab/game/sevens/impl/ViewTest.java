package org.tigergrab.game.sevens.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;

public class ViewTest {

	@Test
	public void testPutResourceAlert() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceAlert("test.arg0");

		verify(logger).info("> ******** ほげ ******** ");
	}

	@Test
	public void testPutResourceInteraction() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceInteraction("test.arg0");

		verify(logger).info("> ほげ");
	}

	@Test
	public void testPutResourceDescription() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceDescription("test.arg0");

		verify(logger).info("> ほげ");
	}

	@Test
	public void testPutResourceDescriptionArg1() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceDescription("test.arg1", "hoge");

		verify(logger).info("> ほげ:{}", "hoge");
	}

	@Test
	public void testPutResourceDescriptionArg2() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new View(logger);
		view.putResourceDescription("test.arg2", "hoge", "moga");

		verify(logger).info("> ほげ:{}-もが:{}", "hoge", "moga");
	}

	@Test
	@Ignore
	public void testPutSpace() throws Exception {
		Game game = new Game();

		game.space.putCard(new Card(Suite.Spade, 5));
		game.space.putCard(new Card(Suite.Heart, 2));
		game.space.putCard(new Card(Suite.Dia, 4));
		game.space.putCard(new Card(Suite.Club, 11));

		Logger logger = mock(Logger.class);
		View view = new View(logger);
		view.putSpace(game.space);

		verify(logger, times(1)).info("> 場札");
		verify(logger, times(1))
				.info(">                         [S-05]                                                \n"
						+ ">       [H-02]                                                                  \n"
						+ ">                   [D-04]                                                      \n"
						+ ">                                                             [C-11]            \n");
	}

}
