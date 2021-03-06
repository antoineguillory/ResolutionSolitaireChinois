package util;

import java.util.HashMap;
import java.util.Map;
//Représente les différents types de plateau, on peut facilement rajouter des plateaux en modifiant badpositions et en rajoutant deux variables. 
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
	// Permet de faire le lien avec l'ihm, puisqu'on passe d'une string à une liste d'entiers.
	public static Map<String, Integer> bijection (Integer type) {
		int k = 1;
		Integer b[] = badpositions(type);
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
	
	public static Map<Integer, String> bijection2 (Integer type) {
		Map <String, Integer> m = bijection (type);
		Map <Integer, String> ret = new HashMap<Integer, String>();
		for(String s : m.keySet()) {
			ret.put(m.get(s), s);
		}
		return ret;
	}
	
	
	private static boolean inArrayint(Integer a, Integer t[]) {
		for (int i : t) {
			if (i == a) return true;
		}
		return false;
	}
}
