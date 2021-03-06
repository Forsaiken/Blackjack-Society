package scenes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Display;
import engine.NameGenerator;
import engine.Sprite;
import global.Constants;
import global.Path;
import global.Settings;
import objects.Card;
import objects.Dealer;
import objects.Persona;
import objects.Player;

public class BlackJack extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Card> deck = new ArrayList<Card>();
	Player[] players;
	Sprite[] HBplayers;
	Dealer dealer;
	boolean load = false;
	int phase = 0;

	public BlackJack(Display window, CountDownLatch CDL) {
		
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	deck = this.createDeck();
    	players = this.createRandomPlayers(6);
    	dealer = new Dealer();    	
   
    	Timer t = new Timer((int) Settings.FPS1000, this);
    	t.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		dealer.blackjack(g);
		
		for (int i = players.length - 1; i > -1; i--) {			
			players[i].blackjack(g);
		}
		
		load = true;
	}
	
	public void addCard(Player player) {
		int index = ThreadLocalRandom.current().nextInt(0, deck.size());
		Card pickcard = this.deck.get(index);
		this.deck.remove(index);
		player.BJaddCard(0, pickcard);
	}

	public ArrayList<Card> createDeck() {
		
		ArrayList<Card> deck = new ArrayList<Card>();
		
		for (int x = 1; x < 14; x++) {
			for (int y = 0; y < 4; y++) {
				int value = 0;
				if (x >= 10)
					value = 10;
				else
					value = x;
				Card card = new Card(x,y,value);
				deck.add(card);
			}
		}
		
		return deck;
		
	}
	
	public Player[] createRandomPlayers(int quantity) {
		
		Player[] players = new Player[quantity];
		
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("" , 500000000, new Persona("RANDOM"), false);
			players[i].setName(players[i].getCharacter().getName().toUpperCase());
			players[i].setPosition(i);
			players[i].setRank(ThreadLocalRandom.current().nextInt(1, 100));
			players[i].setBJmoney(5000);
			players[i].BJaddHand();
		}
		
		return players;
		
	}
	
	@Override	public void actionPerformed(ActionEvent arg0) {

		if (phase == 0 && players[5].getLoad() == false) {
			for (int i = 0; i < players.length; i++) {
				players[i].setStatus(Constants.INACTIVE);
			}
		}
		
		repaint();
		
	}

	
}
