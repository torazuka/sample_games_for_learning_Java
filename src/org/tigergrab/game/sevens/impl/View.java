package org.tigergrab.game.sevens.impl;

import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.sevens.Player;
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
	 * ユーザに入力を促すメッセージを表示
	 */
	public void putInteraction(String str) {
		logger.info("> " + str);
	}

	/**
	 * ユーザに入力を促すメッセージを表示
	 */
	public void putInteraction(String str, String arg) {
		logger.info("> " + str, arg);
	}

	/**
	 * ユーザに入力を促すメッセージをリソースから表示
	 */
	public void putResourceInteraction(String str) {
		logger.info("> " + resources.getString(str));
	}

	/**
	 * ユーザへの警告を表示
	 */
	public void putAlert(String str) {
		logger.info("> ******** " + str + " ******** ");
	}

	/**
	 * ユーザへの警告を表示
	 */
	public void putResourceAlert(String str) {
		logger.info("> ******** " + resources.getString(str) + " ******** ");
	}

	/**
	 * 説明を表示
	 */
	public void putDescription(String str) {
		logger.info("> " + str);
	}

	/**
	 * 説明を表示
	 */
	public void putDescription(String str, String arg) {
		logger.info("> " + str, arg);
	}

	/**
	 * 説明を表示
	 */
	public void putDescription(String str, String arg1, String arg2) {
		logger.info("> " + str, arg1, arg2);
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
		logger.info("> 現在の場札");
		StringBuilder sb = new StringBuilder();

		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite suite : allSuite) {
			sb.append("> ");
			sb.append(viewCards(space.getCardsBySuite(suite)));
			sb.append("\n");
		}
		logger.info(new String(sb));
	}

	protected String viewCards(List<Card> cardList) {
		StringBuilder sb = new StringBuilder();
		for (Card card : cardList) {
			if (card != null) {
				sb.append(card.toShortString());
			} else {
				sb.append(Card.EMPTY);
			}
		}
		return new String(sb);
	}

	/**
	 * 1人のプレイヤーの手札を表示
	 */
	public void putHand(Player player) {
		StringBuffer sb = new StringBuffer();
		sb.append(">");

		List<Card> hand = player.getHand();
		if (hand != null && 0 < hand.size()) {
			for (Card card : hand) {
				sb.append(" " + card.toShortString());
			}
			putDescription("{}の手札: ", player.getScreenName());
		} else {
			sb.append(" ");
			sb.append(player.getScreenName());
			sb.append("の手札はもうありません。");
		}
		logger.info(new String(sb));
	}

	/**
	 * 1人のプレイヤーの手札をデバッグ表示
	 */
	public void putHandForDebug(Player player) {
		StringBuffer sb = new StringBuffer();
		sb.append(" ");
		List<Card> hand = player.getHand();
		for (Card card : hand) {
			sb.append(" " + card.toShortString());
		}
		logger.debug("{}の手札: ", player.getScreenName());
		logger.debug(new String(sb));
	}

}
