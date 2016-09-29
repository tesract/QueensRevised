import org.junit.Test;
import org.junit.Assert;

public class ThreeQueenCheckerTest {
	@Test
	public void TestForward(){
		ThreeQueenChecker checker=new ThreeQueenChecker();
		
		Assert.assertTrue(checker.Add(0, 0));
		Assert.assertTrue(checker.Add(1, 1));
		Assert.assertFalse(checker.Add(3, 3));
	}
	@Test
	public void TestMix(){
		ThreeQueenChecker checker=new ThreeQueenChecker();
		
		Assert.assertTrue(checker.Add(1, 1));
		Assert.assertTrue(checker.Add(0, 0));
		Assert.assertFalse(checker.Add(3, 3));
	}
	@Test
	public void TestReverse(){
		ThreeQueenChecker checker=new ThreeQueenChecker();
		
		Assert.assertTrue(checker.Add(3, 3));
		Assert.assertTrue(checker.Add(1, 1));
		Assert.assertFalse(checker.Add(0, 0));
	}

	@Test
	public void TestForward2(){
		ThreeQueenChecker checker=new ThreeQueenChecker();
		
		Assert.assertTrue(checker.Add(0, 1));
		Assert.assertTrue(checker.Add(2, 0));
		Assert.assertTrue(checker.Add(3, 2));
		Assert.assertTrue(checker.Add(2, 3));
	}

}
