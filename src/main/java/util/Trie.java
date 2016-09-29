package util;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
	Node root;

	public Trie() {
		root = new Node();
	}

	public void put(int ... key) {
		Node current = root;
		for (int k : key) {
			if (!current.children.containsKey(k)) {
				current.children.put(k, new Node());
			}
			current = current.children.get(k);
		}
	}

	public void walk(int[] key, TrieWalker walker) 
	{
		walk(root, key, 0, walker, new int[key.length]);
	}

	private void walk(Node current, int[] key, int i, TrieWalker walker, int val[]) {
		if (i >= key.length){
			walker.found(Arrays.copyOf(val, val.length));
			return;
		}
		
		int currentKey = key[i];
		
		for(Map.Entry<Integer,Node> entry : current.children.entrySet())
		{
			int k=entry.getKey();
			if (currentKey==-1 || k==-1)
			{
				val[i]=k;
				walk(entry.getValue(), key, i+1, walker, val);
			}
		}
	}

	private static class Node {
		Map<Integer, Node> children = new TreeMap<Integer, Node>();
	}
}
