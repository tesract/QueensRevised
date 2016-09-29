package graph;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class Graph 
{
	HashSet<Integer> nodes=new HashSet<Integer>();
	MultiValuedMap<Integer,Integer> edges=new ArrayListValuedHashMap<Integer,Integer>();
	
	public void addEdge(int a, int b)
	{
		nodes.add(a);
		nodes.add(b);
		
		edges.put(a, b);
		edges.put(b, a);
	}
	
	public HashMap<Integer,Integer> dykstra(int s)
	{
		HashMap<Integer,Integer> ret = new HashMap<Integer,Integer>();
		
		HashSet<Integer> visited=new HashSet<Integer>();
		Queue<Pair<Integer,Integer>> toVisit=new ArrayDeque<Pair<Integer,Integer>>(nodes.size());
		
		toVisit.add(new Pair<Integer, Integer>(s,0));
		
		while(!toVisit.isEmpty())
		{
			Pair<Integer,Integer> record = toVisit.poll();
			
			int visit = record.getA();
			int dist = record.getB();
			
			ret.put(visit, dist);
			visited.add(visit);
			
			for(Integer outNode : edges.get(visit))
			{
				if (!visited.contains(outNode))
					toVisit.add(new Pair<Integer,Integer>(outNode, visit+1));
			}
		}
		
		return ret;
	}
	
	public static class Pair<A,B>
	{
		A a;
		B b;
		
		public Pair(A a, B b) {
			this.a=a;
			this.b=b;
		}
		
		public A getA() {
			return a;
		}
		
		public B getB() {
			return b;
		}
	}
	
	public static void main(String args[]) throws IOException
	{
		DataInputStream dis = new DataInputStream(System.in);
		
		int tests = dis.readInt();
		
		for(int test=0; test<tests; test++)
		{
			Graph g = new Graph();
			
			int nodes = dis.readInt();
			int edges = dis.readInt();
			
			for(int edge=0; edge<edges; edge++)
			{
				g.addEdge(dis.readInt(), dis.readInt());
			}
			
			int start = dis.readInt();
			
			HashMap<Integer,Integer> dists = g.dykstra(start);
			
			for(int i=0; i<nodes; i++)
			{
				if (i==start) continue;
				
				Integer dist = dists.get(i);
				
				if (dist==null) dist=-1;
				
				System.out.print(dist);
				if (i!=nodes-1 || (start==nodes-1 && start==nodes-2))
					System.out.print(" ");
			}
			
			System.out.println();
		}
	}
}
