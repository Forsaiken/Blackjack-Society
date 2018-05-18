package objects;

import java.util.ArrayList;

import engine.BlendComposite;
import engine.Sprite;
import global.Constants;
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
	
	public static int BJwidth = Settings.convertWidth(313);
	public static int BJline = (Settings.WIDTH - (BJwidth * 6)) / 7;
	public static int BJresto = (Settings.WIDTH - (BJwidth * 6)) % 7;
	public static int BJbarHeight = (int)(80 * (float) (BJwidth * 6 + BJline * 5) / (float) 1908);
	public static int BJpanelHeight = (Settings.HEIGHT - BJline * 4 - BJresto - BJbarHeight) / 2;
	public static int BJpanelPosY = Settings.HEIGHT - BJline - BJpanelHeight - BJresto/2;
	
	public boolean load = true;
	private int bjmoney;
	private int bjbet;
	public int[] bjsoma = new int[4];
	
	public ArrayList<ArrayList<Card>> bjHand = new ArrayList<ArrayList<Card>>();
	
	public Sprite bjRect;
	public Sprite bjName;
	private Sprite bjCharacter;
	private Sprite bjBackCharacter;
	public Sprite bjRank;
	public Sprite bjMoney;
	public Sprite[] bjSoma = new Sprite[4];
	public Sprite bjMoney2;
	public Sprite bjBet;
	public Sprite bjBar;
	public Sprite bjBarTexture;
	
	public Player (String name, int money, Persona character, boolean control){
		
		this.name = name;
		this.money = money;
		this.control = control;
		this.character = character;
		
	}
	 
	// ACTIONS
	
	public void blackjack(Graphics g) {
		
		if (load) {
			
			// CONFIG VARIABLE - PLAYER BASE IN BLACKJACK
			
			Font smallFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(18));
			Font mediumFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(30));
			Font largeFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36));
			
			this.bjRect = new Sprite();
			this.bjRect.setFillRect(BJwidth, BJpanelHeight, Color.BLACK);
			this.bjRect.setLocation(BJwidth * this.position + (BJline * (this.position + 1)) + BJresto / 2, BJpanelPosY);
			this.bjRect.setRadialGradient(Settings.convertWidth(240), new float[] {0f,1f}, this.character.getThemeColor());
			this.bjRect.setInitialPosX(0 - BJwidth);
			this.bjRect.setPosXToInitial();
			this.bjRect.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjName = new Sprite();
			this.bjName.setString(this.name, largeFont, Color.WHITE, 1f);
			this.bjName.setLocation(bjRect.getFinalX() + bjRect.getFinalWidth() / 2, bjRect.getFinalY() + bjName.getStringHeight(g) + Settings.convertPositionY(14));
			this.bjName.setFormatString(CENTER);
			this.bjName.setSpacementString(0.001);
			this.bjName.setInitialPosX(0 - BJwidth/2);
			this.bjName.setPosXToInitial();
			this.bjName.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjCharacter = new Sprite();
			this.bjCharacter.setImage(Path.BJavatar + this.character.getName() + ".png");
			this.bjCharacter.setLocation(bjRect.getFinalX(), bjName.getFinalY() + ((bjName.getFinalY() - bjName.getStringHeight(g)) - bjRect.getFinalY()) + 2);
			this.bjCharacter.setInitialPosX(0 - BJwidth);
			this.bjCharacter.setPosXToInitial();
			this.bjCharacter.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjBackCharacter = new Sprite();
			this.bjBackCharacter.setFillRect(BJwidth, bjCharacter.getFinalHeight() + 4, Color.WHITE);
			this.bjBackCharacter.setLocation(bjRect.getFinalX(), bjCharacter.getFinalY() - 2);
			this.bjBackCharacter.setInitialPosX(0 - BJwidth);
			this.bjBackCharacter.setPosXToInitial();
			this.bjBackCharacter.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjRank = new Sprite();
			this.bjRank.setString("RANK: #" + this.rank , smallFont, Color.WHITE, 1f);
			this.bjRank.setLocation(bjRect.getFinalX() + Settings.convertPositionX(18), bjBackCharacter.getFinalY() + bjBackCharacter.getFinalHeight() + bjRank.getStringHeight(g) + Settings.convertPositionY(10));
			this.bjRank.setFormatString(LEFT_TO_RIGHT);
			this.bjRank.setInitialPosX(0 - BJwidth + Settings.convertPositionX(18));
			this.bjRank.setPosXToInitial();
			this.bjRank.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjMoney = new Sprite();
			this.bjMoney.setString("$" + Settings.convertToMoney(this.money), smallFont , Color.WHITE, 1f);
			this.bjMoney.setLocation(bjRank.getFinalX() + Settings.convertPositionX(145), bjRank.getFinalY());
			this.bjMoney.setFormatString(LEFT_TO_RIGHT);
			this.bjMoney.setInitialPosX(0 - BJwidth + Settings.convertPositionX(145));
			this.bjMoney.setPosXToInitial();
			this.bjMoney.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjMoney2 = new Sprite();
			this.bjMoney2.setString("MATCH: $" + Settings.convertToMoney(this.bjmoney), smallFont, Color.WHITE, 1f);
			this.bjMoney2.setLocation(bjRect.getFinalX() + Settings.convertPositionX(18), this.bjMoney.getFinalY() + Settings.convertPositionY(250));
			this.bjMoney2.setFormatString(LEFT_TO_RIGHT);
			this.bjMoney2.setInitialPosX(0 - BJwidth + Settings.convertPositionX(18));
			this.bjMoney2.setPosXToInitial();
			this.bjMoney2.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjBet = new Sprite();
			this.bjBet.setString("BET: $" + Settings.convertToMoney(this.bjbet), smallFont, Color.WHITE, 1f);
			this.bjBet.setLocation(bjRect.getFinalX() + Settings.convertPositionX(18), bjMoney2.getFinalY() + bjMoney2.getStringHeight(g) +  Settings.convertPositionY(10));
			this.bjBet.setInitialPosX(0 - BJwidth + Settings.convertPositionX(18));
			this.bjBet.setPosXToInitial();
			this.bjBet.setMotionAnimation(0.1f*(position + 1), 0, 0, 0, 0);
			
			this.bjBar = new Sprite();
			this.bjBar.setImage(Path.helpBar + this.getCharacter().getName() + ".png");
			this.bjBar.redimensionImageByWidth(BJwidth * 6 + BJline * 5);
			this.bjBar.setLocation(BJline + BJresto / 2, bjRect.getFinalY() - bjBar.getFinalHeight() - BJline);
			
			this.bjBarTexture = new Sprite();
			this.bjBarTexture.setImage(Path.helpBar + "Texture.png");
			this.bjBarTexture.setBlendMode(BlendComposite.ColorBurn);
			this.bjBarTexture.setAlpha(0.35f);
			this.bjBarTexture.redimensionImageByWidth(BJwidth * 6 + BJline * 5);
			this.bjBarTexture.setLocation(BJline + BJresto / 2, bjRect.getFinalY() - bjBar.getFinalHeight() - BJline);

			this.bjRect.setAnimation(true);
			this.bjName.setAnimation(true);
			this.bjCharacter.setAnimation(true);
			this.bjBackCharacter.setAnimation(true);
			this.bjRank.setAnimation(true);
			this.bjMoney.setAnimation(true);
			this.bjMoney2.setAnimation(true);
			this.bjBet.setAnimation(true);
			
			for (int i = 0; i < 4; i++) {
				this.bjSoma[i] = new Sprite();
				this.bjSoma[i].setString("", mediumFont, Color.WHITE, 1f);
				this.bjSoma[i].setFormatString(CENTER);
			}
			
			load = false;
			
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
		bjBarTexture.draw(g);
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
	
	public void reloadSprites() {
		this.load = false;
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
				if (y == 0)
					bjHand.get(x).get(y).setLocation(bjRect.getFinalX() + bjRect.getFinalWidth()/2 - Settings.convertWidth(92)/2 - (bjHand.get(x).size() - 1) * Settings.convertPositionX(18) / 2,
													 bjMoney.getFinalY() + Settings.convertPositionY(30));
				else
					bjHand.get(x).get(y).setLocation(bjHand.get(x).get(y-1).getPosX() + Settings.convertPositionX(18),
													 bjMoney.getFinalY() + Settings.convertPositionY(30));
					
				bjHand.get(x).get(y).setDimensionByWidth(92);
			}
		}
	}
	
	public void setStatus(byte status) {
		if (status == Constants.INACTIVE) {
			bjRect.setRadialGradient(Settings.convertWidth(240), new float[] {0f,1f}, new Color[] {new Color(150,150,150), new Color(30,30,30)});
		}
		
		if (status == Constants.ACTIVE) {
			this.bjRect.setRadialGradient(Settings.convertWidth(240), new float[] {0f,1f}, this.character.getThemeColor());
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
	
	// GETS - BLACKJACK PANEL
	
	public Sprite getBJpanel() {
		return this.bjRect;
	}
	
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
	
	public boolean getLoad() {
		return this.load;
	}
}
