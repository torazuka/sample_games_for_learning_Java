package org.tigergrab.game.conf.impl;

import java.util.ResourceBundle;

/**
 * ResourceBundleを作成する。
 */
public class ResourceFactory {

	protected static final String PKG_ROOT = "org.tigergrab.game";

	public static enum PKG {
		ROOT, SEVENS
	};

	/**
	 * 設定ファイルの言語設定から、ResourceBundleを返す．
	 * 
	 * @param pkg
	 * @return ResourceBundle 言語設定が存在しない場合は、デフォルトのResourceBundleを返す．
	 */
	public static ResourceBundle getConfigurationByFile(PKG pkg) {
		String family = getFamily(pkg);
		LangConfigurationAction action = new LangConfigurationAction();
		if (action.readConfigurationFromFile()) {
			Lang lang = action.getLanguage();
			family += ("_" + lang.name());
		}
		return ResourceBundle.getBundle(family);
	}

	protected static String getFamily(PKG pkg) {
		String result = PKG_ROOT;
		if (pkg.equals(PKG.SEVENS)) {
			result += ".sevens";
		}
		result += ".resources";
		return result;
	}
}
