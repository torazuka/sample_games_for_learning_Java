package org.tigergrab.game.conf.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
}
