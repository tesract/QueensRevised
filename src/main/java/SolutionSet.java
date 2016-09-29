import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;

import util.Trie;
import util.TrieWalker;

public class SolutionSet {
	final int size;

	HashSet<Board> solutions;
	Trie blTrie = new Trie();

	public SolutionSet(int size) {
		this.size = size;
		solutions = new HashSet<Board>();
	}

	public void addSolution(Board board) 
	{
		if (size != board.pos.length)
			throw new Error("Argument size exception");

		solutions.add(board);

		blTrie.put(board.pos);
	}

	public Collection<Board> all() {
		return solutions;
	}

	public Collection<Board> tr(Board tl) {
		ArrayList<Board> ret=new ArrayList<Board>();
		
		for(Board board : solutions){
			if (horizCheck(tl,  board))
				ret.add(board);
		}
		
		return ret;
	}

	public Collection<Board> bl(Board tl) {
		//Set<Board> expected = solutions.stream().filter(x -> vertCheck(x, tl)).collect(Collectors.toSet());

		final HashSet<Board> actual = new HashSet<Board>();

		blTrie.walk(tl.pos, new TrieWalker() {
			@Override
			public void found(int[] key) {
				actual.add(new Board(key));
			}
		});

		return actual;
	}

	public Collection<Board> br(Board tr, final Board bl) {
		
		final HashSet<Board> actual = new HashSet<Board>();

		blTrie.walk(tr.pos, new TrieWalker() {
			@Override
			public void found(int[] key) {
				Board b = new Board(key);
				
				if (horizCheck(bl, b))
					actual.add(b);
			}
		});
		
		return actual;
	}
	
	static boolean vertCheck(Board a, Board b) {
		if (a.pos.length!=b.pos.length) throw new Error("mismatched board sizes");

		for (int i = 0; i < a.pos.length; i++) {
			if (a.pos[i] != -1 && b.pos[i] != -1)
				return false;
		}

		return true;
	}

	 static boolean horizCheck(Board a, Board b) {
		int s = a.pos.length;
		
		if (a.pos.length!=b.pos.length) throw new Error("mismatched board sizes");

		BitSet horiz = new BitSet(s);
		for (int i = 0; i < s; i++) {
			if (a.pos[i] != -1) {
				if (horiz.get(a.pos[i]))
					return false;
				else
					horiz.set(a.pos[i]);
			}
			if (b.pos[i] != -1) {
				if (horiz.get(b.pos[i]))
					return false;
				else
					horiz.set(b.pos[i]);
			}
		}

		return true;
	}
}
