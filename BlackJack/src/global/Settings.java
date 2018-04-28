package global;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import engine.Display;

public class Settings {
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		public static String LANGUAGE = "en-us";
		public static int widthMonitor;
		public static int heightMonitor;
		public static int widthResolution = 1920;
		public static int heightResolution = 1080;
		public static int WIDTH;
		public static int HEIGHT;
		public static boolean FULL_SCREEN = true;
		public static boolean SWITCH = false;
		public static boolean SMOOTH = true;
		public static int FPS = 60;
		public static float FPS1000 = 16.66666666667f;
		
		public static int convertFont (int size){
			int converted = (int)(size * ((double)HEIGHT/(double)1080));
			return converted;
		}
		
		public static int convertPositionX(int position){
			int converted = (int)(position * ((double)WIDTH/(double)1920));
			return converted;
		}
		
		public static int convertPositionY(int position){
			int converted = (int)(position * ((double)HEIGHT/1080));
			return converted;
		}
		
		public static int convertWidth(int width){
			int converted = (int)(width * ((double)WIDTH/(double)1920));
			return converted;
		}
		
		public static int convertHeight(int height){
			int converted = (int)(height * ((double)HEIGHT/1080));
			return converted;
		}
		
		public static String[] createIntString(int min, int max, int count){
			ArrayList<String> list = new ArrayList<>();
			String string;
			for(int i = min; i <= max; i+= count){
				string = Integer.toString(i);
				list.add(string);
			}
			String [] convertedList = list.toArray(new String[list.size()]);
			return convertedList;
		}
		
		public static Dimension getMinimumScreen(Dimension screenSize){
			int width = (int)screenSize.getWidth();
			int height = (int)screenSize.getHeight();
			int[] windowWidth = {640, 960, 1280, 1366, 1600, 1920};
			int[] windowHeight = {360, 540, 720, 768, 900, 1080};
			int chooseWidth = 0;
			int chooseHeight = 0;
			
			
			for (int i = 0; i < windowWidth.length; i++){
				
				if (windowWidth[i] > chooseWidth && windowWidth[i] <= width && 
					windowHeight[i] > chooseHeight && windowHeight[i] <= height){
					chooseWidth = windowWidth[i];
					chooseHeight = windowHeight[i];
				}
				
			}
	
			return new Dimension(chooseWidth, chooseHeight);
			
		}
		
		public static Dimension getCorrectFullScreen(Dimension screenSize){
			int width = (int)screenSize.getWidth();
			int height = (int)screenSize.getWidth()/16*9;
				
			return new Dimension(width, height);
			
		}
		
}
