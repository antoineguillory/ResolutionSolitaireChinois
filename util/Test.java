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
				System.out.print(m.getStart());
						for(Integer i : m.getRemoved()) {
							System.out.print(" " + i);
						}
				System.out.print(" " + m.getEnd() + '\n');
				l.removeLast();
			}
		}
	}

}
