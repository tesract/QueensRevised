import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	int arr[];

	public Board(BoardGen boardGen) {
		this(boardGen.genBoardDiag());
	}

	public Board(int arr[]) {
		this.arr = arr;
	}


	public static void shuffleArray(int[] arr) {
		
		int diag[] = new int[arr.length*2];
		int biag[] = new int[arr.length*2];
		int conflicts=0;
		
		for(int i=0; i<arr.length; i++)
		{
			int d = i-arr[i];
			int b = (arr.length-1-i)-arr[i];
			
			if (++diag[d]>1) conflicts++;
			if (++biag[b]>1) conflicts++;
		}
		
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i1 = arr.length - 1; i1 > 0; i1--) {
			int i2 = rnd.nextInt(i1 + 1);
			
			int a1 = arr[i1];
			int a2 = arr[i2];
			
			int d1a = i1-a1;
			int d2a = i2-a2;
			int b1a = (arr.length-1-i1) - a1;
			int b2a = (arr.length-1-i2) - a2;
			
			int d1b = i1-a2;
			int d2b = i2-a1;
			int b1b = (arr.length-1-i1) - a2;
			int b2b = (arr.length-1-i2) - a1;
			
			arr[i1]=a2;
			arr[i2]=a1;
			
			while(
				biag[b1a]!=0 ||
				biag[b2a]!=0 ||
				biag[b1b]!=0 ||
				biag[b2b]!=0)
			{
				
			}
			
			if (--diag[d1a]<=1) conflicts--;
			if (--diag[d2a]<=1) conflicts--;
			if (--biag[b1a]<=1) conflicts--;
			if (--biag[b2a]<=1) conflicts--;
			
			if (--diag[d1b]<=1) conflicts++;
			if (--diag[d2b]<=1) conflicts++;
			if (--biag[b1b]<=1) conflicts++;
			if (--biag[b2b]<=1) conflicts++;
		}
	}
	
	private HashSet<Integer> conflicts = new HashSet<Integer>();
	private Float fitness;

	public float getFitness() {
		if (fitness == null) {
			final int N = arr.length;

			for (int x1 = 0; x1 < N; x1++) {
				final int y1 = arr[x1];

				for (int x2 = x1 + 1; x2 < N; x2++) {
					final int y2 = arr[x2];

					int ax = x2 - x1;
					int ay = y2 - y1;

					// check diagonals
					if (ax == ay || ax == -ay) {
//						System.out.println(x1+","+y1+" "+x2+","+y2);
						 conflicts.add(x1);
						 conflicts.add(x2);
						 continue;
					}

					int gcd = Math.abs(Util.GCD(ax, ay));

					ax = ax / gcd;
					ay = ay / gcd;

					final int M = x1 * ay - y1 * ax;

					// check 3 aligned
					for (int x3 = x1 % ax; x3 < N; x3 += ax) {
						if (x1 == x3 || x2 == x3 || x3<x2)
							continue;

						final int y3 = arr[x3];

						if (x3 * ay - y3 * ax == M) {
//							System.out.println(x1+" "+x2+" "+x3);

							conflicts.add(x1);
							conflicts.add(x2);
							conflicts.add(x3);
							continue;
						}
					}
				}
			}

			fitness=(arr.length - conflicts.size())/(float)arr.length;
		}

		return fitness;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < arr.length; i++)
			sb.append(arr[i]).append(" ");
		sb.append("\n");

		for (int y = 0; y < arr.length; y++) {
			for (int x = 0; x < arr.length; x++) {
				sb.append((arr[y] == x) ? 'x' : '_');
			}

			sb.append("\n");
		}

		return sb.toString();
	}
}