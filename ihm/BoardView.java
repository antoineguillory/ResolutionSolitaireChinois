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
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.HeurPath;
import model.IHeurPath;
import util.BoardsTypes;
import util.Move;
import util.Wrapper;

public class BoardView {

	// type du plateau
	private int type;

	// Positions incorrectes par rapport au plateau
	private Integer[] badposprimitive;

	// Coups restants a faire avant de "gagner"
	private LinkedList<Move> movesToExplore;

	// Coups déjà effectués
	private LinkedList<Move> movesAlreadyExplored;

	// Composants graphiques
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

	// Boutons de l'IHM
	private HashMap<Integer, GridJButton> GridButtons;

	private IHeurPath model;

	// Images a charger
	private ImageIcon Application_Icon;
	private ImageIcon Empty_Tile;
	private ImageIcon Filled_Tile;
	private ImageIcon Orange_Tile;
	private ImageIcon End_Tile;
	private IHMState state;

	/*
	 * @author Antoine Guillory
	 */
	// Constructeur.
	public BoardView(int type) {
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

	private void initializeImages() {
		Empty_Tile = new ImageIcon("img/empty_tile.png");
		Filled_Tile = new ImageIcon("img/filled_tile.png");
		Orange_Tile = new ImageIcon("img/orange_tile.png");
		End_Tile = new ImageIcon("img/end_tile.png");
		Application_Icon = new ImageIcon("img/solitaire.png");
	}

	// Initialise les composants graphiques
	private void initializeComponents() {
		MainWindow = new JFrame();
		{
			MainWindow.setVisible(true);
			MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MainPanel = new JPanel(new BorderLayout());
			{
				MainWindow.add(MainPanel);
				JPanel NFL = new JPanel(new FlowLayout(FlowLayout.CENTER));
				{
					MainPanel.add(NFL, BorderLayout.NORTH);
					BestShot = new JLabel("?");
					NFL.add(new JLabel("Meilleur coup : "));
					NFL.add(BestShot);
					JPanel NFLFL = new JPanel(new FlowLayout(FlowLayout.CENTER));
					{
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
				JPanel SBL = new JPanel(new BorderLayout());
				{
					JPanel SBLN = new JPanel();
					{
						SBLN.add(new JLabel("Depart choisi : "));
						ChosenStart = new JLabel("");
						SBLN.add(ChosenStart);
						SBLN.add(new JLabel("Arrivée choisie : "));
						ChosenEnd = new JLabel("");
						SBLN.add(ChosenEnd);
						SBL.add(SBLN, BorderLayout.NORTH);
					}
					JPanel SBLS = new JPanel();
					{
						ResetBtn = new JButton("Reset");
						SBLS.add(ResetBtn);
						ConfirmBtn = new JButton("Confirm");
						ConfirmBtn.setEnabled(false);
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

	// Initialise la grille en fonction du type de tableau
	private void initializeGrid() {
		GridButtons = new HashMap<Integer, GridJButton>();
		GridLayout manager = new GridLayout(7, 7);
		CGL = new JPanel(manager);
		{
			ArrayList<Integer> badPos = new ArrayList<Integer>();
			badPos.addAll(Arrays.asList(badposprimitive));
			Integer realNumerotation = 1;
			for (Integer i = 0; i != 49; ++i) {
				if (badPos.contains(i)) {
					this.addBadButton();
					continue;
				}
				this.addGoodButton(realNumerotation);
				++realNumerotation;
			}
		}
		MainPanel.add(CGL, BorderLayout.CENTER);
	}

	// Ajoute un bouton inutile (ne contenant pas de receptacle a pion)
	private void addBadButton() {
		GridJButton badBtn = new GridJButton(Orange_Tile, 0);
		badBtn.setPreferredSize(new Dimension(30, 30));
		CGL.add(badBtn);
	}

	// Ajoute un bouton
	private void addGoodButton(Integer id) {
		GridJButton goodBtn = new GridJButton(Filled_Tile, id);
		goodBtn.setForeground(Color.GREEN);
		goodBtn.setPreferredSize(new Dimension(30, 30));
		GridButtons.put(id, goodBtn);
		GridButtons.get(id).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (state == IHMState.SET_START) {
					ChosenStart.setText(((GridJButton) e.getSource()).getNumerotation().toString());
					GridJButton pointedBtn = GridButtons.get(Integer.parseInt(ChosenStart.getText()));
					resetImages();
					ConfirmBtn.setEnabled(false);
					pointedBtn.setIcon(Empty_Tile);
					pointedBtn.setState(state);
					state = IHMState.DO_NOTHING;
				}
				if (state == IHMState.SET_END) {
					ChosenEnd.setText(((GridJButton) e.getSource()).getNumerotation().toString());
					GridJButton pointedBtn = GridButtons.get(Integer.parseInt(ChosenEnd.getText()));
					pointedBtn.setIcon(End_Tile);
					ConfirmBtn.setEnabled(true);
					pointedBtn.setState(state);
					state = IHMState.DO_NOTHING;
				}
			}
		});
		CGL.add(goodBtn);
	}

	// Met a jour la grille sur son état initial
	private void resetImages() {
		for (GridJButton but : GridButtons.values()) {
			but.setIcon(Filled_Tile);
			but.setState(IHMState.DO_NOTHING);
		}
	}

	// Définit le comportement des boutons
	private void defineListeners() {
		StartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = IHMState.SET_START;
			}
		});
		EndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				state = IHMState.SET_END;
			}
		});

		ConfirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<Integer, String> localTranslation = BoardsTypes.bijection2(type);
				model = new HeurPath(type, localTranslation.get(Integer.parseInt(ChosenStart.getText())),
						localTranslation.get(Integer.parseInt(ChosenEnd.getText())));
				StartBtn.setEnabled(false);
				EndBtn.setEnabled(false);
				resetImages();
				ConfirmBtn.setEnabled(false);
				model.calculPath();
				try {
					System.out.println("SAMPLE : " + model.getBestMoves().toString());
					movesToExplore = Wrapper.getMoves(model.getBestMoves().toString(), type);
				} catch (NullPointerException exc) {
					JOptionPane.showMessageDialog(new JFrame("Echec d'Heuristique"),
							"L'Heuristique n'a pas trouvé de solutions...");

				} finally {
					BestShot.setText(Integer.toString(movesToExplore.size() - 1));
					nextStep.setEnabled(true);
				}
			}
		});

		nextStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Iterator<Move> it = movesToExplore.iterator();
				Iterator<Move> it2 = movesAlreadyExplored.iterator();
				System.out.println("ETAT PILE CURR");
				while (it.hasNext()) {
					Move mv = it.next();
					System.out.println(
							"{START : " + Integer.toString(mv.getStart()) + "{END : " + Integer.toString(mv.getEnd()));
				}
				System.out.println("ETAT PILE ALREADY");
				while (it2.hasNext()) {
					Move mv = it2.next();
					System.out.println(
							"{START : " + Integer.toString(mv.getStart()) + "{END : " + Integer.toString(mv.getEnd()));
				}

				if (!movesToExplore.isEmpty()) {
					Move currMove = movesToExplore.removeLast();
					movesAlreadyExplored.addLast(currMove);
					for (Integer i : currMove.getRemoved()) {
						System.out.println("NEXT : To remove(set)" + Integer.toString(i));
						GridButtons.get(i).setIcon(Empty_Tile);
					}
					System.out.println("NEXT : To add(end)" + Integer.toString(currMove.getEnd()));
					GridButtons.get(currMove.getEnd()).setIcon(Filled_Tile);
				} else {
					nextStep.setEnabled(false);
					previousStep.setEnabled(true);
				}

				if (!movesToExplore.isEmpty()) {
					previousStep.setEnabled(true);
				}
			}
		});

		previousStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Iterator<Move> it = movesToExplore.iterator();
				Iterator<Move> it2 = movesAlreadyExplored.iterator();
				System.out.println("ETAT PILE CURR");
				while (it.hasNext()) {
					Move mv = it.next();
					System.out.println(
							"{START : " + Integer.toString(mv.getStart()) + "{END : " + Integer.toString(mv.getEnd()));
				}
				System.out.println("ETAT PILE ALREADY");
				while (it2.hasNext()) {
					Move mv = it2.next();
					System.out.println(
							"{START : " + Integer.toString(mv.getStart()) + "{END : " + Integer.toString(mv.getEnd()));
				}

				if (!movesAlreadyExplored.isEmpty()) {
					Move currMove = movesAlreadyExplored.removeLast();
					movesToExplore.addLast(currMove);
					for (Integer i : currMove.getRemoved()) {
						System.out.println("PREVIOUS : To add(set) : " + Integer.toString(i));
						GridButtons.get(i).setIcon(Filled_Tile);
					}
					System.out.println("PREVIOUS : To remove(end)" + Integer.toString(currMove.getEnd()));
					GridButtons.get(currMove.getEnd()).setIcon(Empty_Tile);
					// MainWindow.repaint();
				} else {
					nextStep.setEnabled(true);
					previousStep.setEnabled(false);
				}
				if (!movesToExplore.isEmpty()) {
					nextStep.setEnabled(true);
				}
			}
		});

		ResetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetImages();
				ChosenEnd.setText("");
				ChosenStart.setText("");
				EndBtn.setEnabled(true);
				StartBtn.setEnabled(true);
				// reset des listes
				movesAlreadyExplored = new LinkedList<Move>();
				movesToExplore = new LinkedList<Move>();
				previousStep.setEnabled(false);
				nextStep.setEnabled(false);
			}
		});
	}

	// Récupère les boutons de l'IHM.
	public HashMap<Integer, GridJButton> getButtons() {
		return GridButtons;
	}
}
