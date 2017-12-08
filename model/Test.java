package model;

public class Test {

	public static void main(String[] args) {
		IPath p = new Path(1, "d4", "d4");
//		p.computePath();
//		System.out.println(p.getBestMoves());
		
		int i = 0;
		for (IHole h : p.getBoard().getHoleSet()) {
			i++;
			if (h.pegIn()) {
				System.out.println(i + " " + h.getPosition());
			} else {
				System.out.println(i + " " + h.getPosition() + " out");
			}
			for (int j = 1; j <= 4; j++) {
				if (h.nearHoleHere(j)) {
					System.out.println(" " + h.getNearHole(j).getPosition());
				}
			}
		}
		
//		p.computePath();
//		System.out.println("" + p.getBestNb() +"\n"
//			+ p.getNb() +"\n"
//			+ p.getPegOutNb() +"\n"
//			+ p.getBestMoves() +"\n"
//			+ p.getBoard() +"\n"
//			+ p.getCurrentHole() +"\n"
//			+ p.getMoves());
		
	}

}
