import org.junit.Assert;
import org.junit.Test;

public class BoardTest {
	@Test
	public void testBoard1(){
		Assert.assertTrue(new Board(-1).isValid());
		Assert.assertTrue(new Board(0).isValid());
	}

	@Test
	public void testBoard2(){
		Assert.assertTrue(new Board(-1,-1).isValid());
		Assert.assertTrue(new Board(0,-1).isValid());
		Assert.assertTrue(new Board(-1,0).isValid());
		Assert.assertTrue(new Board(1,-1).isValid());
		Assert.assertTrue(new Board(-1,1).isValid());
		Assert.assertFalse(new Board(0,1).isValid());
		Assert.assertFalse(new Board(1,0).isValid());
		Assert.assertFalse(new Board(0,0).isValid());
		Assert.assertFalse(new Board(1,1).isValid());
	}
	
	@Test
	public void testBoard4(){
		Assert.assertTrue(new Board(0,3,1,-1).isValid());
	}

}
