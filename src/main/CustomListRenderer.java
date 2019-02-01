package main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CustomListRenderer extends JLabel implements ListCellRenderer<Combatant> {
	
	Color clrBackground = Color.WHITE;
	Color clrSelected = Color.GREEN;
	
	@Override
    public Component getListCellRendererComponent(JList<? extends Combatant> list, Combatant combatant, int index,
        boolean isSelected, boolean cellHasFocus) {
          
		setOpaque(true);
		
        String name = combatant.getName();
        int initiative = combatant.getInitiative();
        int health = combatant.getHealth();
         
        setText(name + "  " + health);
        if(isSelected) {
        	setBackground(clrSelected);
        }else {
        	setBackground(clrBackground);
        }
        return this;
    }
}
