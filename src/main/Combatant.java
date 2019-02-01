package main;


public class Combatant {
	private String name;
	private int initiative;
	private int health;
	
	public Combatant(String name, int initiative, int health) {
		this.name = name;
		this.initiative = initiative;
		this.health = health;
	}

	// methods
	public String getName() {
		return name;
	}
	
	public int getInitiative() {
		return initiative;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int takeDamage(int damage) {
		this.health -= damage;
		if(this.health < 0) 
			this.health = 0;
		return this.health;
	}
}
