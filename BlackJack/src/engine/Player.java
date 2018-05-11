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
	private int[] bjsoma = new int[4];
	
	private ArrayList<ArrayList<Card>> bjHand = new ArrayList<ArrayList<Card>>();
	
	private Sprite bjRect;
	private Sprite bjName;
	private Sprite bjCharacter;
	private Sprite bjBackCharacter;
	private Sprite bjRank;
	private Sprite bjMoney;
	private Sprite[] bjSoma = new Sprite[4];
	private Sprite bjMoney2;
	private Sprite bjBet;
	private Sprite bjBar;
	
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
			
			// CONFIG VARIABLE - PLAYER BASE IN BLACKJACK
			
			Font smallFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(18));
			Font mediumFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(30));
			Font largeFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36));
			int width = Settings.convertWidth(313);
			
			int line = (Settings.WIDTH - (width * 6)) / 7;
			System.out.println(Math.floor((Settings.WIDTH - (width * 6)) / 7));
			int resto = (Settings.WIDTH - (width * 6)) % 7;
			int panelHeight = ((Settings.HEIGHT - ((line * 4) + resto)) - Settings.convertHeight(80)) / 2;
			int PanelPosition = Settings.convertHeight(80) + ((line * 3) + resto/2) + panelHeight;
			
			this.bjRect = new Sprite();
			this.bjRect.setFillRect(width, panelHeight, Color.BLACK);
			this.bjRect.setLocation(width * this.position + (line * (this.position + 1)) + resto / 2, PanelPosition);
			this.bjRect.setRadialGradient(Settings.convertWidth(220), new float[] {0f,1f}, this.character.getThemeColor());
			
			this.bjName = new Sprite();
			this.bjName.setString(this.name, largeFont, Color.WHITE, 1f);
			this.bjName.setLocation(bjRect.getPosX() + bjRect.getWidth() / 2, bjRect.getPosY() + bjName.getStringHeight(g) + Settings.convertPositionY(14));
			this.bjName.setFormatString(CENTER);
			this.bjName.setSpacementString(0.001);
			
			this.bjCharacter = new Sprite();
			this.bjCharacter.setImage(Path.BJavatar + this.character.getName() + ".png");
			this.bjCharacter.setLocation(bjRect.getPosX(), bjName.getPosY() + ((bjName.getPosY() - bjName.getStringHeight(g)) - bjRect.getPosY()) + 2);
			
			this.bjBackCharacter = new Sprite();
			this.bjBackCharacter.setFillRect(width, bjCharacter.getHeight() + 4, Color.WHITE);
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
			
			this.bjBar = new Sprite();
			this.bjBar.setImage(Path.helpBar + this.getCharacter().getName() + ".png");
			this.bjBar.redimensionImageByWidth(width * 6 + line * 5);
			this.bjBar.setLocation(line + resto / 2, bjRect.getPosY() - bjBar.getHeight() - line);
			
			for (int i = 0; i < 4; i++) {
				this.bjSoma[i] = new Sprite();
				this.bjSoma[i].setString("", mediumFont, Color.WHITE, 1f);
				this.bjSoma[i].setFormatString(CENTER);
			}
			
			load = true;
			
		} else {
		
		bjRect.draw(g);
		bjName.draw(g);
		bjBackCharacter.draw(g);
		bjCharacter.draw(g);
		bjRank.draw(g);
		bjMoney.draw(g);
		for (int x = 0; x < bjHand.size(); x++) {
			
			for (int y = 0; y < bjHand.get(x).size(); y++) {
				bjHand.get(x).get(y).draw(g);
			}
			
			bjSoma[x].draw(g);
		}
		bjMoney2.draw(g);
		bjBet.draw(g);
		bjBar.draw(g);
		}
	}
	
	// SET - BASIC PROPERTIES
	
	public void setName(String name) {
		this.name = name;
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
		card.load();
		this.bjHand.get(hand).add(card);
		this.BJorganizeHand();
		this.BJupdateValue();
	}
	
	public void BJorganizeHand(){
		for (int x = 0; x < bjHand.size(); x++) {
			for (int y = 0; y < bjHand.get(x).size(); y++) {
				if (y == 0 && bjHand.get(x).size() > 1)
					bjHand.get(x).get(y).setLocation(bjRect.getPosX() + bjRect.getWidth()/2 - Settings.convertWidth(92)/2 - (y+1 * Settings.convertPositionX(18)), bjMoney.getPosY() + Settings.convertPositionY(30));
				else if (y == 0)
					bjHand.get(x).get(y).setLocation(bjRect.getPosX() + bjRect.getWidth()/2 - Settings.convertWidth(92)/2 - (y * Settings.convertPositionX(18)), bjMoney.getPosY() + Settings.convertPositionY(30));
				else
					bjHand.get(x).get(y).setLocation(bjHand.get(x).get(y-1).getPosX() + Settings.convertPositionX(18), bjMoney.getPosY() + Settings.convertPositionY(30));
					
				bjHand.get(x).get(y).setDimensionByWidth(92);
			}
		}
	}
	
	public void BJupdateValue() {
		for (int x = 0; x < bjHand.size(); x++) {
			
			bjsoma[x] = this.BJgetValueHand(bjHand.get(x));
			if (bjsoma[x] == 21)
				bjSoma[x].setStringName("BJ");
			else if (bjsoma[x] > 21)
				bjSoma[x].setStringName("OUT");
			else
				bjSoma[x].setStringName("" + bjsoma[x]);
			
			if (bjHand.size() == 1) {
				bjSoma[x].setLocation(bjRect.getPosX() + bjRect.getWidth()/2, bjRank.getPosY() + Settings.convertPositionY(200));
			}
		}
	}
	
	public int BJgetValueHand(ArrayList<Card> hand) {
		int soma = 0;
		boolean haveAS = false;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSet() != true) {
				if (hand.get(i).getValue() == 1)
					haveAS = true;
			
				soma += hand.get(i).getValue();
			}
		}
		
		if (soma < 12 && haveAS)
			soma+=10;
		
		return soma;
	}
	
	// GETS
	
	public String getName() {
		return name;
	}
	
	public int getMoney() {
		return money;
	}
	
	public Persona getCharacter() {
		return this.character;
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
