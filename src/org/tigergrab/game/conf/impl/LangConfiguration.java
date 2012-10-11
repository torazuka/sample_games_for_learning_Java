package org.tigergrab.game.conf.impl;

import java.io.File;
import java.util.EnumSet;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.conf.Configuration;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 言語設定の読取りと書込みを行う．
 * 
 */
public class LangConfiguration implements Configuration {

	private static final Logger logger = LoggerFactory
			.getLogger("LangConfigurationAction.class");

	/** 現在設定されている言語 */
	protected Lang language;

	/**
	 * 対話的に言語設定を行う．
	 */
	@Override
	public void execute() {
		boolean continueFlag = true;
		for (; continueFlag;) {
			boolean done = setConfigrationByInput();
			continueFlag = !done;
		}
	}

	/**
	 * ユーザ入力に応じて、言語設定を保存する．
	 * 
	 * @return 設定完了のとき，trueを返す．
	 */
	protected boolean setConfigrationByInput() {
		Properties properties = new Properties();
		if (readConfigurationFromInput()) {
			ConfigurationFileUtil util = new ConfigurationFileUtil();
			if (util.existConfigFile() == false) {
				util.createConfigFile();
			}
			util.storeFile(properties, language.name());
			return true;
		}
		return false;
	}

	/**
	 * ユーザ入力から言語設定を読み取る．
	 * 
	 * @return 正しく読み取れたとき，trueを返す．
	 */
	protected boolean readConfigurationFromInput() {
		ResourceBundle langResources = ResourceFactory
				.getConfigurationByFile(PKG.ROOT);
		logger.info("> " + langResources.getString("q.lang"));

		String input = read();
		if (0 == input.length()) {
			return false;
		}
		String lang = convertToLang(input);
		if (isValidConfiguration(lang)) {
			language = Lang.valueOf(lang);
			return true;
		}
		return false;
	}

	/**
	 * ユーザ入力を言語に変換する．
	 * 
	 * @param input
	 * @return 変換した後の値．変換不可能なときは空文字を返す．
	 */
	protected String convertToLang(String input) {
		EnumSet<Lang> langs = EnumSet.allOf(Lang.class);
		for (Lang lang : langs) {
			if (input.equals(lang.getKey())) {
				return lang.name();
			}
		}
		// bad input
		return "";
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	/**
	 * 設定ファイルから設定値を読み込む．
	 * 
	 * @return 妥当な設定値が存在したときtrueを返す．
	 */
	protected boolean readConfigurationFromFile() {
		String fileName = getConfigFileName();
		Properties properties = new Properties();
		ConfigurationFileUtil util = new ConfigurationFileUtil();
		if (util.readPropertyFile(properties, fileName)) {
			String lang = properties.getProperty("lang");
			if (lang != null && isValidConfiguration(lang)) {
				language = Lang.valueOf(lang);
				return true;
			}
		}
		setDefaultProperty(properties);
		return false;
	}

	public Lang getLanguage() {
		return language;
	}

	/**
	 * 設定ファイルに、デフォルト設定を書き込む．
	 * 
	 * @param properties
	 */
	protected void setDefaultProperty(Properties properties) {
		language = Lang.ja;
		ConfigurationFileUtil util = new ConfigurationFileUtil();
		util.storeFile(properties, language.name());
	}

	/**
	 * 設定ファイル（game.ini）のパスを作成する．
	 * 
	 * @return 設定ファイルの絶対パス
	 */
	protected String getConfigFileName() {
		String parent = new File(".").getAbsoluteFile().getParent();
		return parent + File.separator + "game.ini";
	}

	/**
	 * 文字列が言語の設定値として妥当かを返す．
	 * 
	 * @param input
	 * @return trueなら妥当
	 */
	protected boolean isValidConfiguration(String input) {
		if (input == null || 0 == input.length()) {
			return false;
		}
		EnumSet<Lang> langs = EnumSet.allOf(Lang.class);
		for (Lang lang : langs) {
			if (input.equals(lang.name())) {
				return true;
			}
		}
		return false;
	}
}
