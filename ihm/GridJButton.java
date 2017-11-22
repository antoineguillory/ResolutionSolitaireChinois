package ihm;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GridJButton extends JButton {
	
	private Integer numerotation;
	private IHMState state;
	
	public GridJButton(ImageIcon icn, Integer numerotation){
		super(icn);
		if(numerotation==null)
			throw new NullPointerException();
		state = IHMState.DO_NOTHING;
		this.numerotation = numerotation;
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
