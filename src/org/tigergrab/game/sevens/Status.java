package org.tigergrab.game.sevens;

import java.util.List;

public interface Status {

	/** プレイヤーのリストを作成する */
	public void createPlayers(int numPlayer);

	/** 手札を初期化する */
	public void initHands();

	/** 単一のプレイヤーを生存リストに設定する */
	public void setLivePlayer(Player player);

	/** 複数のプレイヤーを生存リストに設定する */
	public void setLivePlayers(List<Player> players);

	/** プレイヤーを生存リストから勝利リストへ移動する */
	public void moveToWinner(Player player);

	/** プレイヤーを生存リストから脱落リストへ移動する */
	public void moveToDead(Player player);

	/** ゲーム終了かどうかを判定する */
	public boolean isGameOver();

	/** 生存リストを返す */
	public List<Player> getLivePlayers();

	/** idで指定した生存リストのプレイヤーを返す */
	public Player getLivePlayer(int id);

	/** 生存リストの人数を返す */
	public int getLivePlayersNum();

	/** 脱落リストを返す */
	public List<Player> getDeadPlayers();

	/** プレイヤーをゲーム結果順位の昇順で返す */
	public List<Player> getPlayersRank();

	/** 脱落リストの人数を返す */
	public int getDeadPlayersNum();

	/** 勝利リストを返す */
	public List<Player> getWinners();

	/** 勝利リストの人数を返す */
	public int getWinnersNum();

	public void viewGameResult();

	public void playback();

	public void registerRecord(Turn turn);
}
