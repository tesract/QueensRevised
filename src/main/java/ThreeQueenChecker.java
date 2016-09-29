import java.math.BigInteger;
import java.util.HashSet;
import java.util.TreeSet;

public class ThreeQueenChecker {
	TreeSet<Point> points = new TreeSet<Point>();
	HashSet<Line> lines = new HashSet<Line>();
	
	boolean Add(int x, int y) {
		Point a=new Point(x,y);
		
		if (points.contains(a)) {
			return true;
		}
		
		for(Point b: points) {
			Line line = new Line(a,b);
			
			if (lines.contains(line)) {
				return false;
			}
			else {
				lines.add(line);
				return true;
			}
		}
		
		points.add(a);
		return true;
	}

	class Line {
		int dx;
		int dy;

		int ix;
		int iy;
		
		public Line(Point a, Point b) {
			int dx = a.x-b.x;
			int dy = a.y-b.y;
			int gcd = BigInteger.valueOf(dx).gcd(BigInteger.valueOf(dy)).intValue();
			if (gcd==0) gcd=1;
			if (dx<0) gcd=-gcd;
			
			this.dx=dx/gcd;
			this.dy=dy/gcd;
			
			ix=a.x;
			iy=a.y;
			
			int div;
			if (dx==0) div=a.y/dy;
			else if (dy==0) div=a.x/dx;
			else div = Math.min(a.x/dx, a.y/dy);
			
			ix=a.x-div*dx;
			iy=a.y-div*dy;

			while(ix>=this.dx && iy>=this.dy) {
				ix-=this.dx;
				iy-=this.dy;
			}
		}
		
		@Override
		public String toString() {
			return "L["+this.dx+","+this.dy+","+this.ix+","+this.iy+"]";
		}
		
		@Override
		public int hashCode() {
			final int p=31;
			int hash=0;
			
			hash=hash*p+dx;
			hash=hash*p+dy;
			hash=hash*p+ix;
			hash=hash*p+iy;	
			
			return hash;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj==null) return false;
			if (this==obj) return true;
			if (obj.getClass() != Line.class)
				return false;
			Line other = (Line)obj;
			return 
					dx==other.dx && 
					dy==other.dy && 
					ix==other.ix && 
					iy==other.iy; 
		}
	}
	
	class Point implements Comparable<Point> {
		int x;
		int y;
		public Point(int x, int y) {
			this.x=x;
			this.y=y;
		}
		
		@Override
		public String toString() {
			return "P["+this.x+","+this.y+"]";
		}
		
		@Override
		public int compareTo(Point o) {
			int dx = x-o.x;
			if (dx!=0) {
				return dx;
			}
			return y-o.y;
		}
	}
}
