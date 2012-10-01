package org.tigergrab.game.sevens.player.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.tigergrab.game.conf.impl.LangConfigurationAction;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.CardFactory;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.TurnAction;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 人間のユーザを表わす．ユーザに対話的なプレイ操作を提供する．
 */
public class HumanPlayer extends DefaultPlayer implements Player {

	ResourceBundle resources;

	public HumanPlayer(int i) {
		super(i);

		LangConfigurationAction action = new LangConfigurationAction(PKG.SEVENS);
		resources = action.getResourceBundle();
	}

	@Override
	public String getScreenName() {
		return resources.getString("you");
	}

	protected void viewUsesForLead() {
		view.putInteraction("info.leadcard");
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	@Override
	public TurnAction decide(final Space space, final Status status) {

		viewUsesForLead();

		Map<String, Card> map = new HashMap<>();
		CardFactory factory = new CardFactory();
		List<Card> allCard = factory.getAllCard();
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
					view.putAlert("alert.inhand");
					continue;
				}

				// 場に出せるかを確認する
				if (space.canLead(card) == false) {
					view.putAlert("alert.validcard");
					continue;
				}

				// 最終確認
				view.putInteraction("q.leadconfirm", card.toShortString());
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
		view.putDescription("hand", getScreenName());
		if (hand.show() == false) {
			view.putDescription("nohand");
		}
	}
}
