package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.tigergrab.game.conf.impl.ResourceFactory;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.playingcards.impl.Card;
import org.tigergrab.game.playingcards.impl.Suite;
import org.tigergrab.game.sevens.Space;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.impl.DefaultSpace;
import org.tigergrab.game.sevens.impl.DefaultView;
import org.tigergrab.game.sevens.impl.GameStatus;

public class HumanPlayerTest {

	static ResourceBundle resource;
	static DefaultView view;

	@BeforeClass
	public static void beforeClass() {
		resource = ResourceFactory.getConfigurationByFile(PKG.SEVENS);
		view = new DefaultView();
	}

	@Test
	public void testConfirm() throws Exception {
		HumanPlayer player0 = new HumanPlayer(0) {
			@Override
			protected String read() {
				return "n";
			}
		};
		assertEquals("「n」が入力されたとき、false.", false, player0.confirm());

		HumanPlayer player1 = new HumanPlayer(0) {
			@Override
			protected String read() {
				return "";
			}
		};
		assertEquals("何も入力されなかったとき、true.", true, player1.confirm());

		HumanPlayer player2 = new HumanPlayer(0) {
			@Override
			protected String read() {
				return "hoge";
			}
		};
		assertEquals("「n」以外の文字列が入力されたとき、true.", true, player2.confirm());

		HumanPlayer player3 = new HumanPlayer(0) {
			@Override
			protected String read() {
				return " n ";
			}
		};
		assertEquals("「n」の前後に空白が入力されたとき、false.", false, player3.confirm());
	}

	@Test
	public void testGetScreenName() throws Exception {
		HumanPlayer player = new HumanPlayer(0);
		assertEquals("HumanPlayerの表示名は「あなた」", resource.getString("you"),
				player.getScreenName());
	}

	@Test
	public void testGetCardMapForSearch() throws Exception {
		HumanPlayer player = new HumanPlayer(0);
		Map<String, Card> map = player.getCardMapForSearch();
		assertEquals("要素は全部で52個。", 52, map.size());
	}

	@Test
	public void testShowHand() throws Exception {
		HumanPlayer player = new HumanPlayer(0);
		List<Card> cardList = new ArrayList<>();
		cardList.add(new Card(Suite.Heart, 7));
		player.setHand(cardList);

		Logger logger = mock(Logger.class);
		player.view = new DefaultView(logger);
		player.showHand();
		verify(logger).info("> {}の手札: ", "あなた");

		HumanPlayer player1 = new HumanPlayer(0);
		List<Card> cardList1 = new ArrayList<>();
		player1.setHand(cardList1);

		Logger logger1 = mock(Logger.class);
		player1.view = new DefaultView(logger1);
		player1.showHand();
		verify(logger).info("> {}の手札: ", "あなた");
	}

	@Test
	public void testDecide() throws Exception {
		HumanPlayer player = new HumanPlayerMockForDecide(0);
		Space space = new DefaultSpace();
		Status status = new GameStatus(new DefaultView());
		player.decide(space, status);
	}

	@Test
	public void testDecidePass() throws Exception {
		HumanPlayer player = new HumanPlayerMockForDecidePass(0);
		Space space = new DefaultSpace();
		Status status = new GameStatus(new DefaultView());
		player.decide(space, status);
	}

	@Test
	public void testLeadCard() throws Exception {
		// Game game = new Game();
		//
		// Player currentPlayer = new HumanPlayer(0) {
		// protected String read() {
		// return "pass";
		// };
		// };
		// currentPlayer.pass(); // パス1回目
		// currentPlayer.pass(); // パス2回目
		// Card leadCard = currentPlayer.leadCard(game, currentPlayer);
		// assertEquals("passを入力して、pass可能だったとき、戻り値がnullになる。", null, leadCard);
		// Turn turn = new UserTurn(currentPlayer);
		// assertEquals("パス回数は2回。", 2, currentPlayer.getPassNum());
		// assertEquals("2回パスした場合、パス可能。", true, currentPlayer.canPass());
		//
		// currentPlayer.pass(); // パス3回目
		// leadCard = currentPlayer.leadCard(game, currentPlayer);
		// assertEquals("passを入力して、pass不可能だったとき、戻り値がnullになる。", null, leadCard);
		// assertEquals("パス回数は3回。", 3, currentPlayer.getPassNum());
		// assertEquals("3回パスした場合、これ以上のパスは不可能。", false,
		// currentPlayer.canPass());

		// 「null」という文字列を入力したとき
		// 何も入力せずにEnterを押したとき

		// 変換できない入力をしたとき

		// 手札に含まれていて、場に出せるカードを選んだとき
		// 手札に含まれないが、場に出せるカードを選んだとき
		// 手札に含まれるが、場に出せないカードを選んだとき
		// 手札に含まれなくて、場に出せないカードを選んだとき

		// 確認でYesを選んだとき
		// 確認でNoを選んだとき
		// 確認でYesでもNoでもない入力をしたとき
		// 確認で何も入力しなかったとき // TODO 何も選ばないときはYesにすべきかも。要検討。

	}
}
