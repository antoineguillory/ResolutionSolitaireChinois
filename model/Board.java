package model;

import java.util.Set;

import util.Contract;

public class Board implements IBoard {
	
	//ATTRIBUTS
	
	private Set<IHole> setHole;
	//TODO
	
	//CONSTRUCTEURS
	
	public Board() {
		//TODO
	}
	
	public Board(int i) {
		//TODO
		
	}
	
	//METHODES

	@Override
	public Set<IHole> getHoleSet() {
		return setHole;
	}

}
