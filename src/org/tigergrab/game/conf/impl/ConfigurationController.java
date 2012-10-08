package org.tigergrab.game.conf.impl;

import java.util.ArrayList;
import java.util.List;

import org.tigergrab.game.conf.Configuration;
import org.tigergrab.game.conf.Controller;

/**
 * ゲーム全体の設定情報を管理する．
 */
public class ConfigurationController implements Controller {

	List<Configuration> actionList;

	public ConfigurationController() {
		actionList = new ArrayList<>();
	}

	/**
	 * 設定項目をListに追加する．
	 * 
	 * @param action
	 */
	public void append(Configuration action) {
		actionList.add(action);
	}

	@Override
	public void execute() {
		for (Configuration action : actionList) {
			action.execute();
		}
	}
}
