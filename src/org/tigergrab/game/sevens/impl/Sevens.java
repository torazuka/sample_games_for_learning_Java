package org.tigergrab.game.sevens.impl;

import org.tigergrab.game.sevens.GameEvent;
import org.tigergrab.game.sevens.GameEventListener;
import org.tigergrab.game.sevens.Player;
import org.tigergrab.game.sevens.Turn;

/**
 * 七並べを開始するmainメソッドを持つ．
 * 
 */
public class Sevens {

	public static void main(String[] args) {
		Game game = new Game();

		game.addListener(new GameEventListener() {

			@Override
			public void beginTurn(GameEvent event) {
				Game game = event.getGame();
				Turn turn = event.getTurn();

				// プレイヤーがコンピュータの場合は、手札をユーザに見せない
				Player currentPlayer = turn.getCurrentPlayer();
				if (currentPlayer instanceof AIPlayer) {
					game.view.putHandForDebug(currentPlayer);
				} else {
					game.view.putHand(currentPlayer);
				}
			}

			@Override
			public void endTurn(GameEvent event) {
				Game game = event.getGame();
				Turn turn = event.getTurn();

				// プレイヤーがコンピュータの場合だけ、ドローポーズを設定する
				Player currentPlayer = turn.getCurrentPlayer();
				if (currentPlayer instanceof AIPlayer) {
					game.view.putDescription("（Enterを入力すると進みます）");
					game.read();
				}
			}

			@Override
			public void viewMessage(GameEvent event) {
				// TODO Auto-generated method stub

			}
		});
		game.execute();
	}
}
