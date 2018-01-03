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
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.BoardsTypes;
import util.Move;
import util.Wrapper;

public class BoardView {
	/*  
	 * IHM have to look like
	 * BL -> N -> FL -> (LBL , FL -> BTON BTON)
	 * 	  -> C -> GL(7x7) -> BTON
	 * 	  -> S -> BL-> N -> FL -> ( (LBL, JTFLD) , (LBL, JTFLD))
	 * 		   -> S -> BTON
	 * 
	 * 
	 */
	private int type;
	private Integer[] badposprimitive;
		
	private LinkedList<Move> movesToExplore;
	private LinkedList<Move> movesAlreadyExplored;
	
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
				private JButton nextStep;
				private JButton previousStep;
				
	private HashMap<Integer, GridJButton> GridButtons;
	private ImageIcon Application_Icon;
	private ImageIcon Empty_Tile;
	private ImageIcon Filled_Tile;
	private ImageIcon Orange_Tile;
	private ImageIcon End_Tile;
	private IHMState state;
		
	/*
	 * @author Antoine Guillory
	 */
	public BoardView(int type){
		this.type = type;
		badposprimitive = BoardsTypes.badpositions(type);
		state = IHMState.DO_NOTHING;
		movesToExplore = new LinkedList<Move>();
		movesAlreadyExplored = new LinkedList<Move>();

		initializeImages();
		initializeComponents();
		defineListeners();
        MainWindow.setIconImage(Application_Icon.getImage());
	}
	
	private void initializeImages(){
		Empty_Tile = new ImageIcon("img/empty_tile.png");
		Filled_Tile = new ImageIcon("img/filled_tile.png");
		Orange_Tile = new ImageIcon("img/orange_tile.png");
		End_Tile = new ImageIcon ("img/end_tile.png");
		Application_Icon = new ImageIcon("img/solitaire.png");
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
						previousStep = new JButton("Previous step");
						previousStep.setEnabled(false);
						SBLS.add(previousStep);
						nextStep = new JButton("Next step");
						nextStep.setEnabled(false);
						SBLS.add(nextStep);
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
			badPos.addAll(Arrays.asList(badposprimitive));
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
			but.setIcon(Filled_Tile);
			but.setState(IHMState.DO_NOTHING);
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
				resetImages();
				ChosenStart.setText("17");
				ChosenEnd.setText("23");
				GridJButton pointedEnd = GridButtons.get(23);
				pointedEnd.setIcon(End_Tile);
				pointedEnd.setState(IHMState.SET_END);
				GridJButton pointedStart = GridButtons.get(17);
				pointedStart.setIcon(Empty_Tile);
				pointedStart.setState(IHMState.SET_START);
				ConfirmBtn.setEnabled(false);
				movesToExplore = Wrapper.getMoves(Wrapper.sample, type);
				BestShot.setText(Integer.toString(movesToExplore.size()));
				nextStep.setEnabled(true);
				// Toute cette partie ci dessus est a virer quand on sera plus en mode test	
			}
		});
		
		nextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				Iterator<Move> it = movesToExplore.iterator();
				Iterator<Move> it2 = movesAlreadyExplored.iterator();
				System.out.println("ETAT PILE CURR");
				while(it.hasNext()){
					Move mv = it.next();
					System.out.println("{START : "+Integer.toString(mv.getStart())+
								 "{END : "+Integer.toString(mv.getEnd()));
				}
				System.out.println("ETAT PILE ALREADY");
				while(it2.hasNext()){
					Move mv = it2.next();
					System.out.println("{START : "+Integer.toString(mv.getStart())+
								 "{END : "+Integer.toString(mv.getEnd()));
				}
				
				if(!movesToExplore.isEmpty()){
					Move currMove = movesToExplore.removeLast();
					movesAlreadyExplored.addLast(currMove);
					for(Integer i : currMove.getRemoved()){
						System.out.println("NEXT : To remove(set)"+Integer.toString(i));
						GridButtons.get(i).setIcon(Empty_Tile);
					}
					System.out.println("NEXT : To add(end)"+ Integer.toString(currMove.getEnd()));
					GridButtons.get(currMove.getEnd()).setIcon(Filled_Tile);
					//MainWindow.repaint();
				}else{
					nextStep.setEnabled(false);
					previousStep.setEnabled(true);
				}
				
				if(!movesToExplore.isEmpty()){
					previousStep.setEnabled(true);
				}
			}
		});
		
		previousStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				Iterator<Move> it = movesToExplore.iterator();
				Iterator<Move> it2 = movesAlreadyExplored.iterator();
				System.out.println("ETAT PILE CURR");
				while(it.hasNext()){
					Move mv = it.next();
					System.out.println("{START : "+Integer.toString(mv.getStart())+
								 "{END : "+Integer.toString(mv.getEnd()));
				}
				System.out.println("ETAT PILE ALREADY");
				while(it2.hasNext()){
					Move mv = it2.next();
					System.out.println("{START : "+Integer.toString(mv.getStart())+
								 "{END : "+Integer.toString(mv.getEnd()));
				}
				
				if(!movesAlreadyExplored.isEmpty()){
					Move currMove = movesAlreadyExplored.removeLast();
					movesToExplore.addLast(currMove);
				  	for(Integer i : currMove.getRemoved()){
						System.out.println("PREVIOUS : To add(set) : "+ Integer.toString(i));
						GridButtons.get(i).setIcon(Filled_Tile);
					}
					System.out.println("PREVIOUS : To remove(end)"+ Integer.toString(currMove.getEnd()));
					GridButtons.get(currMove.getEnd()).setIcon(Empty_Tile);
					//MainWindow.repaint();
				}else{
					nextStep.setEnabled(true);
					previousStep.setEnabled(false);
				}
				if(!movesToExplore.isEmpty()){
					nextStep.setEnabled(true);
				}
			}
		});
		
		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				resetImages();
				ChosenEnd.setText("");
				ChosenStart.setText("");
				EndBtn.setEnabled(true);
				StartBtn.setEnabled(true);
				//reset des listes
				movesAlreadyExplored = new LinkedList<Move>();
				movesToExplore = new LinkedList<Move>();
				previousStep.setEnabled(false);
				nextStep.setEnabled(false);
				
				
				//DEBUG : ENSUITE A RETIRER
				ConfirmBtn.setEnabled(true);
			}
		});
	}
	
	
	public HashMap<Integer, GridJButton> getButtons() {
		return GridButtons;
	}
}
