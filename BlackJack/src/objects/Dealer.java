package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import engine.Sprite;
import global.Settings;

public class Dealer extends Player {
	
	public static int BJwidth = (Settings.WIDTH - Player.BJline * 4 - Player.BJresto) / 3;
	public static int BJpanelPosY = Settings.HEIGHT - Player.BJline * 3 - Player.BJresto / 2 - Player.BJbarHeight - Player.BJpanelHeight * 2;
	
	public int[] bjsoma = new int[1];
	public Sprite[] bjSoma = new Sprite[1];
	
	public Dealer() {
		super("DEALER", 0, null, false);
		this.BJaddHand();
	}
	
	public void blackjack(Graphics g) {
		
		if (load) {
			
			Font largeFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36));
			Font extraFont = new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(40));
			
			this.bjRect = new Sprite();
			this.bjRect.setFillRect(BJwidth, BJpanelHeight, Color.BLACK);
			this.bjRect.setLocation(BJwidth + BJline * 2, BJpanelPosY);
			this.bjRect.setRadialGradient(Settings.convertWidth(240), new float[] {0f,1f}, new Color[] {new Color(41,41,41), new Color(0,0,0)});
			
			this.bjName = new Sprite();
			this.bjName.setString(this.getName(), largeFont, Color.WHITE, 1f);
			this.bjName.setLocation(bjRect.getPosX() + bjRect.getWidth() / 2, bjRect.getPosY() + bjName.getStringHeight(g) + Settings.convertPositionY(30));
			this.bjName.setFormatString(CENTER);
			
			this.bjSoma[0] = new Sprite();
			this.bjSoma[0].setString("", extraFont, Color.WHITE, 1f);
			this.bjSoma[0].setFormatString(CENTER);
			this.bjSoma[0].setLocation(bjRect.getPosX() + bjRect.getWidth()/2, bjRect.getPosY() + Settings.convertPositionY(460));
			
			load = false;
		}
		
		this.bjRect.draw(g);
		this.bjName.draw(g);
		
		for (int i = 0; i < bjHand.get(0).size(); i++) {
			bjHand.get(0).get(i).draw(g);
		}
		
		bjSoma[0].draw(g);
		
		
	}
	
	public void BJorganizeHand() {
		for (int i = 0; i < bjHand.get(0).size(); i++) {
			if (i == 0)
				bjHand.get(0).get(i).setLocation(bjRect.getPosX() + bjRect.getWidth()/2 - bjHand.get(0).size() * Settings.convertPositionX(90) / 2,
												 bjName.getPosY() + Settings.convertPositionY(235));
			else
				bjHand.get(0).get(i).setLocation(bjHand.get(0).get(i-1).getPosX() + Settings.convertPositionX(90),
												 bjName.getPosY() + Settings.convertPositionY(235));
			
			bjHand.get(0).get(i).setDimensionByWidth(87);
		}
	}
	
	public void BJupdateValue() {
		
		bjsoma[0] = this.BJgetValueHand(bjHand.get(0));
		
		if (bjsoma[0] == 21)
			bjSoma[0].setStringName("BJ");
		else if (bjsoma[0] > 21)
			bjSoma[0].setStringName("OUT");
		else
			bjSoma[0].setStringName("" + bjsoma[0]);
		
	}
		
}
