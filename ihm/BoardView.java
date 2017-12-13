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
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import util.Move;

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
	public static final Integer[] BAD_POS_PRIMITIVE = {0,1,5,6,7,8,12,13,35,36,40,41,42,43,47,48};
		
	private LinkedList<Move> configuration;
	
	private JFrame MainWindow;
	private JPanel MainPanel;
			private JLabel BestShot;
				private JButton StartBtn;
				private JButton EndBtn;
		private JPanel CGL; 
				private JLabel ChosenStart;
				private JLabel ChosenEnd;
				private JButton ResetBtn;
				private JButton ConfirmBtn;
				
	private HashMap<Integer, GridJButton> GridButtons;
	private ImageIcon Empty_Tile;
	private ImageIcon Filled_Tile;
	private ImageIcon Orange_Tile;
	private ImageIcon End_Tile;
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
		End_Tile = new ImageIcon ("img/end_tile.png");
	}
	
	private void initializeComponents(){
		MainWindow = new JFrame();{
			MainWindow.setVisible(true);
			MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MainPanel = new JPanel(new BorderLayout());{
				MainWindow.add(MainPanel);
				JPanel NFL = new JPanel(new FlowLayout(FlowLayout.CENTER));{
					MainPanel.add(NFL, BorderLayout.NORTH);
					BestShot = new JLabel("?");
					NFL.add(new JLabel("Meilleur coup : "));
					NFL.add(BestShot);
					JPanel NFLFL = new JPanel(new FlowLayout(FlowLayout.CENTER));{
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
				JPanel SBL = new JPanel(new BorderLayout());{
					JPanel SBLN = new JPanel(); {
						SBLN.add(new JLabel("Depart choisi : "));
						ChosenStart = new JLabel("?");
						SBLN.add(ChosenStart);
						SBLN.add(new JLabel("Arrivée choisie : "));
						ChosenEnd = new JLabel("?");
						SBLN.add(ChosenEnd);
						SBL.add(SBLN, BorderLayout.NORTH);
					}
					JPanel SBLS = new JPanel(); {
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
					this.addBadButton();
					continue;
				}
				this.addGoodButton(realNumerotation);
				++realNumerotation;
			}
		}
		MainPanel.add(CGL, BorderLayout.CENTER);
	}
    
	private void addBadButton(){
		GridJButton badBtn = new GridJButton(Orange_Tile, 0);
		badBtn.setPreferredSize(new Dimension(30,30));
		CGL.add(badBtn);
	}
	
	private void addGoodButton(Integer id){
		GridJButton goodBtn = new GridJButton(Filled_Tile, id);
		goodBtn.setForeground(Color.GREEN);
		goodBtn.setPreferredSize(new Dimension(30,30));
		GridButtons.put(id, goodBtn);
		GridButtons.get(id).addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){						
				if(state==IHMState.SET_START){
					ChosenStart.setText(((GridJButton) e.getSource()).getNumerotation().toString());
					GridJButton pointedBtn = GridButtons.get(Integer.parseInt(ChosenStart.getText()));
					resetImages();
					pointedBtn.setIcon(Empty_Tile);
					pointedBtn.setState(state);
					state=IHMState.DO_NOTHING;
				}
				if(state==IHMState.SET_END){
					ChosenEnd.setText(((GridJButton) e.getSource()).getNumerotation().toString());
					GridJButton pointedBtn = GridButtons.get(Integer.parseInt(ChosenEnd.getText()));
					resetImages();
					pointedBtn.setIcon(End_Tile);
					pointedBtn.setState(state);
					state=IHMState.DO_NOTHING;
				}
			}
		});
		CGL.add(goodBtn);
	}
	
	private void resetImages() {
		for(GridJButton but: GridButtons.values()){
			if(but.getState()==IHMState.DO_NOTHING) 
				continue;
			else if(but.getState()==IHMState.SET_START && state==IHMState.SET_START)
				but.setIcon(Filled_Tile);
			else if(but.getState()==IHMState.SET_END && state==IHMState.SET_END)
				but.setIcon(Filled_Tile);
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
		
		ConfirmBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				StartBtn.setEnabled(false);
				EndBtn.setEnabled(false);
				System.out.println("DEBUG : pour l'instant le wrapper ne prend en compte qu'un coup de test ! départ et arrivée redéfinis.");
				//Pour les biens du test : départ et arrivée set manuellement
				initializeGrid(); //reset grid
				ChosenStart.setText("17");
				ChosenEnd.setText("23");
				GridJButton pointedEnd = GridButtons.get(23);
				resetImages();
				pointedEnd.setIcon(End_Tile);
				pointedEnd.setState(IHMState.SET_END);
				GridJButton pointedStart = GridButtons.get(17);
				pointedStart.setIcon(Empty_Tile);
				pointedStart.setState(IHMState.SET_START);
				// Toute cette partie ci dessus est a virer quand on sera plus en mode test	
				
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
