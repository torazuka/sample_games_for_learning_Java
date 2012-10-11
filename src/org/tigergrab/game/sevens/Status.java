package org.tigergrab.game.sevens;

import java.util.List;

import org.tigergrab.game.sevens.player.Player;

/**
 * ゲームの状態を表わす．
 * 
 */
public interface Status {

	/** プレイヤーのリストを作成する */
	public void createPlayers(int numPlayer);

	/** 手札を初期化する */
	public void initHands();

	/** 単一のプレイヤーを生存リストに設定する */
	public void setLivePlayer(Player player);

	/** プレイヤーを生存リストから勝利リストへ移動する */
	public void moveToGainer(Player player);

	/** プレイヤーを生存リストから脱落リストへ移動する */
	public void moveToLoser(Player player);

	/** 現在のプレイヤーの次のターンのプレイヤーを返す */
	public Player getNextPlayer(Player currentPlayer);

	/** ゲーム終了かどうかを判定する */
	public boolean isGameOver();

	/** 生存リストを返す */
	public List<Player> getPlayers();

	/** 生存リストの人数を返す */
	public int getPlayersNum();

	/** プレイヤーをゲーム結果順位の昇順で返す */
	public List<Player> getPlayersRank();

	public void viewGameResult();

	public void playback();

	public void registerRecord(Turn turn);
}
