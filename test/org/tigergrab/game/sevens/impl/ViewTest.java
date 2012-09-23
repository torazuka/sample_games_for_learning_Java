package org.tigergrab.game.sevens.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.slf4j.Logger;

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

		verify(logger, times(1)).info("> 場札");
		verify(logger, times(1))
				.info(">                         [S-05]                                                \n"
						+ ">       [H-02]                                                                  \n"
						+ ">                   [D-04]                                                      \n"
						+ ">                                                             [C-11]            \n");
	}

}
