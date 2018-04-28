package scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import engine.Display;
import engine.Persona;
import engine.Profile;
import engine.Sprite;
import engine.UI;
import global.Constants;
import global.Settings;
import global.Strings;
import global.Switch;

public class Title extends JPanel implements ActionListener, KeyListener{
	
	// STRINGS
	
	Strings strings = new Strings("introMenu.str");
	
	// IMAGENS
	
	Display window;
	UI titleMenu;
	
	Sprite city1;
	Sprite city2;
	Sprite stripe1;
	Sprite stripe2;
	Sprite tipo;
	Sprite pressStart;
	Sprite insertName;

	JTextField tfName = new JTextField("");
	
	private String[][] array_menu = {{strings.show(1), strings.show(2), strings.show(3)}};
	private Timer t;
    private int phase = 1;
    private boolean load = false;
    private Profile profile;
    
    private CountDownLatch CDL;

    public Title(Display window, CountDownLatch CDL) throws IOException{
    	
    	this.CDL = CDL;
    	this.window = window;
    	
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	setSize(Settings.WIDTH, Settings.HEIGHT);
        setBackground(Color.WHITE);
        window.addKeyListener(this);
        
        // TITLE MENU CONSTRUCTOR
        
        titleMenu = new UI();
        titleMenu.setBackMenu(false, 1050, 700, 500, 500, null, 0f);
        titleMenu.setMenuArray(array_menu, 40, 960, 800, Constants.CENTER, new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(30)), Color.BLACK, 1f);
        titleMenu.setSelectorMenu(false, null, 0f, new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(48)), Color.BLACK);
        titleMenu.setMenuInitialPoint(960, 800, 0f);
        titleMenu.setMenuPosToInitial();
        titleMenu.setSelect(0);
		titleMenu.setMenuAnimationSpeed(0, 0, 1f);
        
        // STRING CONSTRUCTOR
        
        pressStart = new Sprite();
        pressStart.setString(strings.show(0), new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36)), Color.BLACK, 1f);
        pressStart.setLocation(Settings.WIDTH/2, Settings.convertPositionY(800));
        pressStart.setFormatString(Constants.CENTER);
        pressStart.setAlphaToInitial();
        pressStart.setMotionAnimation(0, 0, 0, 0, 1.5f);
        
        insertName = new Sprite();
        insertName.setString(strings.show(4)+":", new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36)),Color.BLACK, 1f);
        insertName.setLocation(Settings.WIDTH/2 , (Settings.HEIGHT/2) - Settings.convertPositionY(50));
        insertName.setFormatString(Constants.CENTER);
        insertName.setAlphaToInitial();
        insertName.setMotionAnimation(0, 0, 0, 0, 1.5f);
        
        // TIPOGRAPHY LOGO CONSTRUCTOR
        
        tipo = new Sprite();
        tipo.setImage("images/scene/tipo.png");
        tipo.setLocation(Settings.WIDTH/2 - tipo.getWidth()/2, Settings.HEIGHT/2 - Settings.convertHeight(200));
        
        tipo.setAlphaToInitial();
        tipo.setMotionAnimation(0, 0, 0, 0, 2.0f);
        tipo.setAnimation(true);


        // STRIPES CONSTRUCTOR
        
        stripe1 = new Sprite();
        stripe1.setFillRect(tipo.getWidth(), Settings.convertHeight(10), Color.BLACK);
        stripe1.setFinalLocation(tipo.getPosX(), tipo.getPosY() - Settings.convertPositionY(15));
        stripe1.setInitialDimension(0, Settings.convertHeight(10));
        stripe1.setInitialLocation(Settings.WIDTH/2, tipo.getPosY() - Settings.convertPositionY(15));
        stripe1.setDimensionToInitial();
        stripe1.setLocationToInitial();       
        stripe1.setMotionAnimation(1.5f, 0, 1.5f, 0, 0);
        
        stripe2 = new Sprite();
        stripe2.setFillRect(tipo.getWidth(), Settings.convertHeight(10), Color.BLACK);
        stripe2.setFinalLocation(tipo.getPosX(), tipo.getPosY() + tipo.getHeight() + Settings.convertPositionY(5));
        stripe2.setInitialDimension(0, Settings.convertHeight(10));
        stripe2.setInitialLocation(Settings.WIDTH/2, tipo.getPosY() + tipo.getHeight() + Settings.convertPositionY(5));
        stripe2.setDimensionToInitial();
        stripe2.setLocationToInitial();
        stripe2.setMotionAnimation(1.5f, 0, 1.5f, 0, 0);
        
        // BUILD LOGO CONSTRUCTOR
        
        city1 = new Sprite();
        city1.setImage("images/scene/logo2.png");
        city1.setLocation(tipo.getPosX(),stripe1.getFinalY() + Settings.convertPositionY(10));
        city1.setInitialDimension(city1.getFinalWidth(), 0);
        city1.setFinalDimension(city1.getWidth(), city1.getHeight() - city1.getHeight() * 2);
        city1.setDimensionToInitial();
        city1.setMotionAnimation(0, 0, 0, 1.5f, 0);
        
        city2 = new Sprite();
        city2.setImage("images/scene/logo2.png");
        city2.setLocation(tipo.getPosX(),stripe2.getFinalY());
        city2.setInitialDimension(city2.getFinalWidth(), 0);
        city2.setDimensionToInitial();
        city2.setMotionAnimation(0, 0, 0, 1.5f, 0);    
        
        
        t = new Timer((int) Settings.FPS1000, this);
        t.start();
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        if (load == false) {
            
        	load = true;
            phase += 1;

        }
        
        
        tipo.draw(g);
        stripe1.draw(g);
        stripe2.draw(g);
        city1.draw(g);
        city2.draw(g); 
        pressStart.draw(g);
        titleMenu.draw(g);
        insertName.draw(g);
        
    }

    public void actionPerformed(ActionEvent e) {
    	
    	if (phase == 2 && tipo.getAnimation() != true){
    		stripe1.setAnimation(true);
			stripe2.setAnimation(true);
			phase++;
    	}

    	if (phase == 3 && stripe1.getAnimation() != true){
    		city1.setAnimation(true);
    		city2.setAnimation(true);
    		phase++;
    	}
    	
    	if (phase == 4 && city1.getAnimation() != true) {
    		pressStart.setAnimation(true);
    		phase++;
    	}
    	
    	if (phase == 6 && pressStart.getAnimation() != true){
			titleMenu.setMenuAnimationSpeed(0, 0, 1f);
			titleMenu.setKeyListener(window);
			titleMenu.setKeyScroll(true);
			titleMenu.open();
			phase++;
    	}
    	
    	if (phase == 8 && tipo.getAnimation() != true) {
    		insertName.setAnimation(true);
    		phase++;
    		
    		tfName.setFont(new Font("axis", Font.PLAIN, Settings.convertFont(32)));
            tfName.setBackground(Color.WHITE);
            tfName.setForeground(Color.BLACK);
            tfName.setColumns(10);
            tfName.setBounds(Settings.WIDTH/2 - (Settings.convertPositionX(400)/2), Settings.HEIGHT/2 - (Settings.convertPositionY(50)/2),Settings.convertPositionX(400), Settings.convertPositionY(50));
            tfName.setVisible(true);
     		tfName.setHorizontalAlignment(JTextField.CENTER);
     		tfName.addKeyListener(new KeyAdapter(){
     			public void keyTyped(KeyEvent e) { 
     		        if (tfName.getText().length() >= 10 )
     		            e.consume(); 
     			}
     			public void keyPressed(KeyEvent e) {
     				if (e.getKeyCode() == KeyEvent.VK_ENTER){
     					if (tfName.getText().length() > 0){
     						Profile.name = tfName.getText();
     						Profile.character = new Persona("Red Queen");
     						window.requestFocus();
     						CDL.countDown();
     					}
     				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
     					
     					repaint();
     					
     					
     				}
     			}
     		});
 			add(tfName, BorderLayout.CENTER);
 			tfName.grabFocus();
    		phase++;
 
    	}
    	
    	if (phase == 9) {
    		tfName.setBounds(Settings.WIDTH/2 - (Settings.convertPositionX(400)/2), Settings.HEIGHT/2 - (Settings.convertPositionY(50)/2),Settings.convertPositionX(400), Settings.convertPositionY(50));
    	}
    	
    		repaint();
    }

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Pressed "+ e.getKeyCode() +  KeyEvent.VK_ENTER);
		if (e.getKeyCode() == KeyEvent.VK_ENTER && phase == 5) {
    		pressStart.setInitialAlpha(1f);
    		pressStart.setFinalAlpha(0f);
    		pressStart.resetCountAnimation();
    		pressStart.setMotionAnimation(0, 0, 0, 0, 0.5f);
    		pressStart.setAnimation(true);
			phase++;
			repaint();
		}
		
		if (phase == 7){
			
			if (e.getKeyCode() == KeyEvent.VK_ENTER){

				int choice = titleMenu.getSelect();
				if (choice == 0){
					
					city1.setInitialAlpha(1f);
					city1.setFinalAlpha(0f);
					
					city2.setInitialAlpha(1f);
					city2.setFinalAlpha(0f);
					
					stripe1.setInitialAlpha(1f);
					stripe1.setFinalAlpha(0f);
					
					stripe2.setInitialAlpha(1f);
					stripe2.setFinalAlpha(0f);
					
					tipo.setInitialAlpha(1f);
					tipo.setFinalAlpha(0f);
					
					
					city1.setMotionAnimation(0, 0, 0, 0, 1f);
					city2.setMotionAnimation(0, 0, 0, 0, 1f);
					stripe1.setMotionAnimation(0, 0, 0, 0, 1f);
					stripe2.setMotionAnimation(0, 0, 0, 0, 1f);
					tipo.setMotionAnimation(0, 0, 0, 0, 1f);
					
					
					city1.setAnimation(true);
					city2.setAnimation(true);
					stripe1.setAnimation(true);;
					stripe2.setAnimation(true); ;
					tipo.setAnimation(true);
					
					titleMenu.lock();
					
					phase++;
					repaint();
					
					}
				
				if (choice == 1){}
				
				if (choice == 2){ System.exit(0); }
				
			}
			
		}		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}   
}