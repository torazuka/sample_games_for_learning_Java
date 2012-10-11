package org.tigergrab.game.sevens.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.tigergrab.game.sevens.Status;
import org.tigergrab.game.sevens.Turn;
import org.tigergrab.game.sevens.player.Player;

public class GameMockForReplay extends Game {

	@Override
	protected Status createStatus(DefaultView view) {
		return new StatusMock();
	}

	@Override
	protected String read() {
		return "y";
	}
}

class StatusMock implements Status {

	@Override
	public void createPlayers(int numPlayer) {
	}

	@Override
	public void initHands() {
	}

	@Override
	public void setLivePlayer(Player player) {
	}

	@Override
	public void moveToGainer(Player player) {
	}

	@Override
	public void moveToLoser(Player player) {
	}

	@Override
	public Player getNextPlayer(Player currentPlayer) {
		return null;
	}

	@Override
	public boolean isGameOver() {
		return false;
	}

	@Override
	public List<Player> getPlayers() {
		return null;
	}

	@Override
	public int getPlayersNum() {
		return 0;
	}

	@Override
	public List<Player> getPlayersRank() {
		return null;
	}

	@Override
	public void viewGameResult() {

	}

	@Override
	public void playback() {
		assertEquals("ここに到達すれば成功。", 1, 1);
	}

	@Override
	public void registerRecord(Turn turn) {
	}

}
