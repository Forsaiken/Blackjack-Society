package scenes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Card;
import engine.Display;
import engine.NameGenerator;
import engine.Persona;
import engine.Player;
import global.Settings;

public class BlackJack extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Card> deck = new ArrayList<Card>();
	Player[] players;

	public BlackJack(Display window, CountDownLatch CDL) {
		
		setSize(Settings.WIDTH,Settings.HEIGHT);
		
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	deck = this.createDeck();
    	players = this.createRandomPlayers(6);
    	
    	for (int i = 0; i < players.length; i++) {
    		
    	}
   
    	Timer t = new Timer((int) Settings.FPS, this);
    	t.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		for (int i = 0; i < players.length; i++) {
			players[i].blackjack(g);
		}
	}

	public ArrayList<Card> createDeck() {
		
		ArrayList<Card> deck = new ArrayList<Card>();
		
		for (int x = 0; x < 13; x++) {
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
			players[i] = new Player("RED QUEEN" , true, 500000000, new Persona("Red Queen"), false);
			players[i].setPosition(i);
			players[i].setRank(ThreadLocalRandom.current().nextInt(1, 100));
			players[i].setBJmoney(5000);
			players[i].BJaddHand();
			players[i].BJaddCard(0, this.deck.get(ThreadLocalRandom.current().nextInt(0, deck.size() - 1)));
		}
		
		return players;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
