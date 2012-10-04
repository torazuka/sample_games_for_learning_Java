package org.tigergrab.game.sevens.player;

import java.util.List;

import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.turnaction.TurnAction;

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

	/**
	 * 引数で指定したカードが手札にあれば，場に出す．手札になければ，何もしない．
	 */
	public void leadCard(Space space, Card card);

	public boolean hasCard(Card card);

	/**
	 * プレイヤーの手札を表示する．
	 */
	public void showHand();

	public int getRestHand();

	/**
	 * カードを場に出せるかを返す．出せないときはアラートを出す．
	 * 
	 * @return 場に出せるならtrue
	 */
	public boolean checkSpace(Space space, Card card);

	public boolean hasRestHand();
}
