import org.junit.Test;


public class TestSolution {
	@Test
	public void test(){
		GeneticAlgorithmBoard ga = new GeneticAlgorithmBoard(17, 1000, 100);
		
		Board b = ga.find();
		
		System.out.println(b);
	}
}
