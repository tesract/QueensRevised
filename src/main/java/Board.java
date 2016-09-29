import java.util.Arrays;
import java.util.BitSet;

public class Board {
	final int[] pos;

	public Board(int ... pos) {
		this.pos = pos;
	}

	public static Board combine(final Board tl, final Board tr, final Board bl, final Board br) {
		int s = tl.pos.length;

		int[] ret = new int[s * 2];

		for (int i = 0; i < s; i++) {
			if ((tl.pos[i] != -1 && tl.pos[i] == bl.pos[i]) ||
				(tr.pos[i] != -1 && tr.pos[i] == br.pos[i])) {
				System.out.println(tl+" "+tr);
				System.out.println(bl+" "+br);
				throw new Error("combine");
			}
			
			if (tl.pos[i] != -1)
				ret[i] = tl.pos[i];
			else if (bl.pos[i] != -1)
				ret[i] = bl.pos[i] + s;
			else
				ret[i] = -1;

			if (tr.pos[i] != -1)
				ret[i + s] = tr.pos[i];
			else if (br.pos[i] != -1)
				ret[i + s] = br.pos[i] + s;
			else
				ret[i + s] = -1;
		}
		
		return new Board(ret);
	}

	public boolean isValid() {
		int s = pos.length;

		BitSet horiz = new BitSet(s);
		for (int i = 0; i < s; i++) {
			if (pos[i] == -1)
				continue;

			if (horiz.get(pos[i]))
			{
				return false;
			}
			horiz.set(pos[i]);
		}

		// test verticals

		// test diagonals
		SlashSet slash = new SlashSet(s);
		for (int x = 0; x < s; x++) {
			int y = pos[x];

			if (y == -1) continue;

			if (!slash.Add(x, y))
			{
				return false;
			}
		}

		ThreeQueenChecker checker = new ThreeQueenChecker();
		for (int x = 0; x < s; x++) {
			int y = pos[x];
			
			if (y!=-1 && !checker.Add(x, y))
				return false;
		}

		// test 3 queens
		for (int x1 = 0; x1 < s; x1++) {
			int y1 = pos[x1];
			if (y1==-1) continue;
			for (int x2 = x1 + 1; x2 < s; x2++) {
				int y2 = pos[x2];
				if (y2==-1) continue;
				for (int x3 = x2 + 1; x3 < s; x3++) {
					int y3 = pos[x3];
					if (y3==-1) continue;

					if (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2) == 0) {
						return false;
					}
				}
			}
		}
		/*
		*/
		
		
		return true;
	}
	
	public boolean isFull() {
		for(int v : pos) {
			if (v==-1) return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("B[ ");
		for(int i=0; i<pos.length; i++)
		{
			sb.append(pos[i]);
			sb.append(' ');
		}
		sb.append(']');
		sb.append(isValid()?"V":"NV");
		
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (!Arrays.equals(pos, other.pos))
			return false;
		return true;
	}
}
