package objects;

import java.awt.Graphics;

import engine.Sprite;
import global.Path;
import global.Settings;

public class Card {
	
	private Sprite card;
	private String path;
	private int symbol;
	private int naipe;
	private int value;
	private boolean active;
	private boolean set;
	
	public Card(int symbol, int naipe, int value) {
		this.naipe = naipe;
		this.value = value;
		this.path = Path.Cards + symbol + naipe + ".png";
	}
	
	public void load() {
		card = new Sprite();
		card.setImage(path);
		System.out.println(path);
	}
	
	public void draw(Graphics g) {
		card.draw(g);
	}
	
	public void setLocation(int x, int y) {
		card.setLocation(x, y);
	}
	
	public void setDimensionByWidth(int width) {
		card.redimensionImageByWidth(Settings.convertWidth(width));		
	}
	
	public int getValue() {
		return this.value;
	}
	
	public boolean getSet() {
		return this.set;
	}
	
	public int getPosX() {
		return card.getPosX();
	}
	
    }
