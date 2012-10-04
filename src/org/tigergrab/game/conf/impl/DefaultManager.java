package org.tigergrab.game.conf.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.tigergrab.game.conf.ConfigurationAction;
import org.tigergrab.game.conf.ConfigurationManager;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * ゲーム全体の設定情報を管理します．
 */
public class DefaultManager implements ConfigurationManager {

	ResourceBundle resources;
	List<ConfigurationAction> actionList;

	public DefaultManager() {
		actionList = new ArrayList<>();
	}

	@Override
	public void createConfigFile() {
		InputOutputUtil.createNewFile(getConfigFileName());
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
		actionList.add(action);
	}

	@Override
	public void execute() {
		for (ConfigurationAction action : actionList) {
			action.execute();
		}
	}
}
