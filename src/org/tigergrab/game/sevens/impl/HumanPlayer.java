package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.TurnAction;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 人間のユーザを表わす．ユーザに対話的なプレイ操作を提供する．
 */
public class HumanPlayer extends DefaultPlayer implements Player {

	public HumanPlayer(int i) {
		super(i);
	}

	@Override
	public String getScreenName() {
		return "あなた";
	}

	protected void viewUsesForLead() {
		view.putResourceInteraction("info.leadcard");
	}

	protected List<Card> getAllCard() {
		List<Card> result = new ArrayList<>();
		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite s : allSuite) {
			for (int i = Rank.MIN; i <= Rank.MAX; i++) {
				Card c = new Card(s, i);
				result.add(c);
			}
		}
		return result;
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	@Override
	public TurnAction decide(final Space space, final Status status) {

		viewUsesForLead();

		Map<String, Card> map = new HashMap<>();
		List<Card> allCard = getAllCard();
		for (Card c : allCard) {
			map.put(c.toMiniString(), c);
		}

		for (;;) {
			String str = read();

			// passの場合
			if (str != null && str.equals("pass")) {
				return new TurnAction() {
					@Override
					public void execute() {
						pass(status);
					}
				};
			}

			Card card = map.get(str);
			if (card != null) {

				// 手札に存在するかを確認する
				if (this.hasCard(card) == false) {
					view.putAlert("手札にあるカードを選んでください。");
					continue;
				}

				// 場に出せるかを確認する
				if (space.canLead(card) == false) {
					view.putAlert("場札と隣り合ったRank、同じSuiteの札だけを出せます。");
					continue;
				}

				// 最終確認
				view.putInteraction("{}を場に出しますか？ （n: 考え直す, それ以外: 出す）",
						card.toShortString());
				if (this.confirm()) {
					// カードを出せる
					final Card leadedCard = card;
					return new TurnAction() {
						@Override
						public void execute() {
							leadCard(space, status, leadedCard);
						}
					};
				} else {
					// 確認で「n」を入力した場合
					viewUsesForLead();
					continue;
				}
			} else {
				viewUsesForLead();
				continue;
			}
		}

	}

	protected boolean confirm() {
		boolean result = false;
		String confirm = read();

		// n（前後の空白がある場合も含む）以外のときは、一律true
		if ((confirm != null && confirm.trim().equals("n")) == false) {
			result = true;
		}
		return result;
	}

	@Override
	public void showHand() {
		view.putDescription("{}の手札: ", getScreenName());
		if (hand.show() == false) {
			view.putDescription(" もうありません。");
		}
	}
}
