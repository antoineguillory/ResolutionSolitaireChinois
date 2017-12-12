package util;

import java.util.LinkedList;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList l = Wrapper.getMoves(Wrapper.sample, BoardsTypes.PLATEAU1);
		while(l.size() != 0) {
			if(l.getFirst() != null) {
				Move m = (Move) l.getLast();
				System.out.println(m.getStart() + " " + m.getEnd());
				l.removeLast();
			}
		}
	}

}
