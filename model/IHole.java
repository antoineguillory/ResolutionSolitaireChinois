package model;


/**
 * Interface : Hole
 *	
 * Implante les différents trous du plateau.
 * On stocke dans ces objets la presence de peg ou non, ainsi que le lien entre ce trou et ces voisins.
 * Il n'a pas la responsabilité de calculer lui même toute les possibilités du tableau.
 * <pre>
 * @cons
 *  $DESC$ : un trou avec un peg dedans, sans voisin.
 * 	$ARGS$ : STRING P
 * 	$PRE$ : P != NULL
 * 	$POST$ : getNearHole(d) == null (pour tout d) 
 * && pegIn() == true;
 *  </pre>
 */

public interface IHole {
	
	public static int NORTH = 1;
	public static int EAST = 2;
	public static int SOUTH = 3;
	public static int WEST = 4;
	
	/**
	 * pegIn()
	 *  Renvoi si, oui ou non, le peg est présent (on renvoi juste l'attribut)
	 */
	public boolean pegIn();
	
	/**
	 *  nearHoleHere (int dir): renvoi true si un trou voisin dans 
	 *  la direction dir est présent. False sinon.
	 */
	public boolean nearHoleHere(int dir);
	
	
	/**
	 * @pre : dir == NORTH || EAST || SOUTH || WEST
	 * 
	 * getNearHole(int dir) renvoi le trou dans la direction dir. Il peut tout à fait être null donc penser à 
	 * tester le retour de cette fonction ou d'utiliser nearHoleHere avant.
	 */
	public IHole getNearHole(int dir);
	
	
	public int getXPos();
	public int getYPos();
	/**
	 * getPosition renvoi juste une chaine de caractère correspondant à la position du trou sur le plateau.
	 * La position ne doit pas être calculée à chaque fois, elle doit être enregistrée dans un attribut au constructeur.
	 */
	public String getPosition();
	
	
	/**
	 *   @pre dir == NORTH || EAST || SOUTH || WEST
	 *   possibleMove (int dir):
	 *   Renvoi true si il est possible qu'un mouvement se fasse vers ce trou,
	 *   venant de dir.
	 *   Par exemple si dir == WEST, o emplacement avec un peg,
	 *   et h le trou qui appelle cette méthode, dans la configuration ooh
	 *   possibleMove(dir) renvoi true.
	 *   Il va de soi que si le trou n'est pas vide, tout appel de possibleMove renvoi false.
	 */
	
	public boolean possibleMove(int dir);
	
	/**
	 *  @pre : dir == NORTH || EAST || SOUTH || WEST
	 *  canMoveTo (int dir)
	 *  Renvoi vrai si il est possible que le peg de ce trou saute un autre peg 
	 *  dans une direction donnée.
	 *  exemple avec 'o' un trou avec un peg, 'x' un trou sans et 'h' le trou appellant la méthode,
	 *  la configuration x x h o x fait que h.cmt(EAST) == true et h.cmt(WEST) == false 
	 */
	
	public boolean canMoveTo (int dir);
	
	
	/**
	 * @pre : !pegIn()
	 * putPeg met un peg dans ce trou.
	 * @post : petIn()
	 */
	public void putPeg();
	
	/**
	 * @pre : pegIn()
	 * takePeg retire le peg de ce trou.
	 * @post : !petIn()
	 */
	public void takePeg();
	
	
	/**
	 * @pre : dir == NORTH || EAST || SOUTH || WEST
	 * 		&& canMoveTo(dir)
	 * jumpTo réalise un saut de ce trou à celui dans la direction dir.
	 * Ce trou est celui avec un peg dedans.
	 */
	public void jumpTo(int dir);
	
	/**
	 *  @pre : dir == NORTH || EAST || SOUTH || WEST
	 *   && un saut à été fait dans l'autre sens.
	 *   undoJump efface un saut c.jumpTo(SOUTH) avec c.getNearHole(SOUTH).getNearHole(SOUTH).undoJump(NORTH); 
	 *   Ce trou est celui dans lequel à été introduit un peg.
	 */
	public void undoJump(int dir);
	
	
	/**
	 * @pre : dir == NORTH || EAST || SOUTH || WEST && h != null && h != this
	 * setNearHole est une méthode d'initialisation, utilisée dans la Hole Factory.
	 * Elle permet d'assignée à un trou et une direction un certain trou.
	 * 
	 * @post : getNearHole(dir) == h && h.getNearHole(- dir) == this
	 */
	
	public void setNearHole(int dir, IHole h);
	
	
}
