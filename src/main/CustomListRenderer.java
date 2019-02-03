package main;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CustomListRenderer extends JLabel implements ListCellRenderer<Combatant> {
	
	private static final long serialVersionUID = 1L;
	Color clrBackground = Color.WHITE;
	Color clrSelected = Color.GREEN;
	Color clrDead = Color.RED;
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Combatant> list, Combatant combatant, int index,
		boolean isSelected, boolean cellHasFocus) {
        
		setOpaque(true);
		
        String name = combatant.getName();
        int health = combatant.getHealth();
         
        setText(name + "  " + health);
        if(isSelected) {
        	setBackground(clrSelected);
        }else if(health == 0){
        	setBackground(clrDead);
        }else {
        	setBackground(clrBackground);
        }
        return this;
    }
}
