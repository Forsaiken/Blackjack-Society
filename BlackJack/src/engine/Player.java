package engine;

import java.util.ArrayList;

import global.Path;
import global.Settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Player extends Sprite{
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String name;
	private int money;
	private boolean male;
	private boolean control;
	private Persona character;
	private int position;
	
	// VARIABLES - BLACKJACK
	
	private ArrayList<ArrayList<Card>> bjHand = new ArrayList<ArrayList<Card>>();
	
	private Sprite bjRect;
	private Sprite bjName;
	private Sprite bjCharacter;
	private Sprite bjBackCharacter;
	
	public Player (String name, boolean male, int money, Persona character, boolean control){
		
		this.name = name;
		this.money = money;
		this.setMale(male);
		this.control = control;
		this.character = character;
	}
	 
	// ACTIONS
	
	public void blackjack(Graphics g) {
		
		bjRect.draw(g);
		bjName.draw(g);
		bjBackCharacter.draw(g);
		bjCharacter.draw(g);
		
	}
	
	// SET - BASIC PROPERTIES
	
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
	
	public void setPosition(int position) {
		this.position = position;
	}
	
	// SET - BLACKJACK PROPERTIES
	
	public void setToBlackJack() {
		
		int line = (Settings.WIDTH - (Settings.convertWidth(313) * 6)) / 7;
		int resto = (Settings.WIDTH - (Settings.convertWidth(313) * 6)) % 7;
		int panelHeight = ((Settings.HEIGHT - ((line * 4) + resto)) - Settings.convertHeight(80)) / 2;
		int PanelPosition = Settings.convertHeight(80) + ((line * 3) + resto/2) + panelHeight;
		
		this.bjRect = new Sprite();
		this.bjRect.setFillRect(Settings.convertWidth(313), panelHeight, Color.BLACK);
		this.bjRect.setLocation(Settings.convertPositionX((313 * this.position) + (line * (this.position + 1))) + resto / 2, PanelPosition);
		this.bjRect.setRadialGradient(Settings.convertWidth(220), new float[] {0f,1f}, this.character.getThemeColor());
		
		this.bjName = new Sprite();
		this.bjName.setString(this.name, new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36)), Color.WHITE, 1f);
		this.bjName.setLocation(bjRect.getPosX() + bjRect.getWidth() / 2, bjRect.getPosY() + bjName.getFont().getSize() + Settings.convertPositionY(14));
		this.bjName.setFormatString(CENTER);
		
		this.bjCharacter = new Sprite();
		this.bjCharacter.setImage(Path.BJavatar + this.character.getName() + ".png");
		this.bjCharacter.setLocation(bjRect.getPosX(), bjName.getPosY() + Settings.convertPositionY(25));
		
		this.bjBackCharacter = new Sprite();
		this.bjBackCharacter.setFillRect(Settings.convertWidth(313), bjCharacter.getHeight() + 4, Color.WHITE);
		this.bjBackCharacter.setLocation(bjRect.getPosX(), bjCharacter.getPosY() - 2);
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
