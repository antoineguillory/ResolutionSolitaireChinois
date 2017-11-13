package ihm;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridJButton extends JButton {
	
	private Integer numerotation;
	
	public GridJButton(ImageIcon icn, Integer numerotation){
		super(icn);
		if(numerotation==null)
			throw new NullPointerException();
		this.numerotation = numerotation;
	}

	public Integer getNumerotation() {
		return numerotation;
	}

	public void setNumerotation(Integer numerotation) {
		this.numerotation = numerotation;
	}
}
