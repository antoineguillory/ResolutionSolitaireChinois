package model;

import java.util.Set;

import util.Contract;

public class Board implements IBoard {
	
	//ATTRIBUTS
	
	private Set<IHole> setHole;
	
	//CONSTRUCTEURS
	
	public Board() {
		setHole = HoleFactory.generateBoardHoles(IBoard.BAD_POS_PRIMITIVE);
	}
	
	public Board(int i) {
		Contract.checkCondition(1 <= i && i <= IBoard.LAST_TAB);
		
		switch (i) {
		case IBoard.CLASSIC_TAB_NB:
			setHole = HoleFactory.generateBoardHoles(IBoard.BAD_POS_PRIMITIVE);
			break;
		}
	}
	
	//METHODES

	@Override
	public Set<IHole> getHoleSet() {
		return setHole;
	}

}
