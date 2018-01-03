package util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Wrapper {

	public static String sample = "d6Wd4;b5Sd5;c7Wc5;e7Nc7;d4Ed6;b4Sd4;c2Ec4;a3Sc3;a5Wa3;d3Nb3;c4Ec6;f3Nd3;e1Ee3;c1Se1;e4We2;e6We4;g5Ne5;g3Eg5;c7Wc5;a3Sc3;e1Ee3;d3Ed5Sf5;g5Ne5;e4Ee6Nc6;c6Wc4Wc2;c2Se2Ee4;f4Nd4;";
	// utilise ça pour tes tests, tant que le model craint

	public static LinkedList<Move> getMoves(String moves, int typeofBoard) {
		StringTokenizer st = new StringTokenizer(moves, ";");
		LinkedList<Move> l = new LinkedList<Move>();
		Map<String, Integer> m = BoardsTypes.bijection(typeofBoard);
		while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if(s.length() > 6) {
                String start = s.substring(0, 2);
                String dir1 = s.substring(2, 3);
                String mid = s.substring(3, 5);
                String dir2 = s.substring(5, 6);
                String end = s.substring(6, 8);
                Set<Integer> set = new HashSet<Integer>();
                set.add(m.get(start));
                set.add(m.get(dirmove(start, dir1)));
                set.add(m.get(mid));
                set.add(m.get(dirmove(mid, dir2)));
                int sta = m.get(start);
                int en = m.get(end);
                Move entry = new Move(sta, en, set);
                l.addFirst(entry);
			} else {
				String start = s.substring(0, 2);
				String dir1 = s.substring(2, 3);
				String end = s.substring(3, 5);
				Set<Integer> set = new HashSet<Integer>();
				set.add(m.get(start));
				String betw1 = (dirmove(start, dir1));
				set.add(m.get(betw1));
				int sta = m.get(start);
				int en = m.get(end);
				Move entry = new Move(sta, en, set);
				l.addFirst(entry);
			}
		}
		return l;
	}

	private static String dirmove(String s, String dir) {
		int ic1 = (int) s.charAt(0);
		int ic2 = (int) s.charAt(1);
		Character c1 = (char) ic1;
		Character c2 = (char) ic2;
		String ret;
		switch (dir) {
		case "N":
			ic1--;
			c1 = (char) ic1;
			ret = new String(c1.toString() + c2.toString() + "");
			return ret;
		case "E":
			ic2++;
			c2 = (char) ic2;
			ret = new String(c1.toString() + c2.toString() + "");
			return ret;
		case "W":
			ic2--;
			c2 = (char) ic2;
			ret = new String(c1.toString() + c2.toString() + "");
			return ret;
		case "S":
			ic1++;
			c1 = (char) ic1;
			ret = new String(c1.toString() + c2.toString() + "");
			return ret;
		default:
			return "";
		}
	}

}
