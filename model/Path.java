package model;

import util.Contract;

public class Path implements IPath {
	
	//ATTRIBUTS
	private IBoard board;
	private IHole curHole;
	private int bestNB;
	private StringBuffer bestMV;
	private int curNB;
	private StringBuffer curMV;
	private int pegOut;
	// ATTR à changer
	private String start;
	private String end;
	
	//CONSTRUCTEURS
	
	public Path (int i, String start, String end) {
		board = new Board(i);
		curHole = null;
		bestNB = board.getHoleSet().size() + 1;
		bestMV = null;
		curNB = 0;
		curMV = null;
		pegOut = 1;
		
		this.start = start;
		this.end = end;
	}
	
	//METHODES

	@Override
	public IBoard getBoard() {
		return board;
	}

	@Override
	public IHole getCurrentHole() {
		return curHole;
	}

	@Override
	public int getBestNb() {
		return bestNB;
	}

	@Override
	public StringBuffer getBestMoves() {
		return bestMV;
	}

	@Override
	public int getNb() {
		return curNB;
	}

	@Override
	public StringBuffer getMoves() {
		return curMV;
	}

	@Override
	public int getPegOutNb() {
		return pegOut;
	}

	@Override
	public void computePath() {
		if (getPegOutNb() == 32) {
			if (getBestNb() > getNb()
					&& getCurrentHole().getPosition() == end) {
				//TODO Modif du meilleur chemin et meilleur nb
				return;
			}
		}
		if (getNb() >= getBestNb()) {
			return;
		}
		
		// TODO Recursivité
	}

}
