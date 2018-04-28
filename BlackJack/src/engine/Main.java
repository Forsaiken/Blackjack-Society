package engine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import global.Settings;
import global.Strings;
import global.Switch;
import scenes.Intro;
import scenes.MainScreen;
import scenes.Title;

public class Main {
	
	private static Display window;
	private static Intro intro;
	private static Title title;
	private static MainScreen main_screen;
	private static boolean loadSettings = false;
	
	private static CountDownLatch stopSignal;

	public static void main(String[] args) {
		
		File settingsINI = new File("Settings.ini");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Settings.widthMonitor = (int)screenSize.getSize().getWidth();
		Settings.heightMonitor = (int)screenSize.getSize().getHeight();
	
	while (!loadSettings){	
		
		if (!settingsINI.exists()){
			
			Dimension recomendedSize = Settings.getMinimumScreen(screenSize);
			String fullScreen;
			String language = Locale.getDefault().getLanguage();
			
			
			if (screenSize.getWidth() == recomendedSize.getWidth())
				fullScreen = "TRUE";
			else
				fullScreen = "FALSE";
			
			if (language != "pt")
				language = "en-us";
			else
				language = "pt-br";
			
			
			System.out.println("O arquivo Settings.ini não existe!");
			try {
				settingsINI.createNewFile();
				System.out.println("Arquivo Settings.ini criado com sucesso!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Não foi possivel criar o arquivo Settings.ini");
			}
			
			FileWriter settingsWrite = null;
			
			try {
				settingsWrite = new FileWriter(settingsINI);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				settingsWrite.write("WIDTH_RESOLUTION = " + (int)recomendedSize.getWidth() + "\r\n");
				settingsWrite.write("HEIGHT_RESOLUTION = " + (int)recomendedSize.getHeight() + "\r\n");
				settingsWrite.write("FULL_SCREEN = " + fullScreen + "\r\n");
				settingsWrite.write("SMOOTH_RENDER = TRUE" + "\r\n");
				settingsWrite.write("LANGUAGE = " + language);
				settingsWrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Settings.widthResolution = myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 1));
			Settings.heightResolution = myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 2));
			Settings.FULL_SCREEN = myFiles.convertToBoolean(myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 3)));
			Settings.SMOOTH = myFiles.convertToBoolean(myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 4)));
			Settings.LANGUAGE = myFiles.getValueLine("Settings.ini", 5);
			loadSettings = true;
		}
		
	}
	
	// START FRAME
	
	SwingUtilities.invokeLater(Frame);
	
	// START INTRODUCTION SCENE
	
	/**
	
	stopSignal = new CountDownLatch(1);
	SwingUtilities.invokeLater(Introduction);
	
	try {
		stopSignal.await();
		SwingUtilities.invokeLater(endIntro);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	**/
	
	// START TITLE SCENE
	
	stopSignal = new CountDownLatch(1);
	SwingUtilities.invokeLater(Title);
	
	}

	private static Runnable Frame = new Runnable() {
		public void run() {
		window = new Display();
		window.getContentPane().setBackground(Color.WHITE);
		
		Settings.WIDTH = window.getContentPane().getSize().width;
		Settings.HEIGHT = window.getContentPane().getSize().height;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	};
		
	private static Runnable Introduction = new Runnable() {
		public void run() {
			intro = new Intro(window, stopSignal);
    		intro.setVisible(true);
    		window.getContentPane().add(intro);
		}
	};
	
	private static Runnable endIntro = new Runnable() {
		public void run() {
			intro.removeAll();
			intro.invalidate();
			window.getContentPane().removeAll();
			window.revalidate();
			intro = null;
		}
	};
	
	private static Runnable Title = new Runnable() {
        public void run() {
        		try {
					title = new Title(window, stopSignal);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		title.setVisible(true);
        		window.getContentPane().add(title);
       }       
    };
}
		/**
		
		intro = new Intro();		
		window.getContentPane().add(intro);
		
		window.remove(intro);
			
		thread_title = new Thread(t1);
		thread_title.start();
		
		stopSignal = new CountDownLatch(1);
		
		try {
			stopSignal.await();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		stopSignal = new CountDownLatch(1);
		
		thread_main = new Thread(t2);
		thread_main.start();
		
		try {
			stopSignal.await();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
    private static Runnable t1 = new Runnable() {
        public void run() {
            try{
        		intro = new Intro(window, stopSignal);
        		intro.setVisible(true);
        		window.getContentPane().add(intro);
            } catch (Exception e){}
       }
        
    };
	
    private static Runnable t2 = new Runnable() {
        public void run() {
            try{
        		title = new Title(window, stopSignal);
        		window.getContentPane().add(title);
            } catch (Exception e){}
    		try {
    			stopSignal.await();
    		} catch (InterruptedException ex) {
    			ex.printStackTrace();
    		}
    		try {
    			title.setVisible(false);
    			window.remove(title);
				thread_main.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
        
    };
    
    private static Runnable t3 = new Runnable() {
        public void run() {
            try{
            	main_screen = new MainScreen(window, stopSignal);
        		main_screen.setVisible(true);
        		window.getContentPane().add(main_screen);
            } catch (Exception e){}
       }
        
    };
    
}

**/
