package model;

/**
 * Interface : Path
 * 
 * 
 * 
 * Implante une représentation d'un chemin, qui va calculer lui même à partir des pions de son plateau,
 * toute les possibilités.
 * 
 * 
 * @inv
 * 
 * Le format du StringBuffer du chemin suit une règle précise :
 * -Chaque coups est constitué d'un ou de deux mouvement, et ces coups sont séparés par des ';'.
 * -Les mouvements sont symbolisé par la concaténation d'une position, d'une direction (représenté par N,E,S,W)
 *  	et la position d'arrivé du pion. Dans le cas d'un double mouvement, on n'écrit qu'une fois la position intermédiaire dans un saut.
 *  
 *  Exemple :  on réalise un double saut d'un peg de position a, vers un trou de position b au nord puis un trou de position c à l'est ; 
 *  puis on décide de réalisé un saut d'un peg de position d à un trou de position e au sud.
 *  On obtient le chemin suivant : "aNbEc;dSe"
 *  
 *  Il est évident qu'une position peut avoir une taille > 1, mais qu'elle n'a pas le droit de contenir les lettres N,E,S,W, et ;.
 *  
 * @cons
 * 
 * 	$DESC$ Un chemin qui possède son plateau, avec son trou de départ et son trou d'arrivée.
 * 	$ARGS$ [int i correspondant au type de plateau], String posdepart, String posarrive
 * 	$PRE$ (posdepart && posarive) appartiennent aux positions des trous du plateau [défini par i].
 *  $POST$
 *  	getBoard() != null;
 *  	getCurrentHole() == null;
 *  	getBestNb() == 33 [peut varier si on prend en compte les différents plateaux]
 *  	getBestMoves() == null;
 *  	getNb() == 0;
 *  	getMoves() == null;
 * 	    getPegOutNumber() == 1
 * 
 */
public interface IPath {
	
	/**
	 * Renvoi le plateau
	 */
	public IBoard getBoard();
	
	/**
	 * Renvoi le trou "courant"; càd le dernier où un peg a sauté dedans. L'intérêt
	 * est de pouvoir rapidement gérer le "retour arrière" qu'on fera souvent.
	 * Le trou en question a donc bien un peg à l'intérieur.
	 * 
	 */
	
	public IHole getCurrentHole();
	
	/**
	 * Renvoi le meilleur nombre de coup enregistré (33 de base, on peut imaginer que ce nombre se modifie avec
	 * le plateau, par rapport à la factory, ect..)  
	 */
	public int getBestNb();
	
	/**
	 * Renvoi le meilleur chemin enregistré, sous le format exprimé dans l'invariant.
	 * Si le meilleur chemin n'a pas été trouvé, on renvoi null
	 */
	public StringBuffer getBestMoves();
	
	/**
	 * Renvoi le Nb de coup utilisé pour arriver à ce point.
	 */
	public int getNb();
	
	/**
	 * Renvoi le chemin utilisé pour arriver à ce point. (Format précisé dans l'invariant)
	 */
	public StringBuffer getMoves();
	
	/**
	 * nombre de peg retiré du jeu qu'on incrémente une, deux fois pour chaque mouvement
	 * en fonction de si on fait un saut, double saut.
	 */
	public int getPegOutNb();
	
	/** 
	 * Algo principal que le Path va exécuter récursivement. Cela va calculer le meilleur chemin.
	 * Voici le principe :
	 * si getPegOutNb == 32 [ou différent en fonction du plateau]
	 * 		alors si getBestNb() > getNb() && getCurrentHole.getPosition = this.posarrive 
	 * 		modification du meilleur chemin(String Buffer)/meilleur nb
	 * 		return
	 * si getNb() > getBestNb()
	 * 		return;
	 * 
	 * Pour chaque trou,
	 * 		Pour chaque possibilités de coup dans ce trou
	 * 			incrémenter pegOutNb en fonction du nombre de saut du mouvement
	 * 			incrémenter Nb, modifier la Stringbuffer des mouvements
	 * 			faire le mouvement (utiliser les méthodes de Hole)
	 *			lancer computePath
	 *			décrémenter tout ce qui a été incr précedément dans cette boucle,
	 *			annuler le changement de la stringbuffer correspondant au chemin actuel.
	 */
	public void computePath();
	
	
	
}
