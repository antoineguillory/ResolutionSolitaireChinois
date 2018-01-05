package model;

import java.util.HashMap;
import java.util.Map;

import util.Contract;

public class Hole implements IHole {
	
	//ATTRIBUTS
	
	private boolean peg;
	private Map<Integer, IHole> voisin;
	private String pos;
	private int x;
	private int y;
	//CONSTRUCTEURS
	
	public Hole(String p, int x, int y) {
		Contract.checkCondition(p != null);
		voisin = new HashMap<Integer, IHole>();
		peg = true;
		pos = p;
		this.x = x;
		this.y = y;
	}
	
	//METHODES

	public boolean pegIn() {
		return peg;
	}


	public boolean nearHoleHere(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		
		return voisin.get(dir) != null;
	}


	public IHole getNearHole(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		
		return voisin.get(dir);
	}

	public String getPosition() {
		return pos;
	}

	public boolean possibleMove(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		if (pegIn()) {
			return false;
		}
		if (!(getNearHole(dir) != null && getNearHole(dir).getNearHole(dir) != null)) {
			return false;
		}

		IHole o = getNearHole(dir);
		IHole o2 = o.getNearHole(dir);
		return o.pegIn() && o2.pegIn();
	}

	 
	public boolean canMoveTo(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		if (!pegIn()) {
			return false;
		}
		if (!(getNearHole(dir) != null && getNearHole(dir).getNearHole(dir) != null)) {
			return false;
		}
		
		IHole o = getNearHole(dir);
		IHole o2 = o.getNearHole(dir);
		return o.pegIn() && !(o2.pegIn());
	}

	 
	public void putPeg() {
		Contract.checkCondition(! pegIn());

		peg = true;
	}

	 
	public void takePeg() {
		Contract.checkCondition(pegIn());

		peg = false;
	}

	 
	public void jumpTo(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		Contract.checkCondition(canMoveTo(dir));

		IHole o = getNearHole(dir);
		IHole o2 = o.getNearHole(dir);
		this.takePeg();
		o.takePeg();
		o2.putPeg();
	}

	 
	public void undoJump(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		Contract.checkCondition(pegIn());
		Contract.checkCondition(getNearHole(dir) != null 
				&& getNearHole(dir).getNearHole(dir) != null
				&& getNearHole(dir).pegIn() == false
				&& getNearHole(dir).getNearHole(dir).pegIn() == false);
		
		IHole o = getNearHole(dir);
		IHole o2 = o.getNearHole(dir);
		this.takePeg();
		o.putPeg();
		o2.putPeg();
	}

	 
	public void setNearHole(int dir, IHole h) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		Contract.checkCondition(h != null && h != this);

		voisin.put(dir, h);
		
	}
	


	public int getXPos() {
		return this.x;
	}

	public int getYPos() {
		return this.y;
	}

}
