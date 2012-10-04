package org.tigergrab.game.sevens.player.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.tigergrab.game.conf.impl.ResourceFactory;
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
		resources = ResourceFactory.getConfigurationByFile(PKG.SEVENS);
	}

	@Override
	public String getScreenName() {
		return resources.getString("you");
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	protected Map<String, Card> getCardMapForSearch() {
		Map<String, Card> result = new HashMap<>();
		CardFactory factory = new CardFactory();
		List<Card> allCard = factory.getAllCard();
		for (Card c : allCard) {
			result.put(c.toMiniString(), c);
		}
		return result;
	}

	protected TurnAction createPassAction(final Status status) {
		return new TurnAction() {
			@Override
			public void execute() {
				pass(status);
			}
		};
	}

	/**
	 * カードが手札に存在するかを返す．存在しなければアラートを出す．
	 */
	@Override
	public boolean hasCard(Card target) {
		if (super.hasCard(target)) {
			return true;
		}
		view.putAlert("alert.inhand");
		return false;
	}

	@Override
	public boolean checkSpace(Space space, Card card) {
		if (super.checkSpace(space, card)) {
			return true;
		}
		view.putAlert("alert.validcard");
		return false;
	}

	@Override
	public TurnAction decide(final Space space, final Status status) {

		Map<String, Card> map = getCardMapForSearch();
		for (;;) {
			view.putInteraction("info.leadcard");
			String input = read();

			if ("pass".equals(input)) {
				return createPassAction(status);
			}

			Card card = map.get(input);
			if (hasCard(card) && checkSpace(space, card) && confirm()) {
				return createLeadAction(space, status, card);
			}
			continue;
		}
	}

	/**
	 * カードを引くアクションを作成して返す．
	 * 
	 * @return
	 */
	protected TurnAction createLeadAction(final Space space,
			final Status status, final Card card) {
		return new TurnAction() {
			@Override
			public void execute() {
				leadCard(space, status, card);
			}
		};
	}

	/**
	 * カードを出す最終確認を行う．
	 * 
	 * @return 出せるときはtrueを返す
	 */
	protected boolean confirm() {
		boolean result = false;
		view.putInteraction("q.leadconfirm");

		String confirm = read();
		// n（前後の空白がある場合も含む）以外のときは、一律true
		if ((confirm.trim().equals("n")) == false) {
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
