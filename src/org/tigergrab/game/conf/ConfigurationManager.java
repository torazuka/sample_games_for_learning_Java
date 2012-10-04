package org.tigergrab.game.conf;


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

}
