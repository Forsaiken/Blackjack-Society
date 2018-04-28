package scenes;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Card;
import engine.Player;
import global.Settings;

public class BlackJack extends JPanel implements ActionListener {
	
	Card[] deck;

	public BlackJack(Player[] players) {
		
		setSize(Settings.WIDTH,Settings.HEIGHT);
		
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	for (int i = 0; i < players.length; i++) {
    		
    	}
   
    	Timer t = new Timer((int) Settings.FPS, this);
    	t.start();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	}

	public void createDeck() {
		
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		for (int x = 0; x < 13; x++) {
			for (int y = 0; y < 4; y++) {
				int value = 0;
				if (x >= 10)
					value = 10;
				else
					value = x;
				Card card = new Card(x,y,value);
				tempDeck.add(card);
			}
		}
		deck = tempDeck.toArray(deck);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
