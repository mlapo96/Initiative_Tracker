package main;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GraphicsEnvironment;

import javax.swing.*;

public class DnD_Initiative_Tracker {
	
	// Populate list for debugging
	static boolean DEBUG = true;
	
	// Header Panel
	static JButton addCombatant;
	static JButton startEncounter;
	static JButton endEncounter;

	static JTextField txtPlayerName;
	static JTextField txtPlayerInitiative;
	static JTextField txtPlayerHealth;
	
	// List Panel	
	static JLabel lblTurnNumber;
	static JLabel lblPlayerTurn;
	static JList<Combatant> listview;
	static DefaultListModel<Combatant> listModel;
	static List<Combatant> orderedCombatantList;
	static JButton nextTurn;

	// Body Panel
	static JComboBox<String> cboDamage;
	static JTextField txtDamage;
	static JButton takeDamage;
	
	static JComboBox<String> cboHeal;
	static JTextField txtHeal;
	static JButton takeHeal;
	
	// Other
	static int turnNumber = 1;
	static Color clrHeader = Color.decode("#676F54");
	static Color clrList = Color.decode("#9CC69B");
	static Color clrBody = Color.decode("#D7F2BA");
	
	public static void main(String[] args) {
        
	    //String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	    //for ( int i = 0; i < fonts.length; i++ ){
	    //	System.out.println(fonts[i]);
	    //}
		
		// Create the main window
		JFrame frame = new JFrame("InitiativeTracker");
		buildFrame(frame);
		 
		List<Combatant> combatantList = new ArrayList<Combatant>();
		
		if(DEBUG) {
			combatantList.add(new Combatant("Player1", 14, 11));
			combatantList.add(new Combatant("Player2", 11, 14));
			combatantList.add(new Combatant("Player3", 21, 8));
		}
		
		// Adds a new Combatant to the list
		addCombatant.addActionListener(new ActionListener() {
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
		        	   
		        	   // adds combatant to the comboboxes
		        	   cboDamage.addItem(orderedCombatantList.get(i).getName());
		        	   cboHeal.addItem(orderedCombatantList.get(i).getName());
		           }
		           listview.setSelectedIndex(0);
		           lblTurnNumber.setText("Turn: " + Integer.toString(turnNumber));
		           String whosTurn = orderedCombatantList.get(listview.getSelectedIndex()).getName();
		           lblPlayerTurn.setText(whosTurn + "'s turn");       
		       }
		});
		
		// Clears the encounter and all lists
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
		
		// Calculates damage and new health on selected target
		takeDamage.addActionListener(new ActionListener() {
		       public void actionPerformed(ActionEvent ae){
		    	   changeHealth( cboDamage.getSelectedItem().toString(), Integer.parseInt(txtDamage.getText()) );
		       }
		});
		// Calculates healing and new health on selected target
		takeHeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				changeHealth( cboHeal.getSelectedItem().toString(), Integer.parseInt(txtHeal.getText())*-1 );
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
	
	// Damage or heal a character
	public static void changeHealth(String name, int value) {
 	    int selected = listview.getSelectedIndex();
	    listModel.clear();		    	   
		for(int i=0; i<orderedCombatantList.size(); i++) {
		    if(orderedCombatantList.get(i).getName() == name) {
 			   orderedCombatantList.get(i).takeDamage(value);
		    }
     	    listModel.addElement(orderedCombatantList.get(i));
 	    }
        listview.setSelectedIndex(selected);

	}
	
	// Add buttons/labels to frame
	public static void buildFrame(JFrame frame) {
		
        int windowWidth = 1000;
        int windowHeight = 800;
        
        List<JLabel> labelList = new ArrayList<JLabel>();
        
        // Panels within the frame --------------------------------------------
        JPanel pnlHeader = new JPanel();
    	JPanel pnlList = new JPanel();
    	JPanel pnlBody = new JPanel();
    	
    	pnlHeader.setLayout(null);
    	pnlList.setLayout(null);
    	pnlBody.setLayout(null);

    	pnlHeader.setBounds(0, 0, 1000, 150);
    	pnlList.setBounds(0, 150, 300, 650);
    	pnlBody.setBounds(300, 150, 700, 650);

    	pnlHeader.setBackground(clrHeader);
    	pnlList.setBackground(clrList);
    	pnlBody.setBackground(clrBody);
    
        // Add Header Panel ---------------------------------------------------
        addCombatant = new JButton("Add Combatant");
        startEncounter = new JButton("Start");
        endEncounter = new JButton("End");
        txtPlayerName = new JTextField();
        txtPlayerInitiative = new JTextField();
        txtPlayerHealth = new JTextField();       
        JLabel lblPlayerName = new JLabel("Name");
        JLabel lblPlayerInitiative = new JLabel("Initiative");
        JLabel lblPlayerHealth = new JLabel("Health");
        
        lblPlayerName.setBounds(50, 10, 50, 40);
        lblPlayerInitiative.setBounds(200, 10, 150, 40);
        lblPlayerHealth.setBounds(350, 10, 150, 40);
        txtPlayerName.setBounds(50, 50, 150, 40);
        txtPlayerInitiative.setBounds(200, 50, 150, 40);
        txtPlayerHealth.setBounds(350, 50, 150, 40);
        
        addCombatant.setBounds(500, 50, 150, 40);
        startEncounter.setBounds(650, 50, 150, 40);
        endEncounter.setBounds(800, 50, 150, 40);
        
        // adding labels to labelList
        labelList.add(lblPlayerName);
        labelList.add(lblPlayerInitiative);
        labelList.add(lblPlayerHealth);

        // add to header panel
        pnlHeader.add(txtPlayerName);
        pnlHeader.add(txtPlayerInitiative);
        pnlHeader.add(txtPlayerHealth); pnlHeader.add(addCombatant);
        pnlHeader.add(startEncounter);        
        pnlHeader.add(endEncounter);
                 
        pnlHeader.add(lblPlayerName);
        pnlHeader.add(lblPlayerInitiative);
        pnlHeader.add(lblPlayerHealth);
        
        // Add List Panel ---------------------------------------------------
        listModel = new DefaultListModel<>();
        listview = new JList<Combatant>(listModel);
        listview.setCellRenderer(new CustomListRenderer());
        nextTurn = new JButton("Next");
        lblTurnNumber = new JLabel("Turn: 0");
        lblPlayerTurn = new JLabel("Player's Turn"); 
        
        listview.setBounds(50, 50, 200, 450);

        listview.setLayout(null);
        nextTurn.setBounds(50, 530, 200, 40);
        lblTurnNumber.setBounds(50, 10, 100, 40);
        lblPlayerTurn.setBounds(130, 10, 150, 40);

        // adding labels to labelList
        labelList.add(lblTurnNumber);
        labelList.add(lblPlayerTurn);

        // add to list panel
        pnlList.add(listview);
        pnlList.add(nextTurn);
        pnlList.add(lblPlayerTurn);
        pnlList.add(lblTurnNumber);
        
        // Add Body Panel ---------------------------------------------------
        cboDamage = new JComboBox<String>();
        txtDamage = new JTextField();
        takeDamage = new JButton("Damage");       
        cboHeal = new JComboBox<String>();
        txtHeal = new JTextField();
        takeHeal = new JButton("Heal");
        
        JLabel lblSelectCharacter = new JLabel("Select Player");
        JLabel lblValue = new JLabel("Value");
        JLabel lblAction = new JLabel("Action");

        cboDamage.setBounds(50, 50, 150, 50);
        txtDamage.setBounds(225, 50, 50, 50);
        takeDamage.setBounds(300, 50, 100, 50);       
        cboHeal.setBounds(50, 150, 150, 50);
        txtHeal.setBounds(225, 150, 50, 50);
        takeHeal.setBounds(300, 150, 100, 50);
        
        lblSelectCharacter.setBounds(50, 15, 150, 40);
        lblValue.setBounds(225, 15, 50, 40);
        lblAction.setBounds(300, 15, 100, 40);
    
        // adding labels to labelList
        labelList.add(lblSelectCharacter);
        labelList.add(lblValue);
        labelList.add(lblAction);
        
        // add to body panel
        pnlBody.add(takeDamage);
        pnlBody.add(txtDamage);
        pnlBody.add(cboDamage);
        pnlBody.add(cboHeal);
        pnlBody.add(txtHeal);
        pnlBody.add(takeHeal);
        pnlBody.add(lblSelectCharacter);
        pnlBody.add(lblValue);
        pnlBody.add(lblAction);
          
        // Set Font ------------------------------------------------------
        Font fntHeader = new Font(Font.SANS_SERIF, Font.BOLD, 17);
        for(int i=0; i<labelList.size(); i++) {
        	labelList.get(i).setFont(fntHeader);
        }
        
        // Add all panels to the main frame ------------------------------
        frame.add(pnlHeader, BorderLayout.NORTH);
    	frame.add(pnlList, BorderLayout.WEST);
    	frame.add(pnlBody, BorderLayout.WEST);
    	
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
	}
	
}
