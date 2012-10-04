package org.tigergrab.game;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigergrab.game.conf.impl.DefaultManager;
import org.tigergrab.game.conf.impl.LangConfigurationAction;
import org.tigergrab.game.conf.impl.ResourceFactory;
import org.tigergrab.game.conf.impl.ResourceFactory.PKG;
import org.tigergrab.game.sevens.impl.Sevens;
import org.tigergrab.game.util.InputOutputUtil;

/**
 * 実行時のメニューを提供します．
 */
public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	DefaultManager conf;
	ResourceBundle resource;

	public Main() {
		conf = new DefaultManager();
		conf.addConfigurationAction(new LangConfigurationAction());

		// 設定ファイルに言語設定があれば読み込む（なければデフォルト言語でメニューを表示する）
		resource = ResourceFactory.getConfigurationByFile(PKG.ROOT);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.execute();
	}

	/**
	 * メニューを提示する．
	 */
	protected void execute() {
		boolean continueFlag = true;
		for (; continueFlag;) {
			logger.info("> " + resource.getString("menu"));
			String selected = read();
			if (isValid(selected) == false) {
				continue;
			}
			continueFlag = menuController(selected);
		}
	}

	/**
	 * ユーザが入力した値がメニューとして妥当かを検証する．
	 * 
	 * @param input
	 * @return 妥当ならtrue
	 */
	protected boolean isValid(String input) {
		if (input.length() == 0) {
			return false;
		}
		if (input.equals("1") || input.endsWith("2") || input.equals("0")) {
			return true;
		}
		return false;
	}

	/**
	 * ユーザの入力に応じてメニューを実行する．
	 * 
	 * @param input
	 */
	protected boolean menuController(String input) {
		if (input.equals("1")) {
			sevens();
			return true;
		} else if (input.equals("2")) {
			setting();
			return true;
		}
		logger.info("> " + resource.getString("quit"));
		return false;
	}

	protected void sevens() {
		Sevens sevens = new Sevens();
		sevens.execute();
	}

	protected void setting() {
		conf.execute();
		resource = ResourceFactory.getConfigurationByFile(PKG.ROOT);
	}

	protected String read() {
		return InputOutputUtil.read();
	}
}
