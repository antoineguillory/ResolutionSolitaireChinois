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
	private String pEnd;
	
	//CONSTRUCTEURS
	
	public Path (int i, String start, String end) {
		board = new Board(i);
		for (IHole h: board.getHoleSet()) {
			if (h.getPosition().compareTo(start) == 0) {
				h.takePeg();
			}
		}
		curHole = null;
		bestNB = 20;
		bestMV = new StringBuffer("");
		curNB = 0;
		curMV = new StringBuffer("");
		pegOutNB = 1;
		this.pEnd = end;
	}
	
	//METHODES

	
	public IBoard getBoard() {
		return board;
	}

	
	public IHole getCurrentHole() {
		return curHole;
	}

	
	public int getBestNb() {
		return bestNB;
	}

	
	public StringBuffer getBestMoves() {
		return bestMV;
	}

	
	public int getNb() {
		return curNB;
	}

	
	public StringBuffer getMoves() {
		return curMV;
	}

	
	public int getPegOutNb() {
		return pegOutNB;
	}

	// Ici on teste toute les possibilités du plateau récursivement
	public void computePath() {
		if (getNb() >= getBestNb()) {
			return;
		}
		//Condition d'arrêt
		if (getPegOutNb() == 32) {
			System.out.println(getMoves());
			if (getBestNb() > getNb()
					&& getCurrentHole().getPosition() == pEnd) {
				this.bestMV = this.curMV;
				this.bestNB = this.curNB;
				
				return;
			}
		}
		//Pour chaque trou vide on teste un coup
		for (IHole h : board.getHoleSet()) {
			for (int dir = IHole.NORTH; dir <= IHole.WEST; dir++) {
				if (h.canMoveTo(dir)) {
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
					// + double coup
					for (int dir2 = IHole.NORTH; dir2 <= IHole.WEST; dir2++) {
						IHole dirh = this.getTwiceHoleFrom(h, dir);
						if (	dirh.getNearHole(dir2) != null && this.getTwiceHoleFrom(dirh, dir2) != null &&
								dirh.getNearHole(dir2).pegIn() && !this.getTwiceHoleFrom(dirh, dir2).pegIn()) {
							String d2 = "";
							switch (dir2) {
							case IHole.NORTH:
								d2 = "N";
								break;
							case IHole.EAST:
								d2 = "E";
								break;
							case IHole.SOUTH:
								d2 = "S";
								break;
							case IHole.WEST:
								d2 = "W";
								break;
							}
							//Ici on simule un coup en modifiant l'état du plateau, le nombre de coup actuel
							this.pegOutNB += 2;
							this.curNB += 1;
							this.curHole = getTwiceHoleFrom(getTwiceHoleFrom(h, dir), dir2);
							IHole old = curHole;
							this.curMV.append(h.getPosition() 
									+ d 
									+ getTwiceHoleFrom(h, dir).getPosition()
									+ d2
									+ this.curHole.getPosition()
									+ ";");
							h.jumpTo(dir);
							getTwiceHoleFrom(h, dir).jumpTo(dir2);
							//On relance l'appel récursif (plus de détail dans le dossier)
							computePath();
							old.undoJump(reverseDir(dir2));
							getTwiceHoleFrom(h, dir).undoJump(reverseDir(dir));
							this.curHole = h;
							this.pegOutNB -= 2;
							this.curNB -= 1;
							this.curMV = this.curMV.delete(curMV.length() - 9, curMV.length());
						}
					}
					
					this.pegOutNB += 1;
					this.curNB += 1;
					IHole oldHole = this.curHole;
					this.curHole = getTwiceHoleFrom(h, dir);
					this.curMV.append(h.getPosition() 
							+ d 
							+ this.curHole.getPosition()
							+ ";");
					h.jumpTo(dir);
					computePath();
					getTwiceHoleFrom(h, dir).undoJump(reverseDir(dir));
					this.curHole = oldHole;
					this.pegOutNB -= 1;
					this.curNB -= 1;
					this.curMV = this.curMV.delete(curMV.length() - 6, curMV.length());
				}
			}
		}
	}
	
	//OUTILS
	
	private IHole getTwiceHoleFrom(IHole h, int dir) {
		return h.getNearHole(dir).getNearHole(dir);
	}
	
	private int reverseDir(int dir) {
		switch (dir) {
		case IHole.NORTH:
			dir = IHole.SOUTH;
			break;
		case IHole.EAST:
			dir = IHole.WEST;
			break;
		case IHole.SOUTH:
			dir = IHole.NORTH;
			break;
		case IHole.WEST:
			dir = IHole.EAST;
			break;
		}
		return dir;
	}

//	private boolean checkPos(String s) {
//		for (IHole h : board.getHoleSet()) {
//			if (h.getPosition() == s) {
//				return true;
//			}
//		}
//		return false;
//	}
}
