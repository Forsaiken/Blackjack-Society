package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Display;
import engine.Persona;
import engine.Profile;
import engine.Sprite;
import engine.UI;
import global.Constants;
import global.Settings;

public class MainScreen extends JPanel implements ActionListener, KeyListener, Constants {
	
	private UI mainMenu= new UI();
	
	private Timer t;
	
	private Sprite background = new Sprite();
	private Sprite menu = new Sprite();
	
	private UI[] menus = new UI[6];
	private UI[] parameters = new UI[6];
	
	private int menuSelect;
	private int parameterSelect;
	
	private Sprite menuTitle = new Sprite();
	private Sprite menuTitleBar = new Sprite();
	private Sprite menuIcon = new Sprite();
	private Sprite helpBar = new Sprite();
	private Sprite helpBarString = new Sprite();
	private Sprite titleMenu1 = new Sprite();
	private Sprite titleMenu2 = new Sprite();
	
	private String help;
	
	boolean options;
	
	private Font menuFont = new Font("axis",Font.LAYOUT_LEFT_TO_RIGHT, Settings.convertFont(36));
	
	private String[][] arrayMenu = {{"SOCIETY MODE","MATCH MODE","PROFILE","TUTORIAL","OPTIONS","BACK"}};
	
	private String[][] societyParameterStrings = {{"EASY", "NORMAL", "HARD"},{"25","50","75","100"},{"YES","NO"}, Settings.createIntString(3, 8, 1)};
	private int[][] societyParameterValues = {{0, 1, 2},{25,50,75,100},{0,1},{3,4,5,6,7,8}};
	private int[] societyParameterSelect = {0,0,0,0};
	
	private String[][] matchParameterStrings = {{"NORMAL MATCH"},{"BLACKJACK"},{"2","3","4","5","6"},{"YES","NO"}};
	private int[][] matchParameterValues = {{0},{0},{1,2,3,4,5},{0,1}};
	private int[] matchParameterSelect = {0,0,0,0,0};
	
	private String[][] profileParameterStrings = {{Profile.name}};
	private int[][] profileParameterValues = {{0}};
	private int[] profileParameterSelect = {0};
	
	private String[][] tutorialParameterStrings = {{"BLACKJACK"}};
	private int[][] tutorialParameterValues = {{0}};
	private int[] tutorialParameterSelect = {0};
			
	private String[][] optionsParameterStrings = {{"960x540","1280x720","1366x768","1600x900","1920x1080"},{"OFF","ON"},{"OFF","ON"},{"English","Português (Brasil)"}};
	private int[][] optionsParameterValues = {{0,1,2,3,4},{0,1},{0,1},{0,1}};
	private int[] optionsParameterSelect = {0,0,0,0};

	private int[][] parametersSelect = {societyParameterSelect, matchParameterSelect, profileParameterSelect, tutorialParameterSelect, optionsParameterSelect};
	
	private String[][][] menuStrings = {
			{{"DIFFICULT", "NUMBER OF PLAYERS","ALLOW POWERS","NUMBER OF SEASONS","START SOCIETY"}},
			{{"MATCH MODE","GAME MODE","NUMBER OF PLAYERS","ALLOW POWERS"}},
			{{"NICKNAME"}},
			{{"MODE","START"}},
			{{"VIDEO","RESOLUTION","FULL SCREEN","SMOOTH RENDER","LANGUAGE"},{"AUDIO","MUSIC VOLUME", "SOUND EFFECTS VOLUME"},{"GAME","GAME SPEED"}}
	};
	
	private String[][][] parameterStrings = {
			{{"EASY", "NORMAL", "HARD"},{"25","50","75","100"},{"YES","NO"},Settings.createIntString(3, 8, 1)},
			{{"NORMAL MATCH"},{"BLACKJACK"},{"2","3","4","5","6"},{"YES","NO"}},
			{{Profile.name}},
			{{"BLACKJACK"}},
			{{"960x540","1280x720","1366x768","1600x900","1920x1080"},{"OFF","ON"},{"OFF","ON"},{"English","Português (Brasil)"}}
	};
			
	private boolean load = false;
	
	public MainScreen(Display window,CountDownLatch fim){
		
		Profile.name = "Red Queen";
		Profile.character = new Persona("Red Queen");
		setSize(Settings.WIDTH,Settings.HEIGHT);
		
    	if (Settings.FULL_SCREEN == true && Settings.heightMonitor != Settings.heightResolution){
    		int wDiff = Settings.widthMonitor - Settings.widthResolution;
    		int hDiff = Settings.heightMonitor - Settings.heightResolution;
    		setLocation(wDiff/2,hDiff/2);
    	}
    	
    	if (Settings.FULL_SCREEN == true){
    		String[] resolutionString = {Settings.widthResolution+"x"+Settings.heightResolution};
    		optionsParameterStrings[0] = resolutionString;    		
    	}
		
		setFocusable(true);
		window.addKeyListener(this);
		
		background.setFillRect(Settings.WIDTH, Settings.HEIGHT, Color.WHITE);
		background.setLocation(0, 0);
		
		mainMenu.setBackMenu(true, 0, 0, 580, 1080, Color.BLACK, 0.6f);
		mainMenu.setBackImage(Profile.character.getBackImage(), 1f);
		mainMenu.setMenuArray(arrayMenu, 80, 65, 300, Constants.LEFT_TO_RIGHT, menuFont, Color.WHITE, 1f);
		
		mainMenu.setBackAnimationSpeed(10, 0, 0, 0, 0, 0);
		mainMenu.setBackInitialPoint(-580, 0, 580, 1080, 1f, 0.6f);
		mainMenu.setBackAnimation(true);
		mainMenu.setBackPosToInitial();
		
		mainMenu.setMenuAnimationSpeed(11, 0, 0);
		mainMenu.setMenuInitialPoint(-580,300,1f);
		mainMenu.setMenuAnimation(true);
		mainMenu.setMenuPosToInitial();
		
		mainMenu.setSelectorMenu(true, Color.RED, 0.6f, menuFont, Color.WHITE);
		mainMenu.setSelect(0);
		
		mainMenu.setKeyListener(window);
		mainMenu.setKeyScroll(true);
		
		for (int i = 0; i < menuStrings.length; i++){
			
			menus[i] = new UI();
			
			menus[i].setTitleBar(arrayMenu[0][i], 579, 0, 1342, 80, Constants.LEFT_TO_RIGHT,
					new Font("axis", Font.CENTER_BASELINE, Settings.convertFont(36)), Color.WHITE, Color.BLUE,
					1f, 1f);
			menus[i].setTitleBarPosToFinal();
			
			menus[i].setBackMenu(true, 580, 65, 1050, 952, Color.WHITE, 0.9f);
			menus[i].setMenuArray(menuStrings[i], 65, 650, 164, Constants.LEFT_TO_RIGHT, menuFont, Color.BLACK, 1f);
			menus[i].setSelectorMenu(true, Color.LIGHT_GRAY, 0.5f, menuFont, Color.BLACK);
			menus[i].setMenuPosToFinal();
			menus[i].setSelect(0);
			
			menus[i].setParameterMenu(parameterStrings[i], 1600, Constants.CENTER);
			menus[i].setParameterSelect(parametersSelect[i]);
			menus[i].setScrollSize(400);
			
		}
		
        t = new Timer((int) Settings.FPS1000, this);
        t.start();
       
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		background.draw(g);
		mainMenu.draw(g);
		//titleMenu1.draw(g);
		//titleMenu2.draw(g);
		//helpBar.draw(g);
		//menuTitleBar.draw(g);
		//menuTitle.draw(g);
		//helpBarString.draw(g);
		menus[mainMenu.getSelect()].draw(g);
		//parameters[menu.getSelect()].draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}
		
		

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
	}

}
