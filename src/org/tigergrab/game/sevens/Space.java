package org.tigergrab.game.sevens;

import java.util.List;

import org.tigergrab.game.sevens.impl.Card;
import org.tigergrab.game.sevens.impl.Suite;

public interface Space {

	public void viewCurrentSpace();

	public void putCard(Card card);

	public Card searchCard(Card card);

	public List<Card> getCardsBySuite(Suite suite);

	/**
	 * 引数の札を場に出せるかどうかを返す
	 * 
	 * @param card
	 * @return trueなら場に出せる
	 */
	public boolean canLead(Card card);

	public void setCards(List<Card> list, Suite suite);

}
