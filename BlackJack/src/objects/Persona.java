package objects;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import global.Path;

public class Persona implements Path {
	
	private String character;
	private Color[] themeColor = new Color[2];
	private String pathMenuImage;
	
	public Persona(String character){
		this.character = character;
		if (character == "RANDOM") {
			int choice = ThreadLocalRandom.current().nextInt(0,6);
			switch (choice) {
			case 0: this.character = "Galactic Girl"; break;
			case 1: this.character = "Gambit"; break;
			case 2: this.character = "Mad Hatter"; break;
			case 3: this.character = "Red Queen"; break;
			case 4: this.character = "Santa Claus"; break;
			case 5: this.character = "Twisted Fate"; break;
			}
		}
		if (this.character == "Galactic Girl"){
			this.themeColor[0] = new Color(85,115,115);
			this.themeColor[1] = new Color(7,32,38);
		}
		if (this.character == "Gambit") {
			this.themeColor[0] = new Color(135,100,80);
			this.themeColor[1] = new Color(35,15,0);
		}
		if (this.character == "Mad Hatter") {
			this.themeColor[0] = new Color(203,117,74);
			this.themeColor[1] = new Color(60,25,15);
		}
		if (this.character == "Red Queen") {
			this.themeColor[0] = new Color(161,63,56);
			this.themeColor[1] = new Color(46,3,3);
		}
		if (this.character == "Santa Claus") {
			this.themeColor[0] = new Color(160,85,85);
			this.themeColor[1] = new Color(55,0,0);
		}
		if (this.character == "Twisted Fate") {
			this.themeColor[0] = new Color(45,90,140);
			this.themeColor[1] = new Color(10,20,45);
		}
		this.pathMenuImage = (Path.characterMenu+character+" Inative"+".png");
	}
	
	public void setCharacter(String character){
		this.character = character;
	}
	
	public void setThemeColor(int oneORtwo, Color color){
		this.themeColor[oneORtwo - 1] = color;
	}
	
	// GETS
	public String getName() {
		return this.character;
	}
	
	public Color[] getThemeColor() {
		return themeColor;
	}
	
	public String getBackImage() {
		return this.pathMenuImage;
	}
}
