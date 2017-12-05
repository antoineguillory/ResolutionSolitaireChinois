package model;

public class Path implements IPath {
	
	//ATTRIBUTS
	private IBoard board;
	private IHole curHole;
	private int bestNB;
	private StringBuffer bestMV;
	private int curNB;
	private StringBuffer curMV;
	private int pegOutNB;
	// ATTR à changer
	private String pStart;
	private String pEnd;
	
	//CONSTRUCTEURS
	
	public Path (int i, String start, String end) {
		board = new Board(i);
		curHole = null;//TODO avec le board
		bestNB = board.getHoleSet().size() + 1;
		bestMV = new StringBuffer("");
		curNB = 0;
		curMV = new StringBuffer("");
		pegOutNB = 1;
		
		this.pStart = start;
		this.pEnd = end;
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
		return pegOutNB;
	}

	@Override
	public void computePath() {
		if (getPegOutNb() == 32) {
			if (getBestNb() > getNb()
					&& getCurrentHole().getPosition() == pEnd) {
				this.bestMV = this.curMV;
				this.bestNB = this.curNB;
				return;
			}
		}
		if (getNb() >= getBestNb()) {
			return;
		}
		
		// TODO Recursivité
		for (IHole h : board.getHoleSet()) {
			for (int dir = IHole.NORTH; dir <= IHole.WEST; dir++) {
				if (h.possibleMove(dir)) {
					String d = "";
					switch (dir) {
					case IHole.NORTH:
						d = "N";
						break;
					case IHole.EAST:
						d = "E";
						break;
					case IHole.SOUTH:
						d = "S";
						break;
					case IHole.WEST:
						d = "W";
						break;
					}
					this.pegOutNB += 1;
					this.curNB += 1;
					StringBuffer savedcurMV = new StringBuffer(this.getMoves());
					this.curMV.append(h.getPosition() 
							+ d 
							+ h.getNearHole(dir).getNearHole(dir).getPosition());
					h.jumpTo(dir);
					computePath();
					this.pegOutNB -= 1;
					this.curNB -= 1;
					this.curMV = savedcurMV;
					// A vérifier avec des pincettes et
					// à compléter avec curHole
				}
			}
		}
	}

}
