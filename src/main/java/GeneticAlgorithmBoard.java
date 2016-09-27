import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithmBoard extends GeneticAlgorithm<Board> {
	
	private int N;
	private BoardGen boardGen;
	
	public GeneticAlgorithmBoard(int N, int max, int nextMax) {
		super(max, nextMax);
		
		this.N=N;
		this.boardGen=new BoardGen(N);
	}
	
	@Override
	public Board genPotential() {
		return new Board(boardGen);
	}

	@Override
	public float fitness(Board a) {
		return a.getFitness();
	}

	@Override
	public int compare(Board o1, Board o2) {
		return (int) (100 * (o2.getFitness() - o1.getFitness()));
	}

	@Override
	public Board generateDescendant(Board a, Board b) {
		// ArrayList<Integer> ac = new ArrayList<Integer>(a.conflicts);
		// ArrayList<Integer> bc = new ArrayList<Integer>(b.conflicts);

		// int []at = transpose(a.arr);
		// int []bt = transpose(b.arr);

		int arr[] = new int[N];

		HashSet<Integer> availableSet = new HashSet<Integer>();

		for (int i = 0; i < N; i++)
			availableSet.add(i);

		int r = ThreadLocalRandom.current().nextInt(10);
		if (r == 0) {
			
			// part from a
			int S = ThreadLocalRandom.current().nextInt((int)(N*.1), (int)(N*.9));
			for (int i = 0; i <S; i++) {
				int v = a.arr[i];

				availableSet.remove(v);

				arr[i] = v;
			}

			HashSet<Integer> slotSet = new HashSet<Integer>();
			for (int i = S + 1; i < N; i++) {
				int v = b.arr[i];

				if (!availableSet.contains(v)) {
					slotSet.add(i);
				} else {
					arr[i] = v;
					availableSet.remove(v);
				}
			}

			Integer[] available = new Integer[availableSet.size()];
			availableSet.toArray(available);

			Integer[] slots = new Integer[slotSet.size()];
			slotSet.toArray(slots);

			Util.shuffleArrayInteger(available);

			for (int i = 0; i < slots.length; i++) {
				arr[slots[i]] = available[i];
			}

			return new Board(arr);
		} else {
			return new Board(boardGen);
		}
	}

	private int[] transpose(int[] input) {
		int[] ret = new int[input.length];

		for (int i = 0; i < input.length; i++) {
			ret[input[i]] = i;
		}

		return ret;
	}
};