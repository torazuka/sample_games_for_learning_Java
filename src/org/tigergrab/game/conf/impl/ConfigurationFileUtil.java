package org.tigergrab.game.conf.impl;

import java.io.File;

import org.tigergrab.game.util.InputOutputUtil;

public class ConfigurationFileUtil {
	/**
	 * 設定ファイルを新規に作成する．
	 */
	public void createConfigFile() {
		InputOutputUtil.createNewFile(getConfigFilePath());
	}

	/**
	 * 設定ファイルがカレントディレクトリに存在するかを返す．
	 * 
	 * @return 存在するときtrue
	 */
	public boolean existConfigFile() {
		File file = new File(getConfigFilePath());
		return file.exists();
	}

	/**
	 * 設定ファイル（game.ini）のデフォルトのパスを返す．
	 * 
	 * @return 設定ファイルの絶対パス
	 */
	public String getConfigFilePath() {
		String parent = new File(".").getAbsoluteFile().getParent();
		return parent + File.separator + "game.ini";
	}

}
