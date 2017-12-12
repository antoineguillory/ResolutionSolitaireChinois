package util;

import java.util.HashMap;
import java.util.Map;

public class BoardsTypes {
	public final static int PLATEAU1 = 1;
	public final static int PLATEAU2 = 2;
	private static final int BadPos1[] =  {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
	private static final int BadPos2[]  = {0,1,5,6,7,13,35,41,42,43,47,48};
	
	public static int[] badpositions(int type) {
		switch(type) {
		case PLATEAU1:
			return BadPos1;
		case PLATEAU2:
			return BadPos2;
		default :
			return null;
		}
	}
	
	public static Map<String, Integer> bijection (int type) {
		int k = 1;
		int b[] = badpositions(type);
		Map<String,Integer> m = new HashMap<String, Integer>();
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				if (!inArrayint((i * 7) + j, b)){
					char y = (char) ('a' + i);
					char x = (char) ('1' + j);
					String s = "" + y + x;
					m.put(s, k);
					System.out.println(s);
					k++;
				}
			}
		}
		return m;
	}
	
	
	private static boolean inArrayint(int a, int t[]) {
		for (int i : t) {
			if (i == a) return true;
		}
		return false;
	}
}
