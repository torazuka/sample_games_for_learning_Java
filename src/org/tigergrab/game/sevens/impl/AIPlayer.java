package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.TurnAction;

/**
 * コンピュータが扮するユーザ．自動でゲームをプレイする．
 */
public class AIPlayer extends DefaultPlayer implements Player {

	private static Logger logger = LoggerFactory.getLogger(AIPlayer.class);

	public AIPlayer(int i) {
		super(i);
	}

	public AIPlayer(int i, Logger logger) {
		this(i);
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
			view.putInteraction("出せる札がありません。");
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
			int max = limit.getMax();
			int min = limit.getMin();
			if (max != Rank.MAX) {
				Card card = new Card(suite, max + 1);
				result.add(card);
			}
			if (min != Rank.MIN) {
				Card card = new Card(suite, min - 1);
				result.add(card);
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
			int max = 0;
			int min = 0;
			if (list != null && 0 < list.size()) {
				for (Card card : list) {
					if (card == null) {
						// そのランクの札が場にまだ出ていない
						continue;
					} else {
						if (max == 0 && min == 0) {
							max = card.rank.getRank();
							min = max;
						} else {
							if (card.rank.getRank() < min) {
								min = card.rank.getRank();
							}
							if (max < card.rank.getRank()) {
								max = card.rank.getRank();
							}
						}
					}
				}
				if (max != 0 || min != 0) {
					SuiteLimit limits = new SuiteLimit(min, max);
					result.put(suite, limits);
				}
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
