package engine;

import global.Path;

public class Card {
	
	private int symbol;
	private String path;
	private int naipe;
	private int value;
	private boolean active;
	
	public Card(int symbol, int naipe, int value) {
		this.naipe = naipe;
		this.value = value;
		this.path = Path.Cards + symbol + naipe;
	}
	
}
