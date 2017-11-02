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
	 * getHoleSet renvoi l'ensemble des pegs du IBoard.
	 * @inv : getHoleSet() != null
	 */
	public Set<IHole> getHoleSet();
	
	
	
	
}
