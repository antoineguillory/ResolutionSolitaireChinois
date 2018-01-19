package ihm;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridJButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//ID par rapport a l'IHM
	private Integer numerotation;
	
	//Etat courant du bouton
	private IHMState state;

	//Contructeur
	public GridJButton(ImageIcon icn, Integer numerotation){
		super(icn);
		this.setOpaque(true);
		this.setBorderPainted(false);
		if(numerotation==null)
			throw new NullPointerException();
		state = IHMState.DO_NOTHING;
		this.numerotation = numerotation;
		Color col = new Color(255,128,0);
		this.setBackground(col);
		this.setForeground(col);
		this.repaint();
	}
	
	//Change l'icone courante
	public void setIcon(ImageIcon icn){
		super.setIcon(icn);
		Color col = new Color(255,128,0);
		this.setBackground(col);
		this.setBackground(col);
	}

	public Integer getNumerotation() {
		return numerotation;
	}
	
	public void setState(IHMState state){
		this.state = state;
	}
	
	public IHMState getState(){
		return state;
	}

	public void setNumerotation(Integer numerotation) {
		this.numerotation = numerotation;
	}
}
