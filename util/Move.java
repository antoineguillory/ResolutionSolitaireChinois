package util;

import java.util.Set;

public class Move {
	private int start;
	private int end;
	private Set<Integer> removed;
	
	public Move(int s, int e, Set<Integer> r) {
		start = s;
		end = e;
		removed = r;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public Set<Integer> getRemoved() {
		return removed;
	}
	
	
}
