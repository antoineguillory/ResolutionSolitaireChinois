package model;

import java.util.Set;

import util.BoardsTypes;
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
		
		setHole = HoleFactory.generateBoardHoles(convert(BoardsTypes.badpositions(i)));
	}
	
	//METHODES

	public Set<IHole> getHoleSet() {
		return setHole;
	}
	
	public IBoard copie(int i){
		IBoard b = new Board(i);
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
	
	//OUTIL
	//Pour passer d'un tableau d'Integer à un int[]
	private int [] convert (Integer[] t) {
		int [] ret = new int[49];
		int k = 0;
		for(Integer i : t) {
			ret[k] = i;
			k++;
		}
		return ret;
	}

}
