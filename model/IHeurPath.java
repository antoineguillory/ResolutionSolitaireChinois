package model;

public interface IHeurPath {

	
	/**
	 * Renvoi le meilleur chemin enregistré, sous le format exprimé dans l'invariant.
	 * Si le meilleur chemin n'a pas été trouvé, on renvoi null
	 */
	public StringBuffer getBestMoves();
	

	
	
	public void calculPath();
	
	
}
