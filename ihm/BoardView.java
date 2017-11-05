package ihm;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class BoardView {
	/* REMINDER 
	 * 
	 * IHM have to look like
	 * BL -> N -> FL -> (LBL , FL -> BTON BTON)
	 * 	  -> C -> GL(7x7) -> BTON
	 * 	  -> S -> BL-> N -> FL -> ( (LBL, JTFLD) , (LBL, JTFLD))
	 * 		   -> S -> BTON
	 * 
	 * 
	 */
	
	private JFrame MainWindow;
	private JPanel MainPanel;
		private JPanel NFL;
			private JLabel BestShot;
			private JPanel NFLFL;
				private JButton StartBtn;
				private JButton EndBtn;
		private JPanel CGL; 
		private JPanel SBL;
			private JPanel SBLN;
			private JPanel SBLS;
		
	/*
	 * @author Antoine Guillory
	 */
	public BoardView(){
		initializeComponents();
	}
	
	private void initializeComponents(){
		
		MainWindow = new JFrame();{
			MainWindow.setVisible(true);
			MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MainPanel = new JPanel(new BorderLayout());{
				MainWindow.add(MainPanel);
				NFL = new JPanel(new FlowLayout(FlowLayout.CENTER));{
					MainPanel.add(NFL, BorderLayout.NORTH);
					BestShot = new JLabel();
					NFL.add(new JLabel("Meilleur coup : "));
					NFL.add(BestShot);
					NFLFL = new JPanel(new FlowLayout(FlowLayout.TRAILING));{
						NFL.add(NFLFL);
						StartBtn = new JButton("Pos de départ");
						NFLFL.add(StartBtn);
						EndBtn = new JButton("Pos d'arrivée");
					}
				}
				this.initializeGrid();
				MainPanel.add(CGL, BorderLayout.NORTH);
				
			}
		}	
	}
	
	private void initializeGrid(){
		GridLayout manager = new GridLayout(7,7);
		CGL = new JPanel(manager);{
			ArrayList<Integer> badPos= new ArrayList<Integer>();
			Integer[] badPosPrimitive = {1,2,6,7,8,9,13,14,36,37,41,42,43,44,48,49};
			badPos.addAll(Arrays.asList(badPosPrimitive));
			Integer realNumerotation=1;
			for(Integer i = 0; i!=49;++i){
				if(badPos.contains(i)){
					CGL.add(new JButton("X"));
					continue;
				}
				CGL.add(new JButton(realNumerotation.toString()));
				++realNumerotation;
			}
		}
	}
}
