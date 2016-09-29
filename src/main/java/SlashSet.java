import java.util.BitSet;

public class SlashSet {
	BitSet fslash;
	BitSet bslash;
	int size;

	public SlashSet(int size) {
		fslash=new BitSet(size*2+1);
		bslash=new BitSet(size*2+1);
		this.size=size;
	}
	
	public SlashSet(SlashSet other) {
		this.size=other.size;
		this.fslash=(BitSet)other.fslash.clone();
		this.bslash=(BitSet)other.bslash.clone();
	}
	
	public boolean Add(int x, int y) {
		int d1 = y - x + size;
		int d2 = y - (size - x) + size;

		if (fslash.get(d1) || bslash.get(d2)) {
			return false;
		}
		
		fslash.set(d1);
		bslash.set(d2);
		
		return true;
	}
	
}
