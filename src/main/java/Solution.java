import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Not;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XmulCeqZ;
import org.jacop.constraints.XplusCeqZ;
import org.jacop.constraints.XplusYeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.MostConstrainedDynamic;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;


public class Solution
{
	public static final int N = 63;
	
	public static void main(String args[])
	{
		Store store = new Store();

		IntVar vars[] = new IntVar[N];
		IntVar diffs[] = new IntVar[N*N];
		IntVar Mdiffs[] = new IntVar[N*N*N*2];
		
		for (int i = 0; i < vars.length; i++)
			vars[i] = new IntVar(store, "x" + i, 0, N - 1);

		store.impose(new Alldifferent(vars));
		
		for (int x1 = 0; x1 < N; x1++) {
			IntVar a = vars[x1];

			for (int x2 = 0; x2 < N; x2++) {
				IntVar b = vars[x2];
				
				IntVar d = new IntVar(store, "d"+x1+","+x2, -N+1, N-1);
				diffs[x1+x2*N]= d;
				
				//d=b-a
				store.impose(new XplusYeqZ(a,d,b));
				
				for(int i=-N+1; i<N; i++)
				{
					IntVar m = new IntVar(store,"m"+d.id+","+i, -N*N, N*N);
					
					Mdiffs[x1+x2*N+(i+N)*N*N]=m;
					
					//m=i*d
					store.impose(new XmulCeqZ(d,i,m));
				}
			}
		}
		
		
		for (int x1 = 0; x1 < N; x1++) {
			IntVar a = vars[x1];

			for (int x2 = x1 + 1; x2 < N; x2++) {
				IntVar b = vars[x2];

				//diag constraints
				store.impose(new Not(new XplusCeqZ(a, x2 - x1, b)));
				store.impose(new Not(new XplusCeqZ(a, x1 - x2, b)));

				int run1 = x2-x1;
				IntVar diff1 = diffs[x1+x2*N];
				
				for (int x3 = x2+1; x3 < N; x3++) {
					if (x1 == x3 || x2 == x3)
						continue;

					IntVar c = vars[x3];

					int run2=x3-x1;
					IntVar diff2 = diffs[x1+x3*N];
					
					IntVar lhs = Mdiffs[x1+x2*N+(N+run2)*N*N];
					IntVar rhs = Mdiffs[x1+x3*N+(N+run1)*N*N];
					
					//(x2-x1) * diff1 != (x3-x1) * diff2
					store.impose(new Not(new XeqY(lhs, rhs)));
				}
			}
		}

		System.out.println("search start");
		long startTime = System.currentTimeMillis();
		
		Search<IntVar> search = new DepthFirstSearch<IntVar>();
		SelectChoicePoint<IntVar> select = 
				new SimpleSelect<IntVar>(vars, 
                    new MostConstrainedDynamic<IntVar>(), 
                    new SmallestDomain<IntVar>(), 
                    new IndomainMin<IntVar>());
		boolean result = search.labeling(store, select);

		if (result) {
			int arr[] = new int[N];

			System.out.println(N);
			for (int i = 0; i < N; i++) {
				arr[i] = vars[i].dom().value();
				System.out.print(arr[i]+1+" ");
			}
			System.out.println();
			
			Board b = new Board(arr);
			float fitness = b.getFitness();

			System.out.println(fitness);
			
			//System.out.pritnln(b.toString());
		} else {
			System.out.println("*** No");
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(endTime-startTime);
	}
}
