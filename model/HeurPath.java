package model;

import java.awt.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class HeurPath implements IHeurPath {
	//ATTRIBUTS
	public static int nbBoard = 1000;
	private IBoard board;
	private IHole curHole;
	private IHole last;
	private StringBuffer bestMv;
	private Set currentPool; //LISTE DE IBOARD
	private Set futurPool;	//PAREIL
	private Map <IBoard, Double> futurPoolHeur;
	private Map <IBoard, StringBuffer> moves;
	private Integer type;

	//CONSTRUCTEURS
	
	public HeurPath(int i, String start, String end) {
		IBoard b = new Board(i);
		type = i;
		for (IHole h: b.getHoleSet()) {
			if (h.getPosition().compareTo(start) == 0) {
				h.takePeg();
			}
			if (h.getPosition().compareTo(end) == 0) {
				last = h;
			}
		}
		this.futurPool = new HashSet();
		this.moves = new HashMap<IBoard, StringBuffer>();
		futurPool.add(b);
		curHole = null;
		StringBuffer s = new StringBuffer("");
		moves.put(b, s);
		this.futurPoolHeur = new HashMap<IBoard, Double>();
	}
	
	//METHODES

	
	public IBoard getBoard() {
		return board;
	}
	
	//La fonction d'heuristique prend un plateau et renvoi la somme des distances entre le peg "final" et tout les autres pegs
	public double fullBoardHeuristique(IBoard b) {
		double s = 0;
		for (IHole h : b.getHoleSet()) {
			if (h.pegIn()) {
				s += (Math.pow((last.getXPos() - h.getXPos()), 2) + Math.pow((last.getYPos() - h.getYPos()), 2));
			}
		}
		if (s == Double.NaN) return 0;
		return s;
	}

	
	public IHole getCurrentHole() {
		return curHole;
	}

	

//On décide d'intégré ou non le board b (avec sa  chaine s) dans le futurpool
	public void integrate (IBoard b, StringBuffer s) {
		double heuri = fullBoardHeuristique(b);
		if (!this.futurPoolHeur.keySet().isEmpty()) {
			for (IBoard bo :(Set <IBoard>) this.futurPool) {
				if (bo.equals(b)) return;
			}
			//Si on a de la place on ajoute juste le plateau sans plus de test.
			if (this.futurPool.size() < this.nbBoard) {
				IBoard cpy = b.copie(type);
				this.futurPoolHeur.put(cpy, heuri);
				this.moves.put(cpy, new StringBuffer(s));
				this.futurPool.add(cpy);
				return;
			}
			//Sinon on vérifie si il existe un plateau à l'heuristique moins bonne dans l'ensemble actuel
			//Si c'est le cas on ajoute b et on retire le board à l'heuristique la moins bonne.
			for(Double d : futurPoolHeur.values())  {
				if (heuri < d) {
					IBoard cpy = b.copie(type);
					this.futurPoolHeur.put(cpy, heuri);
					this.moves.put(cpy, new StringBuffer(s));
					this.futurPool.add(cpy);
					break;
				}
			}
			double max = 0;
			IBoard bmax = b;
			Iterator<IBoard> ite = futurPool.iterator();
			while (ite.hasNext()) {
				IBoard board = ite.next();
				if (futurPoolHeur.get(board) > max) {
					bmax = board;
					max = futurPoolHeur.get(board);
				}
			}
			futurPool.remove(bmax);
			futurPoolHeur.remove(bmax);
			moves.remove(bmax);
			
		} else {
			IBoard cpy = b.copie(type);
			this.futurPoolHeur.put(cpy, heuri);
			this.moves.put(cpy, new StringBuffer(s));
			this.futurPool.add(cpy);
		}
	}
	

	//Contrairement à Path, ici on utilise des boards différents.
	public void computePath(IBoard board) {
		StringBuffer curMv = this.moves.get(board);
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
							//On simule chaque coups possibles
							this.curHole = getTwiceHoleFrom(getTwiceHoleFrom(h, dir), dir2);
							IHole old = curHole;
							curMv.append(h.getPosition() 
									+ d 
									+ getTwiceHoleFrom(h, dir).getPosition()
									+ d2
									+ this.curHole.getPosition()
									+ ";");
							h.jumpTo(dir);
							getTwiceHoleFrom(h, dir).jumpTo(dir2);
							//On tente d'ajouter ce coup au pool des plateau futurs
							integrate(board, curMv);
							old.undoJump(reverseDir(dir2));
							getTwiceHoleFrom(h, dir).undoJump(reverseDir(dir));
							curMv = curMv.delete(curMv.length() - 9, curMv.length());
						}
					}
					
					IHole oldHole = this.curHole;
					this.curHole = getTwiceHoleFrom(h, dir);
					curMv.append(h.getPosition() 
							+ d 
							+ this.curHole.getPosition()
							+ ";");
					h.jumpTo(dir);
					integrate(board, curMv);
					getTwiceHoleFrom(h, dir).undoJump(reverseDir(dir));
					this.curHole = oldHole;
					curMv = curMv.delete(curMv.length() - 6, curMv.length());
				}
			}
		}
	}
	
	public void calculPath() {
		int i = 0;
		//Condition d'arrêt : trouver une solution 
		do {	
			if(this.futurPoolHeur.values().contains(new Double(0))) {
				for(IBoard b : futurPoolHeur.keySet()) {
					if (futurPoolHeur.get(b) == 0) {
						this.bestMv = moves.get(b);
						return;
					}
				}
			}
			
			this.futurPoolHeur = new HashMap<IBoard, Double>();
			// Seulement de l'affichage de donnée ici
			double min = 200;
			TreeSet <String> printmove = new TreeSet();
			for (IBoard b : (Set <IBoard>)futurPool) {
				double val = (fullBoardHeuristique(b));
				if (val < min) {
					min = val;
				}
				printmove.add(new String(moves.get(b)));
			}
			for(String s : printmove){
				System.out.println(s);
			}
			
			System.out.println(min);
			// Fin de l'affichage 
			this.currentPool = this.futurPool;
			System.out.println("i = " + i +" size currentpool " + currentPool.size());
			this.futurPool = new HashSet();
			Iterator boards = currentPool.iterator();
			while (boards.hasNext()) {
				//Pour chaque plateau du pool courant on calcule tout ses coups possibles.
				IBoard b = (IBoard) boards.next();
				computePath(b);
			}
			System.out.println("i = " + i +" size futurpool " + futurPool.size());
			i++;
		} while(!currentPool.isEmpty());
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


	public StringBuffer getBestMoves() {
		return this.bestMv;
	}
}
