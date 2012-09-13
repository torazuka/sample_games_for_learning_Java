package org.tigergrab.game.sevens;

import java.util.List;

import org.tigergrab.game.sevens.impl.Card;

/**
 * プレイヤーを表わす．実体が人間かコンピュータかを問わない．
 */
public interface Player {

	public int getId();

	public String getScreenName();

	public Turn createTurn(Space space);

	public void pass(Status status);

	public TurnAction decide(Space space, Status status);

	public void setHand(List<Card> next);

	public List<Card> getHand();

	public void leadCard(Space space, Status status, Card card);

	public boolean isInHand(Card card);

	public void showHand();
}
