package scenes;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Display;
import engine.Sprite;
import global.Settings;

public class Intro extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private int phase = 0;
	
	private Sprite background = new Sprite();
	private Sprite forsaiken;
	private Sprite kenney;
	private CountDownLatch CDL;
	private Timer t;
		
	public Intro(Display window,CountDownLatch end){
		
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	CDL = end;
    	
    	setSize(Settings.WIDTH,Settings.HEIGHT);
		setBackground(Color.BLACK);
		
		background.setFillRect(Settings.WIDTH, Settings.HEIGHT, Color.WHITE);
		background.setLocation(0, 0);
		forsaiken = new Sprite();
		kenney = new Sprite();
		
		forsaiken.setImage("images/scene/forsaiken.png");
		kenney.setImage("images/scene/kenney.png");
		
		forsaiken.setLocation((Settings.WIDTH/2) - (forsaiken.getWidth()/2), (Settings.HEIGHT/2) - (forsaiken.getHeight()/2));
		kenney.setLocation((Settings.WIDTH/2) - (kenney.getWidth()/2), (Settings.HEIGHT/2) - (kenney.getHeight()/2));
		
		forsaiken.setAlphaToInitial();
		kenney.setAlphaToInitial();
		
		forsaiken.setMotionAnimation(0, 0, 0, 0, 1.5f);
		kenney.setMotionAnimation(0, 0, 0, 0, 1.5f);
		
		forsaiken.setAnimation(true);
		
		t = new Timer((int) Settings.FPS1000, this);
		t.start();
		
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		background.draw(g);
		forsaiken.draw(g);
		kenney.draw(g);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (phase == 0 && forsaiken.getAnimation() != true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			forsaiken.setInitialAlpha(1f);
			forsaiken.setFinalAlpha(0f);
			forsaiken.setMotionAnimation(0, 0, 0, 0, 0.5f);
			forsaiken.setAnimation(true);
			phase++;
		}
		
		if (phase == 1 && forsaiken.getAnimation() != true) {
			kenney.setAnimation(true);
			phase++;
		}
		
		if (phase == 2 && kenney.getAnimation() != true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			kenney.setInitialAlpha(1f);
			kenney.setFinalAlpha(0f);
			kenney.setMotionAnimation(0, 0, 0, 0, 0.5f);
			kenney.setAnimation(true);
			phase++;
		}
		
		if (phase == 3 && kenney.getAnimation() != true) {
			CDL.countDown();
			t.stop();
		}
		
		repaint();
	}
}
