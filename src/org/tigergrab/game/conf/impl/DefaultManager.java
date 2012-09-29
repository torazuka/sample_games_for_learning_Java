package org.tigergrab.game.conf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.conf.ConfigurationAction;
import org.tigergrab.game.conf.ConfigurationManager;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * ゲーム全体の設定情報を管理します．
 */
public class DefaultManager implements ConfigurationManager {

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultManager.class);
	ResourceBundle resources;
	List<ConfigurationAction> actionList;

	public DefaultManager() {
		actionList = new ArrayList<>();
	}

	@Override
	public void createConfigFile() {
		InputOutputUtil.createFile(getConfigFileName());
	}

	@Override
	public boolean existConfigFile() {
		File file = new File(getConfigFileName());
		return file.exists();
	}

	@Override
	public String getConfigFileName() {
		String parent = new File(".").getAbsoluteFile().getParent();
		return parent + File.separator + "game.ini";
	}

	@Override
	public void addConfigurationAction(ConfigurationAction action) {
		if (action != null) {
			actionList.add(action);
		}
	}

	@Override
	public void execute() {
		for (ConfigurationAction action : actionList) {
			action.execute();
		}
	}

	@Override
	public ResourceBundle getResource() {
		LangConfigurationAction langAction = null;
		for (ConfigurationAction action : actionList) {
			if (action instanceof LangConfigurationAction) {
				langAction = (LangConfigurationAction) action;
			}
		}
		return langAction.getResourceBundle();
	}

	@Override
	public ResourceBundle getMenuResource() {
		LangConfigurationAction langAction = null;
		for (ConfigurationAction action : actionList) {
			if (action instanceof LangConfigurationAction) {
				langAction = (LangConfigurationAction) action;
			}
		}
		return langAction.getResourceBundle();
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
	protected Properties readProperties(Properties properties, String fileName) {
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
}
