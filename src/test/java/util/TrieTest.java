package util;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class TrieTest {
	@Test
	public void testTrie() {
		Trie t = new Trie();
		
		t.put(0,1,2,3);
		
		t.walk(row(-1,-1,-1,-1), new TrieWalker(){
			@Override
			public void found(int[] key) {
				Assert.assertArrayEquals(row(0,1,2,3), key);
			}
		});
	}
	
	@Test
	public void testTrie2() {
		Trie t = new Trie();
		
		t.put(0,1,2,3);
		t.put(0,1,-1,3);
		
		final ArrayList<int[]> results = new ArrayList<int[]>();
		
		t.walk(row(-1,-1,-1,-1), new TrieWalker(){
			@Override
			public void found(int[] key) {
				results.add(key);
			}
		});
		
		Assert.assertArrayEquals(row(0,1,-1,3), results.get(0));
		Assert.assertArrayEquals(row(0,1,2,3), results.get(1));
	}
	
	public static int[] row(int ... rows){return rows;}
}
