package org.tigergrab.game.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;

public class InputOutputUtil {

	private static final Logger logger = LoggerFactory
			.getLogger("InputOutputUtil.class");

	protected static final String CHARSET = "UTF-8";

	/**
	 * @return 標準入力から1行読み込んで返す。
	 * @throws WrongInputException
	 */
	public static String read() {
		String result = null;
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			result = readLine(bufferedReader);
		} catch (IOException e) {
			logger.error("入力を読み込めませんでした。");
		}

		if (result == null) {
			result = "";
		}
		return result;
	}

	protected static String readLine(BufferedReader bufferedReader)
			throws IOException {
		return bufferedReader.readLine();
	}

	public static boolean createNewFile(String fileName) {
		boolean result = false;
		File file = createFile(fileName);
		try {
			result = file.createNewFile();
		} catch (IOException e) {
			logger.error("ファイルを作成できませんでした。");
		}
		return result;
	}

	protected static File createFile(String fileName) {
		return new File(fileName);
	}

	public static void writeFile(String fileName, String string) {
		FileOutputStream os = null;
		OutputStreamWriter writer = null;
		try {
			os = new FileOutputStream(new File(fileName));
			writer = new OutputStreamWriter(os, CHARSET);
			write(writer, string);
		} catch (UnsupportedEncodingException e) {
			logger.error("文字コード " + CHARSET + " をサポートしていません。");
		} catch (FileNotFoundException e) {
			logger.error("ファイル" + fileName + "が見つかりません。");
		} finally {
			Closeables.closeQuietly(os);
			Closeables.closeQuietly(writer);
		}
	}

	protected static void write(OutputStreamWriter writer, String string) {
		try {
			writer.write(string);
		} catch (IOException e) {
			logger.error("書き込みエラーです。");
		}
	}
}
