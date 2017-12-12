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


	public Set<IHole> getHoleSet() {
		return setHole;
	}
	
	public IBoard copie(){
		IBoard b = new Board();
		for(IHole h : this.setHole) {
			for(IHole nh : b.getHoleSet()) {
				if(nh.getXPos() == h.getXPos() && nh.getYPos() == h.getYPos()) {
					if(!h.pegIn()) nh.takePeg();
				}
			}
		}
		return b;
	}
	
	public boolean equals(Object o) {
		IBoard b = (IBoard) o;
		for(IHole h : this.setHole) {
			for(IHole nh : b.getHoleSet()) {
				if(nh.getXPos() == h.getXPos() && nh.getYPos() == h.getYPos()) {
					if(h.pegIn() != nh.pegIn()) return false;
				}
			}
		}
		return true;
	}

}
