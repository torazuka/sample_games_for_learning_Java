package org.tigergrab.game.sevens.impl;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultSpaceTest {

	@Test
	public void testPutCard() throws Exception {
		DefaultSpace space = new DefaultSpace();
		space.putCard(new Card(Suite.Spade, 5));
		space.putCard(new Card(Suite.Spade, 6));
		space.putCard(new Card(Suite.Spade, 8));

		Card card = new Card(Suite.Spade, 7);

		assertNull(space.searchCard(card));
		space.putCard(card);

		Card temp = space.searchCard(card);
		assertNotNull(temp);
		assertEquals(Suite.Spade, temp.suite);
		assertEquals(7, temp.rank.getRank());
	}

	@Test
	public void testCanLead() throws Exception {
		DefaultSpace space = new DefaultSpace();
		space.putCard(new Card(Suite.Spade, 5));
		space.putCard(new Card(Suite.Spade, 6));
		space.putCard(new Card(Suite.Spade, 7));

		assertEquals("場:S-05, S-06, S-07のとき、S-04は出せる。", true,
				space.canLead(new Card(Suite.Spade, 4)));
		assertEquals("場:S-05, S-06, S-07のとき、S-08は出せる。", true,
				space.canLead(new Card(Suite.Spade, 8)));
		assertEquals("場:S-05, S-06, S-07のとき、S-09は出せない。", false,
				space.canLead(new Card(Suite.Spade, 9)));
		assertEquals("場:S-05, S-06, S-07のとき、H-04は出せない。", false,
				space.canLead(new Card(Suite.Heart, 4)));
	}

	@Test
	public void testSearchCard() throws Exception {
		DefaultSpace space = new DefaultSpace();
		space.putCard(new Card(Suite.Spade, 5));
		space.putCard(new Card(Suite.Spade, 6));
		space.putCard(new Card(Suite.Spade, 7));

		assertNotNull("場:S-05, S-06, S-07のとき、S-05はある。",
				space.searchCard(new Card(Suite.Spade, 5)));
		assertNull("場:S-05, S-06, S-07のとき、S-08はない。",
				space.searchCard(new Card(Suite.Spade, 8)));
	}
}
