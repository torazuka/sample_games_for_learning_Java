package org.tigergrab.game.sevens;

import org.tigergrab.game.conf.ConfigurationManager;

public interface View {
	public ConfigurationManager createConf();

	/**
	 * ユーザ入力を促すメッセージをリソースから表示する
	 */
	public void putInteraction(String str);

	/**
	 * ユーザ入力を促すメッセージをリソースから表示する（プレースホルダを1個含む)
	 */
	public void putInteraction(String str, String arg);

	/**
	 * ユーザへの警告を表示する
	 */
	public void putAlert(String str);

	/**
	 * 説明をリソースから表示する
	 */
	public void putDescription(String str);

	/**
	 * 説明をリソースから表示する（プレースホルダを1個含む）
	 */
	public void putDescription(String str, String arg);

	/**
	 * 説明をリソースから表示する（プレースホルダを2個含む）
	 */
	public void putDescription(String str, String arg1, String arg2);

	/**
	 * 場札を表示する
	 */
	public void putSpace(Space space);
}
