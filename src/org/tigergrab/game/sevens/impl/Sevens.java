package org.tigergrab.game.sevens.impl;

import org.tigergrab.game.sevens.GameEvent;
import org.tigergrab.game.sevens.GameEventListener;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;
import org.tigergrab.game.sevens.player.impl.AIPlayer;

/**
 * 七並べを開始するmainメソッドを持つ．
 * 
 */
public class Sevens {

	public static void main(String[] args) {
		Game game = new Game();
		game.addListener(getTurnListener());
		boolean isContinue = game.execute();
		for (; isContinue;) {
			isContinue = game.execute();
		}
	}

	protected static GameEventListener getTurnListener() {
		return new GameEventListener() {

			@Override
			public void beginTurn(GameEvent event) {
				Turn turn = event.getTurn();
				Player currentPlayer = turn.getCurrentPlayer();
				currentPlayer.showHand();
			}

			@Override
			public void endTurn(GameEvent event) {
				Game game = event.getGame();
				Turn turn = event.getTurn();

				// プレイヤーがコンピュータの場合だけ、ドローポーズを設定する
				Player currentPlayer = turn.getCurrentPlayer();
				if (currentPlayer instanceof AIPlayer) {
					game.view.putResourceInteraction("info.drawpause");
					game.read();
				}
			}

			@Override
			public void viewMessage(GameEvent event) {
				// TODO Auto-generated method stub

			}
		};
	}
}
