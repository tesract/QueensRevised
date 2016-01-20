import java.util.ArrayList;

import org.jacop.constraints.PrimitiveConstraint;
import org.jacop.core.FailException;
import org.jacop.core.IntDomain;
import org.jacop.core.IntVar;
import org.jacop.core.IntervalDomain;
import org.jacop.core.Store;
import org.jacop.core.Var;

/**
 * Constraint X * C #= Z
 * 
 * Boundary consistency is used.
 * 
 * @author Krzysztof Kuchcinski and Radoslaw Szymanek
 * @version 3.1
 */

public class Colinear extends PrimitiveConstraint {

	static int idNumber = 1;

	public IntVar x;
	public IntVar z;
	public float a;
	public float b;

	public static String[] xmlAttributes = {"x", "a", "z", "b"};

	public Colinear(IntVar x, float a, IntVar z, float b) {

		assert (x != null) : "Variable x is null";
		assert (z != null) : "Variable z is null";

		numberId = idNumber++;
		numberArgs = 2;

		this.x = x;
		this.z = z;
		this.a = a;
		this.b = b;
	}

	@Override
	public ArrayList<Var> arguments() {

		ArrayList<Var> variables = new ArrayList<Var>(2);

		variables.add(x);
		variables.add(z);
		return variables;
	}

	@Override
	public void consistency (Store store) {

		if (a==0)
			z.domain.in(store.level, z, 0, 0);
		if (b==0)
			x.domain.in(store.level, z, 0, 0);

		if (a != 0)
		do {

		    store.propagationHasOccurred = false;
				
		    // Bounds for X
		    IntervalDomain xBounds = divIntBounds(z.min(), z.max(), a/b, a/b);
		    x.domain.in(store.level, x, xBounds);

		    // Bounds for Z
		    IntervalDomain zBounds = mulBounds(x.min(), x.max(), a/b, a/b);

		    z.domain.in(store.level, z, zBounds);
				
		} while (store.propagationHasOccurred);
	}

	@Override
	public void notConsistency (Store store) {

	    if (a != 0 && b!=0) {

		if ( x.singleton() ) 

		    z.domain.inComplement(store.level, z, (int)Math.round(x.value()*a/b));

		if ( z.singleton() ) {
		    IntervalDomain xBounds;

		    try {
			xBounds = divIntBounds(z.min(), z.max(), a/b, a/b);
		    } catch (FailException e) {
			// z/c does not produce integer value; nothing to do since inequality holds
			return;
		    }

		    x.domain.inComplement(store.level, x, xBounds.value());
		}

	    }
	    else
	    {
	    	if (a==0)
	    		z.domain.inComplement(store.level, z, 0);
	    	else if (b==0)
	    		x.domain.inComplement(store.level, x, 0);
	    }
	}

	@Override
	public boolean satisfied() {
		IntDomain Xdom = x.dom(), Zdom = z.dom();
		return Xdom.singleton() && Zdom.singleton()
		&& Xdom.min() * a == Zdom.min() * b;
	}

	@Override
	public boolean notSatisfied() {

	    return ! z.domain.isIntersecting(mulBounds(x.min(), x.max(), a/b, a/b));

	    // IntDomain Xdom = x.dom();
	    // IntDomain Zdom = z.dom();
	    // return Xdom.singleton() && Zdom.singleton() && Xdom.min() * c != Zdom.min();
	}



	@Override
	public int getConsistencyPruningEvent(Var var) {

		// If consistency function mode
		if (consistencyPruningEvents != null) {
			Integer possibleEvent = consistencyPruningEvents.get(var);
			if (possibleEvent != null)
				return possibleEvent;
		}
		return IntDomain.BOUND;
	}

	@Override
	public int getNestedPruningEvent(Var var, boolean mode) {

		// If consistency function mode
		if (mode) {
			if (consistencyPruningEvents != null) {
				Integer possibleEvent = consistencyPruningEvents.get(var);
				if (possibleEvent != null)
					return possibleEvent;
			}
			return IntDomain.BOUND;
		}

		// If notConsistency function mode
		else {
			if (notConsistencyPruningEvents != null) {
				Integer possibleEvent = notConsistencyPruningEvents.get(var);
				if (possibleEvent != null)
					return possibleEvent;
			}
			return IntDomain.GROUND;
		}

	}

	@Override
	public int getNotConsistencyPruningEvent(Var var) {

		// If notConsistency function mode
		if (notConsistencyPruningEvents != null) {
			Integer possibleEvent = notConsistencyPruningEvents.get(var);
			if (possibleEvent != null)
				return possibleEvent;
		}
		return IntDomain.GROUND;
		
	}

	@Override
	public void impose(Store store) {
		x.putModelConstraint(this, getConsistencyPruningEvent(x));
		z.putModelConstraint(this, getConsistencyPruningEvent(z));
		store.addChanged(this);
		store.countConstraint();
	}

	@Override
	public void removeConstraint() {
		x.removeConstraint(this);
		z.removeConstraint(this);
	}

	@Override
	public String toString() {

		return id() + " : XmulCeqZmulD(" + x + ", " + a + ", " + z +" ," +b+" )";
	}

	@Override
	public void increaseWeight() {
		if (increaseWeight) {
			x.weight++;
			z.weight++;
		}
	}
	
	public final static IntervalDomain mulBounds(int a, int b, float c, float d) {
		
		float low = Math.min(Math.min(a*c,a*d), Math.min(b*c,b*d));
		float high = Math.max(Math.max(a*c,a*d), Math.max(b*c,b*d));
		
		int min = (int)Math.round( Math.ceil( low ) );
		int max = (int)Math.round( Math.floor( high ));
		
		return new IntervalDomain(min, max);
	}
	
	public final static IntervalDomain divIntBounds(int a, int b, float c, float d) {
		int min=0, max=0;

		IntervalDomain result=null;

		if (a <= 0 && b >= 0 && c <= 0 && d >= 0) { // case 1
			min = IntDomain.MinInt;
			max = IntDomain.MaxInt;
			result = new IntervalDomain(min, max);
		}

		else if (c == 0 && d == 0 && (a > 0 || b < 0)) // case 2
			throw Store.failException;

		else if ( c < 0 && d > 0 && (a > 0 || b < 0)) { // case 3
			max = Math.max(Math.abs(a), Math.abs(b));
			min = -max;
			result = new IntervalDomain(min, max);	    
		}

		else if (c == 0 && d != 0 && (a > 0 || b < 0)) // case 4 a
			result = divIntBounds(a, b, 1, d);
		else if (c != 0 && d == 0 && (a > 0 || b < 0)) // case 4 b
			result = divIntBounds(a, b, c, -1);

		else { // if (c > 0 || d < 0) { // case 5
			float ac = (float)a/c, ad = (float)a/d, 
					bc = (float)b/c, bd = (float)b/d;
			float low = Math.min(Math.min(ac, ad), Math.min(bc, bd));
			float high = Math.max(Math.max(ac, ad), Math.max(bc, bd));
			min = (int)Math.round( Math.ceil( low ) );
			max = (int)Math.round( Math.floor( high ));
			if (min > max) throw Store.failException;
			result = new IntervalDomain(min, max);
		}

		return result;
	}
}
