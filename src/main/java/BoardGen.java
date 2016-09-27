import org.jacop.constraints.Alldifferent;
import org.jacop.constraints.Not;
import org.jacop.constraints.XplusCeqZ;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainRandom;
import org.jacop.search.MostConstrainedDynamic;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;
import org.jacop.search.SimpleSelect;
import org.jacop.search.SmallestDomain;


public class BoardGen 
{
	Store store;
	IntVar vars[];
	int N;
	
	public BoardGen(final int N)
	{
		this.N=N;
		
		store = new Store();

		vars = new IntVar[N];
		
		for (int i = 0; i < vars.length; i++)
			vars[i] = new IntVar(store, "x" + i, 0, N - 1);

		store.impose(new Alldifferent(vars));
		
		for (int x1 = 0; x1 < N; x1++) {
			IntVar a = vars[x1];

			for (int x2 = x1 + 1; x2 < N; x2++) {
				IntVar b = vars[x2];

				//diag constraints
				store.impose(new Not(new XplusCeqZ(a, x2 - x1, b)));
				store.impose(new Not(new XplusCeqZ(a, x1 - x2, b)));
			}
		}
	}
	
	public int[] genBoardDiag()
	{
		Search<IntVar> search = new DepthFirstSearch<IntVar>();
		search.setPrintInfo(false);
		
		SelectChoicePoint<IntVar> select = 
				new SimpleSelect<IntVar>(vars, 
                    new MostConstrainedDynamic<IntVar>(), 
                    new SmallestDomain<IntVar>(), 
                    new IndomainRandom<IntVar>());
		boolean result = search.labeling(store, select);
		
		if (result) {
			int arr[] = new int[N];

			for (int i = 0; i < N; i++) {
				arr[i] = vars[i].dom().value();
			}
			return arr;
		} else {
			return null;
		}
	}
}
