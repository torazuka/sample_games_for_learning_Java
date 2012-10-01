package org.tigergrab.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.tigergrab.game.conf.impl.DefaultManager;

public class MainTest {
	Main main;

	@Test
	public void testConstructor() throws Exception {
		main = new Main();

		assertNotNull("フィールドconfがセットされている。", main.conf);
		assertEquals("confの型がDefaultManagerである。", true,
				main.conf instanceof DefaultManager);

		assertNotNull("フィールドresourceがセットされている。", main.resource);
	}

	@Ignore("assert悩み中")
	@Test
	public void testMain() throws Exception {
		MainMockForExecute.main(null);
		// TODO: assert
	}

	@Test
	public void testExecute() throws Exception {
		// TODO ユーザ入力がisValidでfalseなら入力を待ち続ける、というテストをどう書くか
		// use MainMockForExecute.class
		main = new MainMockForExecute();
		main.execute();
	}

	@Test
	public void testIsValid() throws Exception {
		main = new Main();
		assertEquals("nullのとき、false。", false, main.isValid(null));
		assertEquals("空文字のとき、false。", false, main.isValid(""));
		assertEquals("1のとき、true。", true, main.isValid("1"));
		assertEquals("3のとき、false。", false, main.isValid("3"));
	}

	@Test
	public void testMenuController() throws Exception {
		main = new MainMockForMenuController();
		assertEquals("1ならtrue", true, main.menuController("1"));
		assertEquals("2ならtrue", true, main.menuController("2"));
		assertEquals("1と2以外ならfalse", false, main.menuController("A"));
	}
}
