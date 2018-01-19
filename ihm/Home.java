package ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Home {
	
	private JFrame mainPanel;
	
	//Liste des boutons de modes
	private ArrayList<JButton> types;
	
	public Home(){
		
		this.mainPanel = new JFrame("Resolution de solitaire chinois");
		this.types     = new ArrayList<JButton>();
		
		mainPanel.setVisible(true);
		mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton type1 = new JButton("Type 1");
		JButton type2 = new JButton("Type 2");
		
		//Si appui démarrage en plateau 1
		type1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new BoardView(1);
			}
		});
		
		//Si appui démarrage en plateau 2
		type2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new BoardView(2);
			}
		});
		
		types.add(type1);
		types.add(type2);
		
		//Place components
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(JButton b : types){
			p.add(b);
		}
		mainPanel.add(p);
		mainPanel.pack();

	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Home();
            }
        });
    }
}
