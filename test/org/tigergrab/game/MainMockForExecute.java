package org.tigergrab.game;

import static org.junit.Assert.assertEquals;

public class MainMockForExecute extends Main {

	@Override
	protected boolean menuController(String input) {
		assertEquals("executeから呼ばれたとき、値が「2」。", true, input.equals("2"));
		// 実装コードではtrueを返すが、無限ループを避けるため、このテストではfalseを返す。
		return false;
	}

	@Override
	protected String read() {
		return "2";
	}

	@Override
	protected boolean isValid(String input) {
		return true;
	}
}
