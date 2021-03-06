package org.tigergrab.game.sevens.player.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.impl.DefaultTurn;
import org.tigergrab.game.sevens.impl.DefaultView;
import org.tigergrab.game.sevens.impl.Hand;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.turnaction.TurnAction;

/**
 * ゲームのプレイヤーのデフォルト実装．プレイヤー種別は，このクラスのサブクラスで表現する．
 */
public abstract class DefaultPlayer implements Player {

	private static Logger logger = LoggerFactory.getLogger(DefaultPlayer.class);

	protected int id;
	protected Hand hand;

	/** パスした回数 */
	protected int numPass;

	DefaultView view;

	public DefaultPlayer(int i) {
		id = i;
		numPass = 0;
		this.view = new DefaultView();
		hand = new Hand();
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
			view.putDescription("info.numPass", String.valueOf(numPass));
		} else {
			view.putDescription("info.retire");
			status.moveToLoser(this);
		}
	}

	@Override
	public void setHand(List<Card> cardList) {
		hand = new Hand(cardList);
	}

	/**
	 * 引数のカードが手札に含まれているかを返す．
	 * 
	 * @return trueなら含まれている．
	 */
	@Override
	public boolean hasCard(Card target) {
		return hand.has(target);
	}

	@Override
	public void leadCard(Space space, Card card) {
		if (hand.lead(card)) {
			view.putDescription("info.leadedCard", getScreenName(),
					card.toShortString());
			space.putCard(card);
		}
	}

	public int getRestHand() {
		return hand.getHandNum();
	}

	@Override
	public boolean hasRestHand() {
		return hand.hasRest();
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getScreenName() {
		return "ID " + this.getId() + " ";
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

	@Override
	public boolean checkSpace(Space space, Card card) {
		return space.canLead(card);
	}
}