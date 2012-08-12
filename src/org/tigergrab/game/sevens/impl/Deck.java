package org.tigergrab.game.sevens.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 山札と，山札に関する操作を表わす． 七並べではゲーム開始時点にだけ山札が存在するため、このクラスはゲーム開始時に行う処理を持つ．
 */
public class Deck {

	private static Logger logger = LoggerFactory.getLogger(Deck.class);

	List<Card> cards;

	public Deck() {
		cards = new ArrayList<Card>(Card.CARD_MAX);

		EnumSet<Suite> allSuite = EnumSet.allOf(Suite.class);
		for (Suite s : allSuite) {
			for (int i = 0; i < Card.RANK_MAX; i++) {
				cards.add(new Card(s, i + 1));
			}
		}
	}

	/**
	 * 指定した人数に、山札を均一に配る。割り切れない場合は、一部のプレイヤーの枚数が、1枚多く（少なく）なってもよい。
	 * 戻り値の要素数（カードリスト）は、プレイヤー人数と等しくなる。
	 * 
	 * @param numPlayer
	 *            プレイヤーの数
	 * @return
	 */
	public List<List<Card>> initDeal(int numPlayer) {

		if (numPlayer < 1) {
			logger.error("0以下のプレイヤー数が与えられた。: {}", numPlayer);
			throw new IllegalArgumentException();
		}

		List<List<Card>> result = new ArrayList<List<Card>>(numPlayer);
		for (int i = 0; i < numPlayer; i++) {
			List<Card> cardList = new ArrayList<Card>();
			result.add(cardList);
		}

		// 山札は1枚配布するごとに減っていくため、現在枚数ではなく、初期枚数で繰り返し回数を固定する
		int counter = cards.size();
		for (int i = 0; i < counter; i++) {
			// 残りの山札を母数とするランダムなindexを取得
			int randomIndex = getRandomIndex(cards.size());

			// どのプレイヤーに配るかを決めて、1枚配る
			int playerIndex = getPlayerIndex(numPlayer, i);
			List<Card> tmpList = result.get(playerIndex);
			Card oneCard = cards.remove(randomIndex);
			tmpList.add(oneCard);

			result.set(playerIndex, tmpList);
		}
		return result;
	}

	/**
	 * @param 0以上maxNum未満のランダムな値を返す
	 * @return
	 */
	protected int getRandomIndex(int maxNum) {
		int result = -1;
		Random random = getRandom();
		result = random.nextInt(maxNum);
		return result;
	}

	/**
	 * テストのためにランダム生成部分を外出し。
	 * 
	 * @return
	 */
	protected Random getRandom() {
		return new Random();
	}

	/**
	 * どのプレイヤーの手札かをプレイヤーindexで返す
	 */
	protected int getPlayerIndex(int numPlayer, int cardIndex) {
		// 2人なら26+26枚、3人なら18+17+17枚、4人なら13+13+13+13枚...となるようにする。
		return cardIndex % numPlayer;
	}

}
