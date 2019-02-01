package main;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class DnD_Initiative_Tracker {
	
	static boolean DEBUG = true;
	
	static JButton newEncounter;
	static JButton startEncounter;
	static JButton endEncounter;
	static JButton nextTurn;
	static JButton previousTurn;
	
	static JTextField txtPlayerName;
	static JTextField txtPlayerInitiative;
	static JTextField txtPlayerHealth;
	
	static JLabel lblTurnNumber;
	static JLabel lblPlayerTurn;

	
	static JComboBox<String> cboDamage;
	
	static JList<Combatant> listview;
	static DefaultListModel<Combatant> listModel;
	
	static List<Combatant> orderedCombatantList;
	
	static int turnNumber = 1;
	
	public static void main(String[] args) {
        
		
		
		// Create the main window
		JFrame frame = new JFrame("InitiativeTracker");
		buildFrame(frame);
		 
		List<Combatant> combatantList = new ArrayList<Combatant>();
		
		if(DEBUG) {
			combatantList.add(new Combatant("Poop", 5, 10));
			combatantList.add(new Combatant("Peep", 14, 10));
			combatantList.add(new Combatant("Gobbo", 21, 10));
		}
		
		// Adds a new Combatant to the list
		newEncounter.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		           combatantList.add(new Combatant(txtPlayerName.getText(), 
		        		   				 Integer.parseInt(txtPlayerInitiative.getText()), 
		        		   				 Integer.parseInt(txtPlayerHealth.getText())
		        		   			)
		           );
		           
		    	   listModel.clear();
		           for(int i=0; i<combatantList.size(); i++) {
		        	   listModel.addElement(combatantList.get(i));
		           }
		           
		           txtPlayerName.setText("");
		           txtPlayerInitiative.setText("");
		           txtPlayerHealth.setText("");
		       }
		});
		
		// Orders the list of Combatants and starts the encounter
		startEncounter.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		    	   listModel.clear();
		    	   orderedCombatantList = orderList(combatantList);
		           for(int i=0; i<orderedCombatantList.size(); i++) {
		        	   listModel.addElement(orderedCombatantList.get(i));
		           }
		           listview.setSelectedIndex(0);
		           lblTurnNumber.setText("Turn: " + Integer.toString(turnNumber));
		           String whosTurn = orderedCombatantList.get(listview.getSelectedIndex()).getName();
		           lblPlayerTurn.setText(whosTurn + "'s turn");
		       }
		});
		
		// Orders the list of Combatants and starts the encounter
		endEncounter.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		    	   listModel.clear();
		    	   combatantList.clear();
		    	   orderedCombatantList.clear();
		    	   turnNumber = 0;
		    	   lblTurnNumber.setText("Turn: " + Integer.toString(turnNumber));
		           lblPlayerTurn.setText("Player's turn");
		       }
		});
		
		// Selects next index in the list
		nextTurn.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		    	   int index = listview.getSelectedIndex() + 1;
		    	   if(index == listview.getModel().getSize()) {
		    		   index = 0;
		    		   turnNumber++;
		    	   }
		    	   listview.setSelectedIndex(index);
		    	   lblTurnNumber.setText("Turn: " + Integer.toString(turnNumber));
		    	   String whosTurn = orderedCombatantList.get(listview.getSelectedIndex()).getName();
		           lblPlayerTurn.setText(whosTurn + "'s turn");
		       }
		});

		
    }
	
	// Order the list by initiative values
	public static ArrayList<Combatant> orderList(List<Combatant> combatantList){
		ArrayList<Combatant> orderedList = new ArrayList<Combatant>();
		
		while(combatantList.size() > 0) {
			int maximum = 0;
			int max_index = 0;
			for(int i=0; i<combatantList.size(); i++) {
				if(combatantList.get(i).getInitiative() > maximum) {
					maximum = combatantList.get(i).getInitiative();
					max_index = i;
				}
			}
			Combatant maxCombatant = combatantList.remove(max_index);
			orderedList.add(maxCombatant);
		}
		
		return orderedList;
	}
	
	// Add buttons/labels to frame
	public static void buildFrame(JFrame frame) {
		
        int windowWidth = 1000;
        int windowHeight = 800;

               
        // Add buttons
        newEncounter = new JButton("Add Combatant");
        startEncounter = new JButton("Start");
        endEncounter = new JButton("End");
        nextTurn = new JButton("Next");
        previousTurn = new JButton("Previous");

        
        newEncounter.setBounds(500, 50, 150, 40);
        startEncounter.setBounds(650, 50, 150, 40);
        endEncounter.setBounds(800, 50, 150, 40);
        nextTurn.setBounds(50, 700, 200, 50);
        
        frame.add(newEncounter);
        frame.add(startEncounter);        
        frame.add(endEncounter);
        frame.add(nextTurn);

        // Add TextFields and Label
        txtPlayerName = new JTextField();
        txtPlayerInitiative = new JTextField();
        txtPlayerHealth = new JTextField();
        
        JLabel lblPlayerName = new JLabel("Name");
        JLabel lblPlayerInitiative = new JLabel("Initiative");
        JLabel lblPlayerHealth = new JLabel("Health");
        lblTurnNumber = new JLabel("Turn: 0");
        lblPlayerTurn = new JLabel("Player's Turn");   
        
        txtPlayerName.setBounds(50, 50, 150, 40);
        txtPlayerInitiative.setBounds(200, 50, 150, 40);
        txtPlayerHealth.setBounds(350, 50, 150, 40);
        
        lblPlayerName.setBounds(50, 10, 50, 40);
        lblPlayerInitiative.setBounds(200, 10, 150, 40);
        lblPlayerHealth.setBounds(350, 10, 150, 40);
        lblTurnNumber.setBounds(300, 100, 100, 40);
        lblPlayerTurn.setBounds(300, 150, 100, 40);

        // Add Listview
        listModel = new DefaultListModel<>();
        listview = new JList<Combatant>(listModel);
        listview.setCellRenderer(new CustomListRenderer());
        listview.setBounds(50, 100, 200, 600);
        
        frame.add(txtPlayerName);
        frame.add(txtPlayerInitiative);
        frame.add(txtPlayerHealth);
        
        frame.add(lblPlayerName);
        frame.add(lblPlayerInitiative);
        frame.add(lblPlayerHealth);
        frame.add(lblTurnNumber);
        frame.add(lblPlayerTurn);

        frame.add(listview);
        
        
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
	}
	
}
