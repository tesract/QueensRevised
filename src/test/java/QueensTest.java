import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class QueensTest 
{
	@Test
	public void combine()
	{
		Board actual = Board.combine(
			row(-1), row(-1) ,
			row(-1), row(-1) );
		
		Board expected = row(
			-1, -1
		);
		
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void combine2()
	{
		Board actual = Board.combine(
			row(0), row(-1) ,
			row(-1), row(0) );
		
		Board expected = row(
			0, 1
		);
		
		Assert.assertEquals(expected, actual);
	}

	
	@Test
	public void combine3()
	{
		Board actual = Board.combine(
			row(0,-1), row(-1,1) ,
			row(-1,1), row(0,-1) );
		
		Board expected = row(
			0,3,2,1
		);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void validTest()
	{
		Assert.assertTrue(row(-1).isValid());
		Assert.assertTrue(row(0).isValid());

		Assert.assertTrue(row(-1,0).isValid());
		Assert.assertTrue(row(-1,1).isValid());
		
		Assert.assertTrue(row(0,-1).isValid());
		Assert.assertTrue(row(1,-1).isValid());

		Assert.assertFalse(row(0,1).isValid());
		Assert.assertFalse(row(1,0).isValid());		

//		Assert.assertTrue(q.valid(row(3,5,7,1,6,0,2,4)));
	}

	
	@Test
	public void test1(){
		Assert.assertEquals(2, new Queens().findSolution(1).solutions.size());
	}
	
	@Test
	public void test2(){
		Assert.assertEquals(5, new Queens().findSolution(2).solutions.size());
	}
	
	@Test
	public void testN(){
		Queens q=new Queens();
		
		for(Board board : q.findSolution(8).all())
		{
			if (!board.isFull()) continue;
			
			int arr[]=board.pos;
			
			System.out.println(board);
			
			for(int y=0; y<arr.length; y++)
			{
				for(int x=0; x<arr.length; x++)
				{
					System.out.print((arr[x]==y) ? "Q " : "_ ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

	public static Board row(int ... rows) { return new Board(rows); }
}
