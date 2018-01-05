package model;

public class Test {

	public static void main(String[] args) {
		IHeurPath p = new HeurPath(1, "d4", "d4");
		
    	p.calculPath();
    	System.out.println(p.getBestMoves());
		
	}

}
