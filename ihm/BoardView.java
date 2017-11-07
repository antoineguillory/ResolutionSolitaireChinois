package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
						NFLFL.add(EndBtn);	
					}
					NFL.add(NFLFL);
				}
				MainPanel.add(NFL);
				this.initializeGrid();
				MainPanel.add(CGL, BorderLayout.NORTH);	
			}
		}
		MainWindow.add(MainPanel);
		MainWindow.pack();
	}
	
	private void initializeGrid(){
		GridLayout manager = new GridLayout(7,7);
		CGL = new JPanel(manager);{
			ArrayList<Integer> badPos= new ArrayList<Integer>();
			Integer[] badPosPrimitive = {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
			badPos.addAll(Arrays.asList(badPosPrimitive));
			Integer realNumerotation=1;
			for(Integer i = 0; i!=49;++i){
				if(badPos.contains(i)){
					JButton badBtn = new JButton("X");
					badBtn.setForeground(Color.RED);
					CGL.add(badBtn);
					continue;
				}
				JButton goodBtn = new JButton(realNumerotation.toString());
				goodBtn.setForeground(Color.GREEN);
				CGL.add(goodBtn);
				++realNumerotation;
			}
		}
	}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BoardView();
            }
        });
    }
}
