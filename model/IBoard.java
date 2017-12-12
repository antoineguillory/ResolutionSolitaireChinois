package model;

import java.util.Set;
import model.IHole;
/**
 * Interface : Board
 * 
 * Génère les Holes à l'aide d'une Hole Factory,
 * fait le lien entre les holes et l'objet path auquel il appartient.
 * Aura beaucoup plus de sens quand on devra faire de l'heuristique, comparer des plateaux différents ect.
 * 
 * <pre>
 * @cons
 * 		$DESC$ un Board avec le schéma de pion "de base" offert par la Factory
 * 		$ARGS$ 
 * 		$PRE$
 * 		$POST$
 * 
 * @cons
 * 		$DESC$ un Board avec un schéma de pion basé sur le model i de la Factory
 * 		$ARGS$ int i
 * 		$PRE$ i correspond à un schema de la factory
 * 		$POST$ pareil qu'au dessus
 */

public interface IBoard {
	
	/**
	 * Tableau de base
	 */
	public static final int CLASSIC_TAB_NB = 1;
	public static final int[] BAD_POS_PRIMITIVE = {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
	
	
	public static final int LAST_TAB = 1;
	
	/**
	 * getHoleSet renvoi l'ensemble des pegs du IBoard.
	 * @inv : getHoleSet() != null
	 */
	public Set<IHole> getHoleSet();

	public IBoard copie();

	
	
	
	
}
