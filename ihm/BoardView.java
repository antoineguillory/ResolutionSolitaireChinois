package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
	private static final Integer[] BAD_POS_PRIMITIVE = {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
	
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
				private JLabel ChosenStart;
				private JLabel ChosenEnd;
			private JPanel SBLS;
				private JButton ResetBtn;
				private JButton ConfirmBtn;
				
	private HashMap<Integer, GridJButton> GridButtons;
	private ImageIcon Empty_Tile;
	private ImageIcon Filled_Tile;
	private ImageIcon Orange_Tile;
	private IHMState state;
		
	/*
	 * @author Antoine Guillory
	 */
	public BoardView(){
		state = IHMState.DO_NOTHING;
		initializeImages();
		initializeComponents();
		defineListeners();
	}
	
	private void initializeImages(){
		Empty_Tile = new ImageIcon("img/empty_tile.png");
		Filled_Tile = new ImageIcon("img/filled_tile.png");
		Orange_Tile = new ImageIcon("img/orange_tile.png");
	}
	
	private void initializeComponents(){
		MainWindow = new JFrame();{
			MainWindow.setVisible(true);
			MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MainPanel = new JPanel(new BorderLayout());{
				MainWindow.add(MainPanel);
				NFL = new JPanel(new FlowLayout(FlowLayout.CENTER));{
					MainPanel.add(NFL, BorderLayout.NORTH);
					BestShot = new JLabel("?");
					NFL.add(new JLabel("Meilleur coup : "));
					NFL.add(BestShot);
					NFLFL = new JPanel(new FlowLayout(FlowLayout.CENTER));{
						NFL.add(NFLFL);
						StartBtn = new JButton("Pos de départ");
						NFLFL.add(StartBtn);
						EndBtn = new JButton("Pos d'arrivée");
						NFLFL.add(EndBtn);	
					}
					NFL.add(NFLFL);
				}
				MainPanel.add(NFL, BorderLayout.NORTH);
				this.initializeGrid();
				MainPanel.add(CGL, BorderLayout.CENTER);
				SBL = new JPanel(new BorderLayout());{
					SBLN = new JPanel(); {
						SBLN.add(new JLabel("Depart choisi : "));
						ChosenStart = new JLabel("?");
						SBLN.add(ChosenStart);
						SBLN.add(new JLabel("Arrivée choisie : "));
						ChosenEnd = new JLabel("?");
						SBLN.add(ChosenEnd);
						SBL.add(SBLN, BorderLayout.NORTH);
					}
					SBLS = new JPanel(); {
						ResetBtn = new JButton("Reset");
						SBLS.add(ResetBtn);
						ConfirmBtn = new JButton("Confirm");
						SBLS.add(ConfirmBtn);
						SBL.add(SBLS, BorderLayout.SOUTH);
					}
				}
				MainPanel.add(SBL, BorderLayout.SOUTH);
				
			}
		}
		MainWindow.add(MainPanel);
		MainWindow.pack();
	}
	
	private void initializeGrid(){
		GridButtons = new HashMap<Integer, GridJButton>();
		GridLayout manager = new GridLayout(7,7);
		CGL = new JPanel(manager);{
			ArrayList<Integer> badPos= new ArrayList<Integer>();
			badPos.addAll(Arrays.asList(BAD_POS_PRIMITIVE));
			Integer realNumerotation=1;
			for(Integer i = 0; i!=49;++i){
				if(badPos.contains(i)){
					GridJButton badBtn = new GridJButton(Orange_Tile, 0);
					badBtn.setPreferredSize(new Dimension(30,30));
					badBtn.setForeground(Color.RED);
					badBtn.setBackground(Color.RED);
					CGL.add(badBtn);
					continue;
				}
				GridJButton goodBtn = new GridJButton(Filled_Tile, realNumerotation);
				goodBtn.setForeground(Color.GREEN);
				goodBtn.setPreferredSize(new Dimension(30,30));
				GridButtons.put(realNumerotation, goodBtn);
				GridButtons.get(realNumerotation).addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){						
						if(state==IHMState.SET_START){
							ChosenStart.setText(((GridJButton) e.getSource()).getNumerotation().toString());
							GridJButton pointedBtn = GridButtons.get(Integer.parseInt(ChosenStart.getText()));
							pointedBtn.setIcon(Empty_Tile);
						}
						if(state==IHMState.SET_END){
							ChosenEnd.setText(((GridJButton) e.getSource()).getNumerotation().toString());
						}
					}
				});
				CGL.add(goodBtn);
				++realNumerotation;
			}
		}
	}
    
	private void defineListeners() {
		StartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				state = IHMState.SET_START;
			}
		});
		EndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				state = IHMState.SET_END;
			}
		});
	}
	
	public HashMap<Integer, GridJButton> getButtons() {
		return GridButtons;
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
