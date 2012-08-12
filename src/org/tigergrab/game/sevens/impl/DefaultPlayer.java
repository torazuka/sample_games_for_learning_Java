package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.TurnAction;

/**
 * ゲームのプレイヤーのデフォルト実装．プレイヤー種別は，このクラスのサブクラスで表現する．
 */
public abstract class DefaultPlayer implements Player {

	int id;

	/** パスした回数 */
	protected int numPass;

	/** プレイヤーの手札 */
	List<Card> hand;

	View view;

	public DefaultPlayer(int i) {
		id = i;
		numPass = 0;
		view = new View();
		hand = new ArrayList<Card>();
	}

	@Override
	public Turn createTurn(Space space) {
		Turn turn = new DefaultTurn(space, this);
		return turn;
	}

	@Override
	public abstract TurnAction decide(final Space space, final Status status);

	@Override
	public void pass(Status status) {
		if (numPass++ < 3) {
			view.putResourceDescription("tell.numPass", String.valueOf(numPass));
		} else {
			view.putResourceDescription("tell.retire");
			status.moveToDead(this);
		}
	}

	@Override
	public void setHand(List<Card> cardList) {
		if (hand == null) {
			hand = new ArrayList<Card>();
		}

		if (cardList != null) {
			for (Card card : cardList) {
				hand.add(card);
			}
		}
	}

	/**
	 * 引数のカードが手札に含まれているかを返す．
	 * 
	 * @return trueなら含まれている．
	 */
	@Override
	public boolean isInHand(Card target) {
		boolean result = false;
		if (hand != null && 0 < hand.size()) {
			for (Card card : hand) {
				if (card.equals(target)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void leadCard(Space space, Status status, Card card) {

		if (hand.remove(card)) {
			view.putResourceDescription("tell.leadedCard", getScreenName(),
					card.toShortString());
			space.putCard(card);
		} else {
			// 手札にターゲットの札がないときは何もしない。
		}

		// 手札がなくなった場合、プレイヤーを勝利者リストに移動
		if (this.getHand().size() == 0) {
			status.moveToWinner(this);
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public List<Card> getHand() {
		return hand;
	}

	@Override
	public String getScreenName() {
		return "プレイヤーID " + this.getId() + " さん";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Player) {
			Player p = (Player) obj;
			result = this.id == p.getId();
		}
		return result;
	}
}