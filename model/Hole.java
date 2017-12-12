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

	@Override
	public boolean pegIn() {
		return peg;
	}

	@Override
	public boolean nearHoleHere(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		
		return voisin.get(dir) != null;
	}

	@Override
	public IHole getNearHole(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		
		return voisin.get(dir);
	}


	@Override
	public String getPosition() {
		return pos;
	}

	@Override
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

	@Override
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

	@Override
	public void putPeg() {
		Contract.checkCondition(! pegIn());

		peg = true;
	}

	@Override
	public void takePeg() {
		Contract.checkCondition(pegIn());

		peg = false;
	}

	@Override
	public void jumpTo(int dir) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		Contract.checkCondition(canMoveTo(dir));

		IHole o = getNearHole(dir);
		IHole o2 = o.getNearHole(dir);
		this.takePeg();
		o.takePeg();
		o2.putPeg();
	}

	@Override
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

	@Override
	public void setNearHole(int dir, IHole h) {
		Contract.checkCondition(1 <= dir && dir <= 4);
		Contract.checkCondition(h != null && h != this);

		voisin.put(dir, h);
		if (! h.nearHoleHere(reverseDir(dir))) {
			h.setNearHole(reverseDir(dir), this);
		}
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


	public int getXPos() {
		return this.x;
	}

	public int getYPos() {
		return this.y;
	}
}
