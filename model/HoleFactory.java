package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HoleFactory {
	private static int columnBase = 'a';
	private static int lineBase = '1';
	
	private static class Pos {
		public int l;
		public int c;
		public Pos(int i, int j) {
			l = i;
			c = j;
		}
		
		public int getC() { return c; }
		public int getL() { return l; }
		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (l == ((Pos)o).getL() && c == ((Pos)o).getC()) {
				return true;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			return l + c;
		}
	}
	
	public static Set<IHole> generateBoardHoles(int[] badPos){
		Map <Pos, IHole> data = new HashMap <Pos, IHole>();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if(isIn(badPos,(i * 7 + j))) {
					continue;
				}
				
				IHole h = new Hole(computeName(i, j), i, j);
				data.put(new Pos(i, j), h);
				if (contains(data, new Pos(i - 1, j))) {
					data.get(new Pos(i - 1, j)).setNearHole(IHole.SOUTH, h);
					h.setNearHole(IHole.NORTH, data.get(new Pos(i - 1, j)));
				}
				if (contains(data, new Pos(i, j - 1))) {
					data.get(new Pos(i, j - 1)).setNearHole(IHole.EAST, h);
					h.setNearHole(IHole.WEST, data.get(new Pos(i, j - 1)));
				}
				
			}
		}
		return new HashSet<IHole> (data.values());
	}
	
	private static boolean contains (Map<Pos, IHole> m, Pos p) {
		Set<Pos> entry = m.keySet();
		for (Pos po : entry) {
			if(po.equals(p)) return true;
		}
		
		return false;
	}
	private static boolean isIn (int [] t, int x) {
		for (int i = 0; i < t.length; i++) {
			if (x == t[i]) {
				return true;
			}
		}
		return false;
	}
	
	private static String computeName (int i, int j) {
		int c = lineBase + j;
		int l = columnBase + i;
		return "" + (char) l + (char) c;
	}
}

