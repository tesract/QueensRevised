import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Util {
	public static void shuffleArrayInteger(Integer[] ar) {
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}

	public static void shuffleArray(int[] arr) {
		// If running on Java 6 or older, use `new Random()` on RHS here
		Random rnd = ThreadLocalRandom.current();
		for (int i = arr.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}
	
	final static Random random = ThreadLocalRandom.current();
	public static int rr(int size)
	{
		int r = random.nextInt(size);
		
		return size - 1 - (r*r/size);
	}	
	
	public static int r(int size)
	{
		return random.nextInt(size);
	}
	
	public static final int GCD( int a, int b ){
        // Deal with the degenerate case where values are Integer.MIN_VALUE
        // since -Integer.MIN_VALUE = Integer.MAX_VALUE+1
        if ( a == Integer.MIN_VALUE )
        {
            if ( b == Integer.MIN_VALUE )
                throw new IllegalArgumentException( "gcd() is greater than Integer.MAX_VALUE" );
            return 1 << Integer.numberOfTrailingZeros( Math.abs(b) );
        }
        if ( b == Integer.MIN_VALUE )
            return 1 << Integer.numberOfTrailingZeros( Math.abs(a) );

        a = Math.abs(a);
        b = Math.abs(b);
        if ( a == 0 ) return b;
        if ( b == 0 ) return a;
        int factorsOfTwoInA = Integer.numberOfTrailingZeros(a),
            factorsOfTwoInB = Integer.numberOfTrailingZeros(b),
            commonFactorsOfTwo = Math.min(factorsOfTwoInA,factorsOfTwoInB);
        a >>= factorsOfTwoInA;
        b >>= factorsOfTwoInB;
        while(a != b){
            if ( a > b ) {
                a = (a - b);
                a >>= Integer.numberOfTrailingZeros( a );
            } else {
                b = (b - a);
                b >>= Integer.numberOfTrailingZeros( b );
            }
        }
        return a << commonFactorsOfTwo;
    }
}
