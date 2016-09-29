
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SolutionSetTest {
	SolutionSet ss1 = new SolutionSet(1);
	SolutionSet ss2 = new SolutionSet(2);
	SolutionSet ss4 = new SolutionSet(4);

	@Before
	public void before() {
		ss1.addSolution(row(-1));
		ss1.addSolution(row(0));

		ss2.addSolution(row(-1, -1));
		ss2.addSolution(row(0, -1));
		ss2.addSolution(row(-1, 0));
		ss2.addSolution(row(1, -1));
		ss2.addSolution(row(-1, 1));
	}

	@Test
	public void horizCheckTest() {
		Assert.assertTrue(SolutionSet.horizCheck(row(-1), row(-1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1), row(0)));
		Assert.assertTrue(SolutionSet.horizCheck(row(0), row(-1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(0), row(0)));

		Assert.assertTrue(SolutionSet.horizCheck(row(-1, -1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, -1), row(-1, 0)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, -1), row(-1, 1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, -1), row(0, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, -1), row(1, -1)));

		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 0), row(-1, -1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(-1, 0), row(-1, 0)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 0), row(-1, 1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(-1, 0), row(0, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 0), row(1, -1)));

		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 1), row(-1, 0)));
		Assert.assertFalse(SolutionSet.horizCheck(row(-1, 1), row(-1, 1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(-1, 1), row(0, -1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(-1, 1), row(1, -1)));

		Assert.assertTrue(SolutionSet.horizCheck(row(0, -1), row(-1, -1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(0, -1), row(-1, 0)));
		Assert.assertTrue(SolutionSet.horizCheck(row(0, -1), row(-1, 1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(0, -1), row(0, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(0, -1), row(1, -1)));

		Assert.assertTrue(SolutionSet.horizCheck(row(1, -1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(1, -1), row(-1, 0)));
		Assert.assertFalse(SolutionSet.horizCheck(row(1, -1), row(-1, 1)));
		Assert.assertTrue(SolutionSet.horizCheck(row(1, -1), row(0, -1)));
		Assert.assertFalse(SolutionSet.horizCheck(row(1, -1), row(1, -1)));

	}

	@Test
	public void vertCheckTest() {
		Assert.assertTrue(SolutionSet.vertCheck(row(-1), row(-1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1), row(0)));
		Assert.assertTrue(SolutionSet.vertCheck(row(0), row(-1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(0), row(0)));

		Assert.assertTrue(SolutionSet.vertCheck(row(-1, -1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, -1), row(-1, 0)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, -1), row(-1, 1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, -1), row(0, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, -1), row(1, -1)));

		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 0), row(-1, -1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(-1, 0), row(-1, 0)));
		Assert.assertFalse(SolutionSet.vertCheck(row(-1, 0), row(-1, 1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 0), row(0, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 0), row(1, -1)));

		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 1), row(-1, -1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(-1, 1), row(-1, 0)));
		Assert.assertFalse(SolutionSet.vertCheck(row(-1, 1), row(-1, 1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 1), row(0, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(-1, 1), row(1, -1)));

		Assert.assertTrue(SolutionSet.vertCheck(row(0, -1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(0, -1), row(-1, 0)));
		Assert.assertTrue(SolutionSet.vertCheck(row(0, -1), row(-1, 1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(0, -1), row(0, -1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(0, -1), row(1, -1)));

		Assert.assertTrue(SolutionSet.vertCheck(row(1, -1), row(-1, -1)));
		Assert.assertTrue(SolutionSet.vertCheck(row(1, -1), row(-1, 0)));
		Assert.assertTrue(SolutionSet.vertCheck(row(1, -1), row(-1, 1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(1, -1), row(0, -1)));
		Assert.assertFalse(SolutionSet.vertCheck(row(1, -1), row(1, -1)));
	}

	@Test
	public void testAll1() {
		AssertEquals(ss1.all(), row(0), row(-1));
	}

	public void testAll2() {
		AssertEquals(ss2.all(), row(-1, -1), row(0, -1), row(-1, 0), row(1, -1), row(-1, 1));
	}

	@Test
	public void testTR1() {
		AssertEquals(ss1.tr(row(0)), row(-1));
		AssertEquals(ss1.tr(row(-1)), row(0), row(-1));
	}

	@Test
	public void testTR2() {
		for (Board tl : ss2.all()) {
			ArrayList<Board> expected = new ArrayList<Board>();
			
			for(Board b : ss2.all()) {
				if (SolutionSet.horizCheck(tl, b)) {
					expected.add(b);
				}
			}
			
			AssertEquals(ss2.tr(tl), expected.toArray(new Board[0]));
		}
	}

	@Test
	public void testBL1() {
		AssertEquals(ss1.bl(row(0)), row(-1));
		AssertEquals(ss1.bl(row(-1)), row(0), row(-1));
	}

	@Test
	public void testBL2() {
		for (Board tl : ss2.all()) {
			ArrayList<Board> expected = new ArrayList<Board>();
			
			for(Board b : ss2.all()) {
				if (SolutionSet.vertCheck(tl, b)) {
					expected.add(b);
				}
			}
			
			AssertEquals(ss2.bl(tl), expected.toArray(new Board[0]));
		}
	}

	@Test
	public void testBR1() {
		AssertEquals(ss1.br(row(-1), row(-1)), row(0), row(-1));
		AssertEquals(ss1.br(row(0), row(-1)), row(-1));
		AssertEquals(ss1.br(row(-1), row(0)), row(-1));
	}

	@Test
	public void testBR2() {
		for (Board tr : ss2.all()) {
			for (Board bl : ss2.all()) {
				ArrayList<Board> expected = new ArrayList<Board>();
				
				for(Board b : ss2.all()) {
					if (SolutionSet.horizCheck(bl, b) && SolutionSet.vertCheck(tr, b)) {
						expected.add(b);
					}
				}
				
				AssertEquals(ss2.br(tr,bl), expected.toArray(new Board[0]));
			}
		}
	}

	
	public static void AssertEquals(Collection<Board> actual, Board... expected) {
		Assert.assertArrayEquals(expected, actual.toArray(new Board[0]));
	}

	public static Board row(int... rows) {
		return new Board(rows);
	}
}
