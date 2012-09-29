package org.tigergrab.game.conf;

import java.util.ResourceBundle;

public interface ConfigurationManager {

	/**
	 * 設定メニューを実行する．
	 */
	public void execute();

	/**
	 * 設定ファイルが存在しなければ作成する．
	 */
	public void createConfigFile();

	/**
	 * 設定ファイルがカレントディレクトリに存在するかを返す．
	 * 
	 * @return 存在するときtrue
	 */
	public boolean existConfigFile();

	/**
	 * 設定ファイル（game.ini）のパスを作成する．
	 * 
	 * @return 設定ファイルの絶対パス
	 */
	public String getConfigFileName();

	/**
	 * 設定項目をListに追加する．
	 * 
	 * @param action
	 */
	public void addConfigurationAction(ConfigurationAction action);

	/**
	 * 設定ファイルを参照して、言語設定に基づくResourceBundleを返す．
	 * 
	 * @return ResourceBundle 言語設定が存在しない場合は、デフォルトのResourceBundleを返す．
	 */
	public ResourceBundle getResource();

	/**
	 * メニュー表示用のResourceBundleを返す．
	 * 
	 * @return
	 */
	public ResourceBundle getMenuResource();
}
