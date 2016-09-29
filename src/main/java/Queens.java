import java.util.Collection;
import java.util.HashMap;

public class Queens {
	HashMap<Integer, SolutionSet> solutions = new HashMap<Integer, SolutionSet>();

	public SolutionSet findSolution(int size) {
		if (solutions.containsKey(size))
			return solutions.get(size);

		final SolutionSet ret = new SolutionSet(size);

		if (size == 1) {
			ret.addSolution(new Board(0));
			ret.addSolution(new Board(-1));
		} else if (size % 2 == 0) {
			// XX
			// XX
			int s2 = size / 2;

			SolutionSet q = findSolution(s2);

			for (Board tl : q.all()) {
				SlashSet tl_slash= new SlashSet(size);
				for(int x=0; x<s2; x++) {
					int y=tl.pos[x];
					if (y!=-1 && !tl_slash.Add(x, y)) continue;
				}
				
				for (Board tr : q.tr(tl)) {
					SlashSet tr_slash= new SlashSet(tl_slash);
					for(int x=0; x<s2; x++) {
						int y=tr.pos[x];
						if (y!=-1 && !tr_slash.Add(x+s2, y)) continue;
					}
					
					for (Board bl : q.bl(tl)) {
						SlashSet bl_slash= new SlashSet(tr_slash);
						for(int x=0; x<s2; x++) {
							int y=bl.pos[x];
							if (y!=-1 && !bl_slash.Add(x, y+s2)) continue;
						}

						for (Board br : q.br(tr, bl)) {
							SlashSet br_slash= new SlashSet(bl_slash);
							for(int x=0; x<s2; x++) {
								int y=br.pos[x];
								if (y!=-1 && !br_slash.Add(x+s2, y+s2)) continue;
							}

							Board sol = Board.combine(tl, tr, bl, br);

							if (sol.isValid())
							{
								ret.addSolution(sol);
							}
						}
					}
				}
			}
		} else if (size % 2 == 1) {
			// X|X
			// ---
			// X|X
		}

		System.out.println(size+"="+ret.solutions.size());
		
		solutions.put(size, ret);

		return ret;
	}
}