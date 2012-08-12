package org.tigergrab.game.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputOutputUtil {

	/**
	 * @return 標準入力から1行読み込んで返す。
	 * @throws WrongInputException
	 */
	public static String read() {
		String result = null;
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			result = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
