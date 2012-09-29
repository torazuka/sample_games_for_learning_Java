package org.tigergrab.game.sevens.player.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.playingcards.impl.SuiteLimit;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.TurnAction;
import org.tigergrab.game.sevens.impl.DefaultView;
import org.tigergrab.game.sevens.player.Player;

/**
 * コンピュータが扮するユーザ．自動でゲームをプレイする．
 */
public class AIPlayer extends DefaultPlayer implements Player {

	private static Logger logger = LoggerFactory.getLogger(AIPlayer.class);

	public AIPlayer(DefaultView view, int i) {
		super(view, i);
	}

	public AIPlayer(DefaultView view, int i, Logger logger) {
		this(view, i);
		this.logger = logger;
	}

	@Override
	public TurnAction decide(final Space space, final Status status) {

		// 手札のうち場に出せる札を取得
		List<Card> leadableCards = getLeadableCards(space);

		if (leadableCards != null && 0 < leadableCards.size()) {

			// ひとまず: 1枚以上ある場合は、リストの先頭のカードを出す
			final Card tmpCard = leadableCards.get(0);

			return new TurnAction() {
				@Override
				public void execute() {
					leadCard(space, status, tmpCard);
				}
			};

		} else {
			// 1枚もない場合は、passを選択する/passできない時はリタイアになる
			return new TurnAction() {
				@Override
				public void execute() {
					pass(status);
				}
			};
		}
	}

	/**
	 * 場に出せる札とプレイヤーの手札を比べて、実際に出せる札のリストを得る。
	 * 
	 * @param game
	 * @return　出せる札がなければサイズ0のリストを返す（ただし、起こりえない）
	 */
	public List<Card> getLeadableCards(Space space) {
		List<Card> result = new ArrayList<Card>();

		List<Card> nextCards = getNextCards(space);
		if (nextCards == null || nextCards.size() == 0) {
			view.putDescription("info.nohand");
		} else {
			for (Card next : nextCards) {
				if (hand.has(next)) {
					result.add(next);
				}
			}
		}
		return result;
	}

	/**
	 * @return 次に出す札の候補をリストで返す．
	 */
	public List<Card> getNextCards(Space space) {
		List<Card> result = new ArrayList<Card>();

		Map<Suite, SuiteLimit> suiteLimit = getSuiteLimit(space);
		Set<Suite> keySet = suiteLimit.keySet();
		for (Suite suite : keySet) {
			SuiteLimit limit = suiteLimit.get(suite);
			Card maxCard = limit.getMax();
			Card minCard = limit.getMin();

			Card tmpMax = maxCard.getNextBig();
			if (tmpMax != null) {
				result.add(tmpMax);
			}
			Card tmpMin = minCard.getNextSmall();
			if (tmpMin != null) {
				result.add(tmpMin);
			}
		}
		return result;
	}

	/**
	 * @return SuiteをキーとするMapで、場札のランクの上端と下端を返す．
	 */
	protected Map<Suite, SuiteLimit> getSuiteLimit(Space space) {
		Map<Suite, SuiteLimit> result = new HashMap<Suite, SuiteLimit>();

		EnumSet<Suite> allSuites = EnumSet.allOf(Suite.class);
		for (Suite suite : allSuites) {
			List<Card> list = space.getCardsBySuite(suite);
			if (list != null && 0 < list.size()) {
				SuiteLimit sl = new SuiteLimit(list);
				result.put(suite, sl);
			}
		}
		return result;
	}

	@Override
	public void showHand() {
		// 手札をユーザに見せない
		logger.debug("{}の手札: ", getScreenName());
		if (hand.show() == false) {
			logger.debug(" もうありません。");
		}
	}

}
