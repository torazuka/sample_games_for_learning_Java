package org.tigergrab.game.conf.impl;


public class ResourceFactory {

	protected static final String PKG_ROOT = "org.tigergrab.game";

	public static enum PKG {
		ROOT, SEVENS
	};

	public static String getFamily(PKG pkg) {
		String result = PKG_ROOT;
		if (pkg.equals(PKG.SEVENS)) {
			result += ".sevens";
		}
		result += ".resources";
		// PKG.ROOT
		return result;
	}

	// public static ResourceBundle getResource(PKG pkg, DefaultManager conf) {
	// if (pkg.equals(PKG.ROOT)) {
	// return conf.getMenuResource();
	// }
	// return null;
	// }
}
