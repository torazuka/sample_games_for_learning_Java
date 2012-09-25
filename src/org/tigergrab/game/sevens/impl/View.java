package org.tigergrab.game.sevens.impl;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.CardFactory;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;

/**
 * 情報の出力を扱う．ユーザに見せるためにコンソールに出したり，ログとしてファイルに出したりする．
 */
public class View {

	private Logger logger;

	ResourceBundle resources;

	public View() {
		logger = LoggerFactory.getLogger(View.class);
		resources = ResourceBundle
				.getBundle("org.tigergrab.game.sevens.resources");
	}

	public View(Logger log) {
		logger = log;
		resources = ResourceBundle
				.getBundle("org.tigergrab.game.sevens.resources");
	}

	/**
	 * ユーザに入力を促すメッセージをリソースから表示
	 */
	public void putResourceInteraction(String str) {
		logger.info("> " + resources.getString(str));
	}

	/**
	 * ユーザに入力を促すメッセージをリソースから表示
	 */
	public void putResourceInteraction(String str, String arg) {
		logger.info("> " + resources.getString(str), arg);
	}

	/**
	 * ユーザへの警告を表示
	 */
	public void putResourceAlert(String str) {
		logger.info("> ******** " + resources.getString(str) + " ******** ");
	}

	/**
	 * 説明をリソースから表示
	 */
	public void putResourceDescription(String str) {
		logger.info("> " + resources.getString(str));
	}

	/**
	 * 説明をリソースから表示
	 */
	public void putResourceDescription(String str, String arg) {
		logger.info("> " + resources.getString(str), arg);
	}

	/**
	 * 説明をリソースから表示
	 */
	public void putResourceDescription(String str, String arg1, String arg2) {
		logger.info("> " + resources.getString(str), arg1, arg2);
	}

	/**
	 * 場札を表示する
	 */
	public void putSpace(Space space) {
		this.putResourceDescription("space");
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
