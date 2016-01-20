import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class GeneticAlgorithm<T> implements Comparator<T> {
	final int max;
	final int nextGen;

	public GeneticAlgorithm(int max, int nextGen) {
		this.max = max;
		this.nextGen = nextGen;
	}

	private long lastPrint = System.currentTimeMillis();
	public T find() {
		ArrayList<T> survivors = new ArrayList<T>(max);

		int generation = 1;
		while (true) {
			// fill queue with new generation
			while (survivors.size() < max)
			{
				T s = genPotential();
				
				if (fitness(s)==1)
					return s;
				
				survivors.add(s);
			}

			Collections.sort(survivors, this);

			if (fitness(survivors.get(0)) == 1.0)
			{
				System.out.println("rnd");
				return survivors.get(0);
			}
			
			// kill off weaker specemins
			while (survivors.size() > nextGen)
				survivors.remove(survivors.size() - 1);

			generateDescendants(survivors);

			Collections.sort(survivors, this);

			if (fitness(survivors.get(0)) == 1.0) {
				System.out.println("mix");
				return survivors.get(0);
			}

			// kill off weaker specemins
			while (survivors.size() > max-nextGen)
				survivors.remove(survivors.size() - 1);

			if (System.currentTimeMillis() - lastPrint > 1000) 
			{
				lastPrint=System.currentTimeMillis();
				
				System.out.println(generation + " " + fitness(survivors.get(0)));
			}
			
			generation++;
		}

	}

	public void generateDescendants(ArrayList<T> survivors) {
		int size = survivors.size();

		while (survivors.size() < max) {
			int ai = Util.rr(size);
			int bi = Util.rr(size);

			if (ai == bi)
				continue;

			T a = survivors.get(ai);
			T b = survivors.get(bi);

			survivors.add(generateDescendant(a, b));
		}
	}

	public abstract float fitness(T a);

	public abstract T genPotential();

	public abstract T generateDescendant(T a, T b);
}
