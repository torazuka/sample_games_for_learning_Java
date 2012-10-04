package org.tigergrab.game.sevens.impl;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.conf.ConfigurationManager;
import org.tigergrab.game.conf.impl.DefaultManager;
import org.tigergrab.game.conf.impl.ResourceFactory;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.CardFactory;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.View;

/**
 * 情報の出力を扱う．ユーザに見せるためにコンソールに出したり，ログとしてファイルに出したりする．
 */
public class DefaultView implements View {

	private Logger logger;

	ResourceBundle.Control control;
	ResourceBundle resources;

	public DefaultView() {

		logger = LoggerFactory.getLogger(DefaultView.class);

		resources = ResourceFactory.getConfigurationByFile(PKG.SEVENS);
	}

	public DefaultView(Logger log) {
		this();
		logger = log;
	}

	@Override
	public ConfigurationManager createConf() {
		return new DefaultManager();
	}

	@Override
	public void putInteraction(String str) {
		logger.info("> " + resources.getString(str));
	}

	@Override
	public void putInteraction(String str, String arg) {
		logger.info("> " + resources.getString(str), arg);
	}

	@Override
	public void putAlert(String str) {
		logger.info("> ******** " + resources.getString(str) + " ******** ");
	}

	@Override
	public void putDescription(String str) {
		logger.info("> " + resources.getString(str));
	}

	@Override
	public void putDescription(String str, String arg) {
		logger.info("> " + resources.getString(str), arg);
	}

	@Override
	public void putDescription(String str, String arg1, String arg2) {
		logger.info("> " + resources.getString(str), arg1, arg2);
	}

	@Override
	public void putSpace(Space space) {
		this.putDescription("space");
		StringBuilder sb = new StringBuilder();

		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite suite : allSuite) {
			sb.append("> ");
			sb.append(viewCards(suite, space.getCardsBySuite(suite)));
			sb.append("\n");
		}
		logger.info(new String(sb));
	}

	protected String viewCards(Suite s, List<Card> cardList) {
		StringBuilder result = new StringBuilder();

		CardFactory factory = new CardFactory();
		List<Card> suiteList = factory.getCardList(s);
		Iterator<Card> compite = suiteList.iterator();
		for (; compite.hasNext();) {
			Card tmp = compite.next();
			if (cardList.contains(tmp)) {
				result.append(tmp.toShortString());
			} else {
				result.append(Card.EMPTY);
			}
		}

		return new String(result);
	}

}
