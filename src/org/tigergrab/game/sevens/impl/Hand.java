package org.tigergrab.game.sevens.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.playingcards.impl.Card;

/**
 * プレイヤーの手札
 * 
 */
public class Hand {

	private static Logger logger = LoggerFactory.getLogger(Hand.class);

	List<Card> hand;

	public Hand() {
		hand = new ArrayList<Card>();
	}

	public Hand(List<Card> cardList) {
		this();
		if (cardList != null) {
			setCards(cardList);
		}
	}

	protected void setCards(List<Card> cardList) {
		for (Card card : cardList) {
			hand.add(card);
		}
	}

	public boolean lead(Card card) {
		if (has(card)) {
			hand.remove(card);
			return true;
		}
		return false;
	}

	/**
	 * 引数のカードが手札に含まれているかを返す．
	 * 
	 * @return trueなら含まれている．
	 */
	public boolean has(Card target) {
		if (hand != null && 0 < hand.size()) {
			return hand.contains(target);
		}
		return false;
	}

	/**
	 * 手札に残りがあるかを返す．
	 * 
	 * @return trueなら1枚以上残っている．
	 */
	public boolean hasRest() {
		if (hand != null && 0 < hand.size()) {
			return true;
		}
		return false;
	}

	/**
	 * 手札を見せる
	 * 
	 * @return trueなら1枚以上の手札を見せた．
	 */
	public boolean show() {
		if (this.hasRest()) {
			StringBuilder sb = new StringBuilder();
			sb.append(">");
			concat(sb);
			logger.info(new String(sb));
			return true;
		}
		return false;
	}

	protected void concat(StringBuilder sb) {
		for (Card card : hand) {
			sb.append(" " + card.toShortString());
		}
	}

	public int getHandNum() {
		if (hand != null) {
			return hand.size();
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hand == null) ? 0 : hand.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Hand other = (Hand) obj;
		if (hand == null && other.hand != null) {
			return false;
		} else if (hand.equals(other.hand) == false) {
			return false;
		}
		return true;
	}

}
