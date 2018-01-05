package util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
/*
 * Représente les différents types de plateau utilisable, avec ce qu'il faut pour les utiliser.
 * Il est facilement étendable à d'autre plateaux. (mais uniquement 7x7 maximum)
 * 
 */

public class BoardsTypes {
	public final static int PLATEAU1 = 1;
	public final static int PLATEAU2 = 2;
	private static final Integer BadPos1[] =  {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
	private static final Integer BadPos2[]  = {0,1,5,6,7,13,35,41,42,43,47,48};
	
	public static Integer[] badpositions(Integer type) {
		switch(type) {
		case PLATEAU1:
			return BadPos1;
		case PLATEAU2:
			return BadPos2;
		default :
			return null;
		}
	}
  
	public static Map<String, Integer> bijection (Integer type) {
		int k = 1;
		Integer b[] = badpositions(type);
		Map<String,Integer> m = new HashMap<String, Integer>();
		TreeSet<String> set = new TreeSet<String>();
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 7; j++) {
				if (!inArrayint((i * 7) + j, b)){
					char y = (char) ('a' + i);
					char x = (char) ('1' + j);
					String s = "" + y + x;
					set.add(s);
					System.out.println(s);
				}
			}
		}
		int k = 1;
		while(!set.isEmpty()) {
			String s = set.pollFirst();
			m.put(s, k);
			k++;
		}
		return m;
	}
	
	
	private static boolean inArrayint(Integer a, Integer t[]) {
		for (int i : t) {
			if (i == a) return true;
		}
		return false;
	}
}
