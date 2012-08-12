package org.tigergrab.game.sevens;


/**
 * 1つのターン．Gameは、複数の独立したTurnから構成される．
 */
public interface Turn {

	public void execute(Space space, Status status);

	public Player getCurrentPlayer();
}
