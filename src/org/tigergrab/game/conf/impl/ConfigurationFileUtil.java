package org.tigergrab.game.conf.impl;

import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.util.InputOutputUtil;

public class ConfigurationFileUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(ConfigurationFileUtil.class);

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

	protected void storeFile(Properties properties, String str) {
		properties.setProperty("lang", str);
		try {
			properties.store(new FileOutputStream(getConfigFilePath()), null);
		} catch (FileNotFoundException e) {
			logger.error("ファイルが見つかりません。");
		} catch (IOException e) {
			logger.error("入出力エラーです。");
		}
	}

	/**
	 * ファイルシステムからプロパティファイルを読み込む．
	 * 
	 * @param properties
	 *            読込んだプロパティを受け取るPropertiesオブジェクト
	 * @param fileName
	 *            ファイル名
	 * @return 読み込めたらtrue
	 */
	protected boolean readPropertyFile(Properties properties, String fileName) {
		boolean result = false;
		try {
			properties.load(new FileInputStream(fileName));
			result = true;
		} catch (FileNotFoundException e) {
			logger.info("設定ファイルがありません。");
		} catch (IOException e) {
			logger.info("設定ファイルの読み込みエラーです。");
		}
		return result;
	}
}
