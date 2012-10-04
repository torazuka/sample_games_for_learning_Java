package org.tigergrab.game.sevens.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.tigergrab.game.conf.ConfigurationManager;
import org.tigergrab.game.conf.impl.DefaultManagerMock;
import org.tigergrab.game.conf.impl.LangConfigurationAction;
import org.tigergrab.game.conf.impl.ResourceFactory;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.View;
import org.tigergrab.game.util.InputOutputUtil;

public class DefaultViewTest {

	static View view;
	static ResourceBundle resource;

	@BeforeClass
	public static void beforeClass() {
		// TODO テストに使う設定ファイルを指定

		ConfigurationManager conf = new DefaultManagerMock();
		conf.addConfigurationAction(new LangConfigurationAction());
		conf.createConfigFile();
		InputOutputUtil.writeFile(conf.getConfigFileName(), "lang-ja");

		resource = ResourceFactory.getConfigurationByFile(PKG.SEVENS);

		view = new DefaultViewMock();
	}

	@AfterClass
	public static void afterClass() {

	}

	@Ignore("ゲーム開始後に言語設定を引き継がない問題を修正中")
	@Test
	public void testPutAlert() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putAlert("test.arg0");

		String s = "> ******** " + resource.getString("test.arg0")
				+ " ******** ";

		verify(logger).info(s);
	}

	@Ignore("ゲーム開始後に言語設定を引き継がない問題を修正中")
	@Test
	public void testPutInteraction() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putInteraction("test.arg0");

		verify(logger).info("> ほげ");
	}

	@Ignore("ゲーム開始後に言語設定を引き継がない問題を修正中")
	@Test
	public void testPutDescription() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putDescription("test.arg0");

		verify(logger).info("> ほげ");
	}

	@Ignore("ゲーム開始後に言語設定を引き継がない問題を修正中")
	@Test
	public void testPutDescriptionArg1() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putDescription("test.arg1", "hoge");

		verify(logger).info("> ほげ:{}", "hoge");
	}

	@Ignore("ゲーム開始後に言語設定を引き継がない問題を修正中")
	@Test
	public void testPutDescriptionArg2() throws Exception {
		Logger logger = mock(Logger.class);

		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putDescription("test.arg2", "hoge", "moga");

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
		View view = new DefaultViewMock(logger);
		view.createConf();
		view.putSpace(game.space);

		verify(logger, times(1)).info("> 場札");
		verify(logger, times(1))
				.info(">                         [S-05]                                                \n"
						+ ">       [H-02]                                                                  \n"
						+ ">                   [D-04]                                                      \n"
						+ ">                                                             [C-11]            \n");
	}

}
