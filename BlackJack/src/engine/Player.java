package engine;

import java.util.ArrayList;
import java.awt.Image;

public class Player extends Sprite{
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String name;
	private int money;
	private boolean male;
	private boolean control;
	private Persona character;
	
	public Player (String name, boolean male, int money, Persona character, boolean control){
		
		this.name = name;
		this.money = money;
		this.setMale(male);
		this.control = control;
		this.character = character;
	}
	
	// SETS
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCard(Card card){
		this.hand.add(card);
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setControl(boolean control) {
		this.control = control;
	}
	
	// GETS
	
	public String getName() {
		return name;
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public int getMoney() {
		return money;
	}
	
	public boolean getControl() {
		return control;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}
}
