package org.tigergrab.game.conf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.conf.ConfigurationAction;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.util.InputOutputUtil;

public class LangConfigurationAction implements ConfigurationAction {

	private static final Logger logger = LoggerFactory
			.getLogger("LangConfigurationAction.class");

	protected String language = "";
	protected ResourceBundle langResources;
	protected PKG pkg;

	public LangConfigurationAction() {
	}

	public LangConfigurationAction(PKG pkg) {
		this.pkg = pkg;
		setConfigurationByFile();
	}

	@Override
	public void execute() {
		boolean continueFlag = true;
		for (; continueFlag;) {
			boolean done = setConfigrationByInput();
			continueFlag = !done;
		}
	}

	/**
	 * ユーザ入力に応じて言語を設定する．
	 * 
	 * @return 設定完了のとき，trueを返す．
	 */
	protected boolean setConfigrationByInput() {
		Properties properties = new Properties();
		String resourceName = ResourceFactory.getFamily(PKG.ROOT);

		if (readConfigurationFromInput()) {
			storeFile(properties);

			resourceName += ("_" + language);
			if (readPropertyFile(properties, getConfigFileName()) == null) {
				InputOutputUtil.createFile(getConfigFileName());
			}
			langResources = ResourceBundle.getBundle(resourceName);
			return true;
		}
		return false;
	}

	protected void storeFile(Properties properties) {
		properties.setProperty("lang", language);
		try {
			properties.store(new FileOutputStream(getConfigFileName()), null);
		} catch (FileNotFoundException e) {
			logger.error("ファイルが見つかりません。");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("入出力エラーです。");
		}
	}

	/**
	 * ユーザ入力から言語設定を読み取る．
	 * 
	 * @return 正しく読み取れたとき，trueを返す．
	 */
	protected boolean readConfigurationFromInput() {
		logger.info("> " + langResources.getString("q.lang"));
		String input = read();
		if (0 == input.length()) {
			return false;
		}
		String lang = convertToLang(input);
		if (isValidConfiguration(lang)) {
			language = lang;
			return true;
		}
		return false;
	}

	/**
	 * ユーザ入力を言語に変換する．
	 * 
	 * @param input
	 * @return 変換した後の値．変換不可能なときは0を返す．
	 */
	protected String convertToLang(String input) {
		if (input.equals("1")) {
			return "ja";
		} else if (input.equals("2")) {
			return "en";
		}
		// bad input
		return "0";
	}

	protected String read() {
		return InputOutputUtil.read();
	}

	/**
	 * 設定ファイルの言語設定に基づくResourceBundleを返す．
	 * 
	 * @return ResourceBundle 言語設定が存在しない場合は、デフォルトのResourceBundleを返す．
	 */
	public ResourceBundle getResourceBundle() {
		return langResources;
	}

	/**
	 * 設定ファイルから設定値を読み込む．
	 * 
	 * @return 妥当な設定値が存在したときtrueを返す．
	 */
	protected boolean readConfigurationFromFile() {
		String fileName = getConfigFileName();
		Properties properties = new Properties();
		if (readPropertyFile(properties, fileName) != null) {
			String lang = properties.getProperty("lang");
			if (lang != null && isValidConfiguration(lang)) {
				language = lang;
				return true;
			}
		}
		setDefaultProperty(properties);
		return false;
	}

	/**
	 * 設定ファイルの言語設定をインスタンスフィールドに反映する．設定が存在しない場合は，デフォルトの設定とする．
	 * 
	 */
	protected void setConfigurationByFile() {
		String resourceName = ResourceFactory.getFamily(pkg);
		if (readConfigurationFromFile()) {
			resourceName += ("_" + language);
		}
		langResources = ResourceBundle.getBundle(resourceName);
	}

	/**
	 * 設定ファイルに、デフォルト設定を書き込む．
	 * 
	 * @param properties
	 */
	protected void setDefaultProperty(Properties properties) {
		language = "ja";
		storeFile(properties);
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
	 * ファイルシステムからプロパティファイルを読み込む．
	 * 
	 * @param properties
	 *            読込んだプロパティを受け取るPropertiesオブジェクト
	 * @param fileName
	 *            ファイル名
	 * @return Propertiesオブジェクト
	 */
	protected Properties readPropertyFile(Properties properties, String fileName) {
		try {
			properties.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			properties = null;
			logger.info("設定ファイルがありません。");
		} catch (IOException e) {
			properties = null;
			logger.info("設定ファイルの読み込みエラーです。");
		}
		return properties;
	}

	protected boolean isValidConfiguration(String lang) {
		if (lang == null || lang.length() == 0) {
			return false;
		}
		if (lang.equals("ja") || lang.equals("en")) {
			return true;
		}
		return false;
	}
}
