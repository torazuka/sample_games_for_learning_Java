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
			result = bufferedReader.readLine();
		} catch (IOException e) {
			logger.error("入力を読み込めませんでした。");
		}

		if (result == null) {
			result = "";
		}
		return result;
	}

	public static boolean createFile(String fileName) {
		boolean result = false;
		File file = new File(fileName);
		try {
			result = file.createNewFile();
		} catch (IOException e) {
			logger.error("ファイルを作成できませんでした。");
		}
		return result;
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
			closeOutoputStream(os);
			closeWriter(writer);
		}
	}

	protected static void write(OutputStreamWriter writer, String string) {
		try {
			writer.write(string);
		} catch (IOException e) {
			logger.error("書き込みエラーです。");
		}
	}

	protected static void closeOutoputStream(FileOutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("FileOusputStreamを閉じようとしてエラーが発生しました。");
			}
		}
	}

	protected static void closeWriter(OutputStreamWriter writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				logger.error("OutputStreamWriterを閉じようとしてエラーが発生しました。");
			}
		}
	}
}
