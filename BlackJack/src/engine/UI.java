package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import global.Constants;
import global.Path;
import global.Settings;

public class UI implements KeyListener {
	
	// VARIABLES - PROPERTIES
	
	private String menuPath;
	
	// VARIABLES - KEY LISTENER
	
	private boolean canScroll;
	
	// VARIABLES - TITLE BAR
		
	private boolean hasTitleMenu;
	
	private Sprite tbString;
	private Sprite tbRect;
	
	private Font tbFont;
	private int tbFormat;
	private boolean tbAnimation;
	private int[] tbPosX = {0,0,0};
	private int[] tbPosY = {0,0,0};
	private int[] tbWidth = {0,0,0};
	private int[] tbHeight = {0,0,0};
	private boolean tbHasRect;
	private Color tbFontColor;
	private Color tbRectColor;
	private float[] tbRectAlpha = {0f,0f,0f};
	private float[] tbFontAlpha = {0f,0f,0f};
	
	private float spdtbX,spdtbY, spdtbW, spdtbH;
	private float spdtbFontAlpha, spdtbRectAlpha;
	
	// VARIABLES - PARAMETERS
	
	private boolean hasParameterMenu;
	private String[][] menuParameterArray;
	private Sprite[][] menuParameterStrings;
	private int[] menuParameterSelect;
	private int parameterPosX;
	private int parameterTextFormat;
	private int scrollsize;
	
	// VARIABLES - SELECT MENU
	
	private int selectedArray;
	
	private Font selectFont;
	private Color selectFontColor;
	private float selectFontAlpha;
	
	private Sprite selectRect;
	private Color selectRectColor;
	private float selectRectAlpha;
	
	private Sprite selectorImage;
	
	private Sprite selectedImage;
	
	// VARIABLES - MENU ARRAY
	
	private String[][] menuArray;
	private Sprite[][] menuStrings;
	private int arraySpacement;
	
	private boolean menuChangeSpacement;
	private double menuStringSpacement;
	
	private byte menuFormatText;	
	private Font menuFont;
	private Color menuFontColor;
	private float menuFontAlpha[] = {0f,0f,0f};
	
	private int menuPosX[] = {0,0,0};
	private int menuPosY[] = {0,0,0};
	
	private boolean drawIcons;
	
	private boolean menuAnimation;
	
	private float spdMenuPosX = 0;
	private float spdMenuPosY = 0;
	private float spdMenuAlpha = 0f;
	
	// VARIABLES - BACK MENU
	
	private boolean hasBackImage;
	private boolean hasBackRect;
	private boolean backAnimation;
	
	private Sprite backImage;
	private Sprite backRect;
	
	private Color backColor;
	
	private float[] backColorAlpha = {0f,0f,0f};
	private float[] backImageAlpha = {0f,0f,0f};
	
	private int[] backPosX	 = 	{0,0,0};
	private int[] backPosY	 = 	{0,0,0};
	private int[] backWidth	 = 	{0,0,0};
	private int[] backHeight = 	{0,0,0};
	
	private float spdBackPosX,spdBackPosY, spdBackWidth, spdBackHeight;
	private float spdBackColorAlpha, spdBackImageAlpha;
		
	// ACTIONS
	
	public void draw(Graphics g) {
		
		// Animation Back Menu Position update
		
		/**
		
		if (this.menuAnimation) {
			
			if (menuPosX[1] + this.spdMenuPosX <= menuPosX[2])
				this.menuPosX[1] += this.spdMenuPosX;
			
			if (menuPosY[1] + this.spdMenuPosY <= menuPosY[2])
				this.menuPosY[1] += this.spdMenuPosY;
			
			if (this.menuFontAlpha[1] + this.spdMenuAlpha <= this.menuFontAlpha[2])
				this.menuFontAlpha[1] += this.spdMenuAlpha;
			
		}
		
		**/
		
		// Drawing Back Menu
		
		if (this.backImage != null)	{	backImage.draw(g);	}
		if (this.backRect != null)	{	backRect.draw(g);	}
		
		// Drawing Title Menu
		
		if (this.tbRect != null || this.tbString != null) {
			
			tbRect.draw(g);
			
			if (this.tbFormat == Constants.CENTER)
				this.tbString.setLocation(this.tbRect.getWidth()/2 - this.tbString.getStringWidth(g)/2,
						this.tbRect.getPosY() + this.tbRect.getHeight()/2 - this.tbString.getStringHeight(g)/2);
			if (this.tbFormat == Constants.LEFT_TO_RIGHT) {
				this.tbString.setLocation(this.tbRect.getPosX() + 50,
						this.tbRect.getPosY() + this.tbString.getStringHeight(g) + (this.tbRect.getHeight()/2 - this.tbString.getStringHeight(g)/2));
			}
			
			tbRect.draw(g);
			tbString.draw(g);
			System.out.println(tbString.getPosX() + " " + this.tbRect.getPosY());
		}
		
		// Drawing Selector
		
		if (this.selectorImage != null) {
			this.selectorImage.setLocation(menuPosX[1], (this.menuPosY[1] - arraySpacement) + ((this.selectedArray + 1) * arraySpacement));
			this.selectorImage.draw(g);
		}
		
		int fontHeight = menuStrings[0][0].getStringHeight(g);
		
		if (this.selectRect != null) {
			this.selectRect.setLocation(backRect.getPosX(), (menuPosY[2] + (arraySpacement/2 - fontHeight/2) + (arraySpacement * selectedArray)));
			this.selectRect.draw(g);
		}
		
		// Drawing Menu
		
		for(int i = 0; i < menuStrings[0].length; i++) {
			if (i == this.selectedArray) {
				menuStrings[0][i].setFont(this.selectFont);
				menuStrings[0][i].setColor(this.selectFontColor);
			} else {
				menuStrings[0][i].setFont(this.menuFont);
				menuStrings[0][i].setColor(this.menuFontColor);
			}

			menuStrings[0][i].draw(g);
		}
		
		if (this.hasParameterMenu) {
			// draw String Sprite parameter
			
			Sprite arrowLeft = new Sprite();
			Sprite arrowRight = new Sprite();
			
			if (this.scrollsize != 0) {
				arrowLeft.setString("<", this.menuFont, this.menuFontColor, 1f);
				arrowLeft.setPosX(this.parameterPosX - this.scrollsize/2);
				arrowRight.setString(">", this.menuFont, this.menuFontColor, 1f);
				arrowRight.setPosX(this.parameterPosX + this.scrollsize/2);
				
			}
			
			for (int i = 0; i < this.menuParameterArray.length; i++) {
				if (this.parameterTextFormat == Constants.CENTER) {
					int parameterFontWidth = this.menuParameterStrings[i][this.menuParameterSelect[i]].getStringWidth(g);
					this.menuParameterStrings[i][this.menuParameterSelect[i]].setPosX(this.parameterPosX - parameterFontWidth/2);
				}
				
				this.menuParameterStrings[i][this.menuParameterSelect[i]].setPosY(menuPosY[2] + (this.arraySpacement * i));
				this.menuParameterStrings[i][this.menuParameterSelect[i]].draw(g);
				
				if (this.menuParameterArray[i].length > 1) {
					arrowLeft.setPosY(menuPosY[2] + (this.arraySpacement * i));
					arrowRight.setPosY(menuPosY[2] + (this.arraySpacement * i));
					arrowLeft.draw(g);
					arrowRight.draw(g);
					
				}
			}
		}
	}
	
	public void open() {
		
		if(this.tbRect != null) {
			this.tbRect.setLocationToInitial();
			this.tbRect.setDimensionToInitial();
			this.tbRect.setAlphaToInitial();
			
			this.tbRect.setMotionAnimation(spdtbX, spdtbY, spdtbW, spdtbH, spdtbRectAlpha);
			this.tbRect.setAnimation(true);
		}
		
		if(this.tbString != null) {
			this.tbString.setAlphaToInitial();
			this.tbString.setMotionAnimation(0,0,0,0,spdtbFontAlpha);
			this.tbString.setAnimation(true);
		}
		
		if (this.backRect != null) {
			this.backRect.setInitialLocation(this.backPosX[0], this.backPosY[0]);
			this.backRect.setInitialDimension(this.backWidth[0], this.backHeight[0]);
			this.backRect.setInitialAlpha(this.backColorAlpha[0]);
			
			this.backRect.setFinalLocation(this.backPosX[2], this.backPosY[2]);
			this.backRect.setFinalDimension(this.backWidth[2], this.backHeight[2]);
			this.backRect.setFinalAlpha(this.backColorAlpha[2]);
			
			this.backRect.setLocationToInitial();
			this.backRect.setDimensionToInitial();
			this.backRect.setAlphaToInitial();
			
			this.backRect.setMotionAnimation(this.spdBackPosX, this.spdBackPosY, this.spdBackWidth, this.spdBackHeight, this.spdBackColorAlpha);
			this.backRect.setAnimation(true);
		}
		
		if (this.backImage != null) {
			this.backImage.setInitialLocation(this.backPosX[0], this.backPosY[0]);
			this.backImage.setInitialAlpha(this.backImageAlpha[0]);
			
			this.backImage.setFinalLocation(this.backPosX[2], this.backPosY[2]);
			this.backImage.setFinalAlpha(this.backImageAlpha[2]);
			
			this.backImage.setLocationToInitial();
			this.backImage.setAlphaToInitial();
			
			this.backImage.setMotionAnimation(this.spdBackPosX, this.spdBackPosY, this.spdBackWidth, this.spdBackHeight, this.spdBackImageAlpha);
			this.backImage.setAnimation(true);
		}
		
		if (this.menuStrings != null) {
			for (int i = 0; i < menuStrings[0].length; i++) {
				this.menuStrings[0][i].setInitialLocation(this.menuPosX[0], this.menuPosY[0] + this.arraySpacement * i);
				this.menuStrings[0][i].setFinalLocation(this.menuPosX[2], this.menuPosY[2] + this.arraySpacement * i);
				this.menuStrings[0][i].setLocationToInitial();
			
				this.menuStrings[0][i].setInitialAlpha(this.menuFontAlpha[0]);
				this.menuStrings[0][i].setFinalAlpha(this.menuFontAlpha[2]);
				this.menuStrings[0][i].setAlphaToInitial();
			
				this.menuStrings[0][i].setMotionAnimation(this.spdMenuPosX, this.spdMenuPosY, 0, 0, this.spdMenuAlpha);
				this.menuStrings[0][i].setAnimation(true);
				this.menuAnimation = true;
			}
		}
	}
	
	public void lock() {
		if (this.menuStrings != null) {
			for (int i = 0; i < menuStrings[0].length; i++) {
				this.menuStrings[0][i].resetCountAnimation();
				this.menuStrings[0][i].setInitialLocation(this.menuPosX[2], this.menuPosY[2] + this.arraySpacement * i);
				this.menuStrings[0][i].setFinalLocation(this.menuPosX[0], this.menuPosY[0] + this.arraySpacement * i);
				this.menuStrings[0][i].setLocationToInitial();
			
				this.menuStrings[0][i].setInitialAlpha(this.menuFontAlpha[2]);
				this.menuStrings[0][i].setFinalAlpha(this.menuFontAlpha[0]);
				this.menuStrings[0][i].setAlphaToInitial();
			
				this.menuStrings[0][i].setMotionAnimation(this.spdMenuPosX, this.spdMenuPosY, 0, 0, this.spdMenuAlpha);
			
				this.menuStrings[0][i].setAnimation(true);
				this.menuAnimation = true;
			}
		}
	}
	
	// SETS - TITLE BAR
	
	public void setTitleBar(String string, int posX, int posY, int width, int height, int textFormat, Font font, Color fontColor, Color rectColor, float rectAlpha, float stringAlpha) {
		
		this.hasTitleMenu = true;

		this.tbPosX[2] = Settings.convertPositionX(posX);
		this.tbPosY[2] = Settings.convertPositionY(posY);
		this.tbWidth[2] = Settings.convertWidth(width);
		this.tbHeight[2] = Settings.convertHeight(height);
		this.tbFontAlpha[2] = stringAlpha;
		this.tbFontColor = fontColor;
		this.tbRectColor = rectColor;
		this.tbFormat = textFormat;
		
		if (string != null) {
			this.tbString = new Sprite();
			this.tbString.setString(string, font, fontColor, stringAlpha);
		}
		
		if (rectColor != null) {
			this.tbRect = new Sprite();
			this.tbRect.setFillRect(this.tbWidth[2], this.tbHeight[2], this.tbRectColor);
			this.tbRect.setFinalLocation(this.tbPosX[2], this.tbPosY[2]);
			this.tbRect.setLocation(this.tbPosX[2], this.tbPosY[2]);
			this.tbRect.setFinalAlpha(this.tbRectAlpha[2]);
			this.tbRect.setAlpha(this.tbRectAlpha[2]);
		}
	}
	
	public void setTitleBarInitialPoints (int posX, int posY, int width, int height, float rectAlpha, float fontAlpha) {
		this.tbPosX[0] = Settings.convertPositionX(posX);
		this.tbPosY[0] = Settings.convertPositionY(posY);
		this.tbWidth[0] = Settings.convertWidth(width);
		this.tbHeight[0] = Settings.convertHeight(height);
		this.tbFontAlpha[0] = fontAlpha;
		this.tbRectAlpha[0] = rectAlpha;
		
		if (tbString != null) {
			this.tbString.setInitialAlpha(fontAlpha);
		}
		
		if (tbRect != null) {
			this.tbRect.setInitialLocation(tbPosX[0], tbPosY[0]);
			this.tbRect.setInitialDimension(this.tbWidth[0], this.tbHeight[0]);
			this.tbRect.setInitialAlpha(rectAlpha);
		}
	}
	
	public void setTitleAnimation (boolean animation) {
		this.tbAnimation = animation;
	}
	
	public void setTitleBarAnimationSpeed(float PosX,float  PosY,float Width, float Height, float fontAlpha, float rectAlpha) {
		this.spdtbX		= PosX;
		this.spdtbY		= PosY;
		this.spdtbW		= Width;
		this.spdtbH		= Height;
		this.spdtbFontAlpha = fontAlpha;
		this.spdtbRectAlpha = rectAlpha;
	}
	
	public void setTitleBarPosToFinal() {
		this.tbPosX[1] = this.tbPosX[2];
		this.tbPosY[1] = this.tbPosY[2];
		this.tbWidth[1] = this.tbWidth[2];
		this.tbHeight[1] = this.tbHeight[2];
		this.tbRectAlpha[1] = this.tbRectAlpha[2];
		this.tbFontAlpha[1] = this.tbFontAlpha[2];
	}
	
	// SETS - MENU ARRAY
	
	public void setMenuArray(String[][] menuArray, int arraySpacement, int posX, int posY, byte formatText, Font font, Color color, float alpha) {
		
		this.menuArray = menuArray;
		this.arraySpacement = Settings.convertPositionY(arraySpacement);
		this.menuFormatText = formatText;
		this.menuFont = font;
		this.menuFontColor = color;
		this.menuFontAlpha[2] = alpha;
		this.menuPosX[2] = Settings.convertPositionX(posX);
		this.menuPosY[2] = Settings.convertPositionY(posY);
		
		this.menuPosX[1] = this.menuPosX[2];
		this.menuPosY[1] = this.menuPosY[2];
		this.menuFontAlpha[1] = this.menuFontAlpha[2];
				
		
		Sprite[][] tempArray = new Sprite[1][menuArray[0].length];
		
		for(int i = 0; i < menuArray[0].length; i++) {
			
			tempArray[0][i] = new Sprite();
			tempArray[0][i].setString(menuArray[0][i], menuFont, menuFontColor, menuFontAlpha[2]);
			if (this.menuChangeSpacement) {
				tempArray[0][i].setSpacementString(this.menuStringSpacement);
			}
			tempArray[0][i].setLocation(this.menuPosX[2], this.menuPosY[2] + (this.arraySpacement * i));
			tempArray[0][i].setFormatString(this.menuFormatText);
		}
		
		this.menuStrings = tempArray;
		
	}
		
	public void setMenuAnimation(boolean animation) {
		this.menuAnimation = animation;
	}
		
	public void setMenuInitialPoint(int iPosX,int iPosY,float fontAlpha) {
		
		this.menuPosX[0]		= Settings.convertPositionX(iPosX);
		this.menuPosY[0]		= Settings.convertPositionY(iPosY);
		this.menuFontAlpha[0]	= fontAlpha;
		
		if (this.menuStrings != null) {
			for (int i = 0; i < menuStrings[0].length; i++) {
				this.menuStrings[0][i].setInitialLocation(this.menuPosX[0], this.menuPosY[0] * this.arraySpacement * i);
				this.menuStrings[0][i].setInitialAlpha(this.menuFontAlpha[0]);
			}
		}
	}
	
	public void setMenuAnimationSpeed(float PosX,float  PosY,float StringAlpha) {
		this.spdMenuPosX		= PosX;
		this.spdMenuPosY		= PosY;
		this.spdMenuAlpha		= StringAlpha;
	}
	
	public void setMenuPosToFinal() {
		this.menuPosX[1] = this.menuPosX[2];
		this.menuPosY[1] = this.menuPosY[2];
		this.menuFontAlpha[1] = this.menuFontAlpha[2];
	}
	
	public void setMenuPosToInitial() {
		this.menuPosX[1] = this.menuPosX[0];
		this.menuPosY[1] = this.menuPosY[0];
		this.menuFontAlpha[1] = this.menuFontAlpha[0];
		
		if (this.menuStrings != null) {
			for (int i = 0; i < menuStrings[0].length; i++) {
				this.menuStrings[0][i].setLocationToInitial();
				this.menuStrings[0][i].setAlphaToInitial();
			}
		}
	}
	
	public void setMenuAlpha(float alpha) {
		this.menuFontAlpha[1] = alpha;
	}
	
	// SETS - PARAMETER MENU
	
	public void setParameterMenu(String[][] parameterArray, int posX, int formatText) {
		this.menuParameterArray = parameterArray;
		this.parameterPosX = Settings.convertPositionX(posX);
		this.parameterTextFormat = formatText;
		this.hasParameterMenu = true;
		
		Sprite[][] tempArray = new Sprite[this.menuParameterArray.length][];
		
		for (int x = 0; x < this.menuParameterArray.length; x++) {
			
			Sprite[] tempArray2 = new Sprite[this.menuParameterArray[x].length];
			
			for (int y = 0; y < this.menuParameterArray[x].length; y++) {				
							 
				tempArray2[y] = new Sprite();
				tempArray2[y].setString(this.menuParameterArray[x][y], this.menuFont, this.menuFontColor, this.menuFontAlpha[2]);
				tempArray2[y].setLocation(this.parameterPosX, 100);
				
			}
			
			tempArray[x] = tempArray2;
			
		}
		
		this.menuParameterStrings = tempArray;
		
	}
	
	public void setParameterSelect(int[] selectArray) {
		this.menuParameterSelect = selectArray;
	}
	
	public void setScrollSize(int width) {
		
		this.scrollsize = Settings.convertWidth(width);
		
	}
	
	// SETS - SELECT MENU
	
	public void setSelectorMenu(Color selectRectColor, float selectRectAlpha, Font selectFont, Color selectFontColor, float selectFontAlpha) {
		
		if (selectRectColor != null) {
			this.selectRect = new Sprite();
			this.selectRect.setFillRect(this.backRect.getFinalWidth(), arraySpacement - (arraySpacement * 2), selectRectColor);
			this.selectRect.setAlpha(selectRectAlpha);
		}
		
		if (selectFont != null) {
			this.selectFont = selectFont;
			this.selectFontColor = selectFontColor;
			this.selectFontAlpha = selectFontAlpha;
		}
	}
	
	public void setSelectorImage(String imagePath, float imageAlpha) {
		
		this.selectorImage = new Sprite();
		this.selectorImage.setImage(imagePath);
		this.selectorImage.setAlpha(imageAlpha);
	}
	
	public void setSelectedImage(String imagePath, float imageAlpha) {
		
		this.selectedImage = new Sprite();
		this.selectedImage.setImage(imagePath);
		this.selectedImage.setAlpha(imageAlpha);
	}
	
	public void setSelect(int array) {
		this.selectedArray = array;
	}
	
	// SETS - BACK MENU
	
	public void setBackImage(String imagePath, float imageAlpha){
		
		this.backImageAlpha[2] = imageAlpha;
		
		this.hasBackImage = true;
		
		backImage = new Sprite();
		backImage.setImage(imagePath);
		backImage.setAlpha(imageAlpha);
		backImage.setLocation(backPosX[0], backPosY[0]);
	}
	
	public void setBackMenu(boolean hasBackRect, int positionX, int positionY, int width, int height, Color rectColor, float rectAlpha){
		
		this.hasBackRect = 	hasBackRect;
		this.backPosX[2]	 =	Settings.convertPositionX(positionX);
		this.backPosY[2]	 = 	Settings.convertPositionY(positionY);
		this.backWidth[2]	 =	Settings.convertWidth(width);
		this.backHeight[2]   =	Settings.convertHeight(height);
		this.backColor 	 = 	rectColor;
		this.backColorAlpha[2] = rectAlpha;
		
		if (this.hasBackRect) {
			backRect = new Sprite();
			backRect.setFillRect(this.backWidth[2], this.backHeight[2], this.backColor);
			backRect.setLocation(this.backPosX[2], this.backPosY[2]);
			backRect.setAlpha(this.backColorAlpha[2]);
		}
	}
	
	public void setBackInitialPoint(int iPosX,int iPosY,int iWidth,int iHeight ,float alphaImage ,float alphaRect) {
		
		this.backPosX[0]		= Settings.convertPositionX(iPosX);
		this.backPosY[0]		= Settings.convertPositionY(iPosY);
		this.backWidth[0]		= Settings.convertWidth(iWidth);
		this.backHeight[0]		= Settings.convertHeight(iHeight);
		this.backImageAlpha[0]	= alphaImage;
		this.backColorAlpha[0]	= alphaRect;
		
		if (this.hasBackImage) {
			this.backImage.setLocation(backPosX[0],backPosY[0]);
			this.backImage.setDimension(backWidth[0], backHeight[0]);
			this.backImage.setAlpha(backImageAlpha[0]);
		}
		
		if (this.hasBackRect) {
			this.backRect.setLocation(backPosX[0],backPosY[0]);
			this.backRect.setDimension(backWidth[0], backHeight[0]);
			this.backRect.setAlpha(backColorAlpha[0]);
		}
	}
	
	public void setBackPosToInitial() {
		this.backPosX[1] = this.backPosX[0];
		this.backPosY[1] = this.backPosY[0];
		this.backWidth[1] = this.backWidth[0];
		this.backHeight[1] = this.backHeight[0];
		this.backImageAlpha[1] = this.backImageAlpha[0];
		this.backColorAlpha[1] = this.backColorAlpha[0];
		
		if (this.backRect != null) {
			this.backRect.setLocationToInitial();
			this.backRect.setAlphaToInitial();
			this.backRect.setDimensionToInitial();
		}
		
		if (this.backImage != null) {
			this.backImage.setLocationToInitial();
			this.backImage.setAlphaToInitial();
		}
	}
	
	public void setBackPosToFinal() {
		this.backPosX[1] = this.backPosX[2];
		this.backPosY[1] = this.backPosY[2];
		this.backWidth[1] = this.backWidth[2];
		this.backHeight[1] = this.backHeight[2];
		this.backImageAlpha[1] = this.backImageAlpha[2];
		this.backColorAlpha[1] = this.backColorAlpha[2];
		
		if (this.backRect != null) {
			this.backRect.setLocationToFinal();
			this.backRect.setAlphaToFinal();
			this.backRect.setDimensionToFinal();
		}
		
		if (this.backImage != null) {
			this.backImage.setLocationToFinal();
			this.backImage.setAlphaToFinal();
		}
	}
	
	public void setBackAnimationSpeed(float posX,float posY,float width,float height ,float alphaRect ,float alphaImage) {
		
		this.spdBackPosX = posX;
		this.spdBackPosY = posY;
		this.spdBackWidth = width;
		this.spdBackHeight = height;
		this.spdBackImageAlpha = alphaImage;
		this.spdBackColorAlpha = alphaRect;
		 
	}
	
	public void setBackAnimation(boolean play) {
		if (this.backRect != null) 
		this.backRect.setAnimation(true);
		if (this.backImage != null)
		this.backImage.setAnimation(true);
	}
	
	// SETS - UI PROPERTIES
	
	public void setMenuProperties(String menuName) {
		this.menuPath = Path.UIpath + menuName + "/";
	}
	
	// GETS
	
	public int getMenuHeight() {
			for (int i = 0; i<this.menuStrings[0].length;i++) {
			}
			return 0;
		}
	
	public int getSelect() {
		return this.selectedArray;
	}
	
	// KEY LISTENER
	
	public void setKeyListener (JFrame display) {
		display.addKeyListener(this);
	}
	
	public void setKeyScroll (boolean canScroll) {
		this.canScroll = canScroll;
	}
	
	public void removeKeyListener (JFrame display) {
		display.removeKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (this.canScroll) {
			if (this.getSelect() == 0 && e.getKeyCode() == KeyEvent.VK_UP){
				this.setSelect(this.menuStrings[0].length - 1);
				System.out.println("Pressed UP!");
			} else if (this.getSelect() == this.menuStrings[0].length - 1 && e.getKeyCode() == KeyEvent.VK_DOWN){
				this.setSelect(0);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				this.setSelect(this.getSelect() + 1);
			} else if (e.getKeyCode() == KeyEvent.VK_UP){
				this.setSelect(this.getSelect() - 1);
			}
		}
		System.out.println(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}	
}
