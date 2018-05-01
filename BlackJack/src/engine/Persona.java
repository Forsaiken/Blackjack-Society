package engine;

import java.awt.Color;

import global.Path;

public class Persona implements Path {
	
	private String character;
	private Color[] themeColor = new Color[2];
	private String pathMenuImage;
	
	public Persona(String character){
		this.character = character;
		if (character == "Red Queen"){
			this.themeColor[0] = new Color(161,63,56);
			this.themeColor[1] = new Color(46,3,3);
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
