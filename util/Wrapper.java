package util;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;



public class Wrapper {
	
	public static String sample = "d6Wd4;b5Sd5;c7Wc5;e7Nc7;d4Ed6;b4Sd4;c2Ec4;a3Sc3;a5Wa3;d3Nb3;c4Wc6;f3Nd3;e1We3;c1Se1;e4We2;e6We4;g5Ne5;g3Wg5;c7Ec5;a3Sc3;e1We3;d3Wd5Sf5;g5Ne5;e4We6Nc6;c6Ec4Ec2;c2Se2We4;f4Nd4;";
	//utilise ça pour tes tests, tant que le model craint
	
	public static LinkedList<Move> getMoves (String moves, int typeofBoard) {
		StringTokenizer st = new StringTokenizer(moves, ";");
		LinkedList<Move> l = new LinkedList<Move>();
		Map <String, Integer> m = BoardsTypes.bijection(typeofBoard);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if(s.length() > 6) {
				String start = s.substring(0, 2);
				String mid = s.substring(3, 5);
				String end = s.substring(6, 8);
				Set<Integer> set = new HashSet<Integer>();
				set.add(m.get(start));
				set.add(m.get(mid));
				Move entry = new Move(m.get(start), m.get(end), set);
				l.addFirst(entry);
			} else {
				String start = s.substring(0, 2);
				String end = s.substring(3, 5);
				Set<Integer> set = new HashSet<Integer>();
				set.add(m.get(start));
				int sta = m.get(start);
				int en = m.get(end);
				Move entry = new Move(sta, en, set);
				l.addFirst(entry);
			}
		}
		return l;
	}
}
