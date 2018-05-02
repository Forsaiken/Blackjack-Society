package engine;

import java.util.ArrayList;

import global.Path;
import global.Settings;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

public class Player extends Sprite{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String name;
	private int money;
	private boolean male;
	private boolean control;
	private Persona character;
	private int position;
	private int rank;
	
	// VARIABLES - BLACKJACK
	
	private boolean load = false;
	private int bjmoney;
	private int bjbet;
	
	private ArrayList<ArrayList<Card>> bjHand = new ArrayList<ArrayList<Card>>();
	
	private Sprite bjRect;
	private Sprite bjName;
	private Sprite bjCharacter;
	private Sprite bjBackCharacter;
	private Sprite bjRank;
	private Sprite bjMoney;
	private Sprite bjMoney2;
	private Sprite bjBet;
	
	public Player (String name, boolean male, int money, Persona character, boolean control){
		
		this.name = name;
		this.money = money;
		this.setMale(male);
		this.control = control;
		this.character = character;
	}
	 
	// ACTIONS
	
	public void blackjack(Graphics g) {
		
		if (load != true) {
			
			Font smallFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(18));
			Font largeFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36));
			
			int line = (Settings.WIDTH - (Settings.convertWidth(313) * 6)) / 7;
			int resto = (Settings.WIDTH - (Settings.convertWidth(313) * 6)) % 7;
			int panelHeight = ((Settings.HEIGHT - ((line * 4) + resto)) - Settings.convertHeight(80)) / 2;
			int PanelPosition = Settings.convertHeight(80) + ((line * 3) + resto/2) + panelHeight;
			
			this.bjRect = new Sprite();
			this.bjRect.setFillRect(Settings.convertWidth(313), panelHeight, Color.BLACK);
			this.bjRect.setLocation(Settings.convertPositionX((313 * this.position) + (line * (this.position + 1))) + resto / 2, PanelPosition);
			this.bjRect.setRadialGradient(Settings.convertWidth(220), new float[] {0f,1f}, this.character.getThemeColor());
			
			this.bjName = new Sprite();
			this.bjName.setString(this.name, largeFont, Color.WHITE, 1f);
			this.bjName.setLocation(bjRect.getPosX() + bjRect.getWidth() / 2, bjRect.getPosY() + bjName.getStringHeight(g) + Settings.convertPositionY(14));
			this.bjName.setFormatString(CENTER);
			
			this.bjCharacter = new Sprite();
			this.bjCharacter.setImage(Path.BJavatar + this.character.getName() + ".png");
			this.bjCharacter.setLocation(bjRect.getPosX(), bjName.getPosY() + ((bjName.getPosY() - bjName.getStringHeight(g)) - bjRect.getPosY()) + 2);
			
			this.bjBackCharacter = new Sprite();
			this.bjBackCharacter.setFillRect(Settings.convertWidth(313), bjCharacter.getHeight() + 4, Color.WHITE);
			this.bjBackCharacter.setLocation(bjRect.getPosX(), bjCharacter.getPosY() - 2);
			
			this.bjRank = new Sprite();
			this.bjRank.setString("RANK: #" + this.rank , smallFont, Color.WHITE, 1f);
			this.bjRank.setLocation(bjRect.getPosX() + Settings.convertPositionX(18), bjBackCharacter.getPosY() + bjBackCharacter.getHeight() + bjRank.getStringHeight(g) + Settings.convertPositionY(10));
			this.bjRank.setFormatString(LEFT_TO_RIGHT);
			
			this.bjMoney = new Sprite();
			this.bjMoney.setString("$" + Settings.convertToMoney(this.money), smallFont , Color.WHITE, 1f);
			this.bjMoney.setLocation(bjRank.getPosX() + Settings.convertPositionX(145), bjRank.getPosY());
			this.bjMoney.setFormatString(LEFT_TO_RIGHT);
			
			this.bjMoney2 = new Sprite();
			this.bjMoney2.setString("MATCH: $" + Settings.convertToMoney(this.bjmoney), smallFont, Color.WHITE, 1f);
			this.bjMoney2.setLocation(bjRect.getPosX() + Settings.convertPositionX(18), this.bjMoney.getPosY() + Settings.convertPositionY(250));
			this.bjMoney2.setFormatString(LEFT_TO_RIGHT);
			
			this.bjBet = new Sprite();
			this.bjBet.setString("BET: $" + Settings.convertToMoney(this.bjbet), smallFont, Color.WHITE, 1f);
			this.bjBet.setLocation(bjRect.getPosX() + Settings.convertPositionX(18), bjMoney2.getPosY() + bjMoney2.getStringHeight(g) +  Settings.convertPositionY(10));
			
			
			load = true;
			
		} else {
		
		bjRect.draw(g);
		bjName.draw(g);
		bjBackCharacter.draw(g);
		bjCharacter.draw(g);
		bjRank.draw(g);
		bjMoney.draw(g);
		bjMoney2.draw(g);
		bjBet.draw(g);
		
		}
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
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	// SET - BLACKJACK PROPERTIES
	
	public void setBJmoney(int money) {
		this.bjmoney = money;
	}
	
	public void BJaddHand() {
		ArrayList<Card> hand = new ArrayList<Card>();
		this.bjHand.add(hand);
	}
	
	public void BJaddCard(int hand, Card card) {
		this.bjHand.get(hand).add(card);
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
