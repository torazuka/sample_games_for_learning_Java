package org.tigergrab.game.sevens.player.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.impl.GameStatus;
import org.tigergrab.game.sevens.player.Player;

public class PlayerManagerTest {
	@Test
	public void testGetNextPlayer() throws Exception {
		Status status = new GameStatus();
		status.createPlayers(3);
		List<Player> livePlayers = status.getPlayers();

		assertEquals("id==0のユーザの次のユーザは、id==1。", 1,
				status.getNextPlayer(livePlayers.get(0)).getId());
		assertEquals("id==2のユーザの次のユーザは、id==0。", 0,
				status.getNextPlayer(livePlayers.get(2)).getId());

		// TODO: プレイヤーが脱落していたり勝利していたりする場合のテストケースを追加。
	}
}
