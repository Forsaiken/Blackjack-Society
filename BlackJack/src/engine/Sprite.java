package engine;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.JPanel;

import global.Constants;
import global.Settings;

public class Sprite extends JPanel implements Constants {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES - BASIC PROPERTIES
	
	private int[] width  =  {0,0,0};
	private int[] height =  {0,0,0};
	private int[] posX 	 =  {0,0,0};
	private int[] posY 	 =  {0,0,0};
	private float[] alpha = {0f,1f,1f};
	
	// VARIABLES - SPEED ANIMATION
	
	private boolean animation = false;
	private float spdX, spdY, spdWidth, spdHeight, spdAlpha;
	private int[] cX = {0,0}, cY = {0,0}, cW = {0,0}, cH = {0,0}, cA = {0,0};
	
	// VARIABLES - STRING
	
	private String string;
	private Font font;
	
	private byte textFormat;
	private boolean spacedFont;
	private double fontSpacement;
	
	// VARIABLES - IMAGE
	
	private BufferedImage image;
	private Image scaledImage;
	private byte shape;
	private FontMetrics metrics;
	
	private boolean visibility;
	private Graphics2D g2d;
	private boolean pressed;
	
	// VARIABLES - GRADIENT
	
	private Color color;
	private RadialGradientPaint radialGradient;	
	private float gradientRadius;
	private float[] gradientDiffusion;
	private Color[] gradientColor;
	
	public void draw(Graphics g) {
		g2d = (Graphics2D) g;
		
		if (this.animation == true) {
			
			int ct = 0;
			
			if (this.cX[0] < this.cX[1]) {
				this.posX[1] = (int) (this.posX[0] + (cX[0]+1) * this.spdX);
				cX[0]++;
			} else
				ct += 1;
			
			if (this.cY[0] < this.cY[1]) {
				this.posY[1] = (int) (this.posY[0] + (cY[0]+1) * this.spdY);
				cY[0]++;
			} else
				ct += 1;
				
			if (this.cW[0] < this.cW[1]) {
				this.width[1] = (int) (this.width[0] + (cW[0]+1) * this.spdWidth);
				cW[0]++;
			} else
				ct += 1;
			
			if (this.cH[0] < this.cH[1]) {
				this.height[1] = (int) (this.height[0] + (cH[0]+1) * this.spdHeight);
				cH[0]++;
			} else
				ct += 1;
			
			if (this.cA[0] < this.cA[1]) {
				if (this.alpha[1] + spdAlpha > 0f && this.alpha[1] + spdAlpha < 1f)
					this.alpha[1] += this.spdAlpha;
				else
					this.alpha[1] = this.alpha[2];
				cA[0]++;
			} else
				ct += 1;
			
			if (ct == 5) {
				this.animation = false;
				this.resetCountAnimation();
			}
		}
		
		if (image != null){
			 
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[1]));
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawImage(scaledImage, posX[1], posY[1], width[1], height[1], null);
			
		} else if (string != null){
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[1]));
			g2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			if (spacedFont == true) {
				Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
				attributes.put(TextAttribute.TRACKING, this.fontSpacement );
				this.font = font.deriveFont(attributes);
			}
			
			g2d.setFont(font);
			g2d.setColor(color);
			if (textFormat == Constants.CENTER)
				g2d.drawString(string, posX[1] - this.getStringWidth(g)/2, posY[1]);
			else 
				g2d.drawString(string, posX[1], posY[1]);
			
		} else if (shape == Constants.RECT) {
			
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[1]));
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			
			if (this.radialGradient != null) {
				g2d.setPaint(this.radialGradient);
			} else {
				g2d.setColor(color);
			}
			
			g2d.fillRect(posX[1], posY[1], width[1], height[1]);
			
		}
	}
	
	// SETS - GEOMETRIC SHAPES
	
	public void setFillRect(int width, int height, Color color){
		
		this.width[2] = width;
		this.height[2] = height;
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
		this.shape = Constants.RECT;
		this.color = color;
		
	}
	
	// SETS - IMAGE
	
	public void setImage(String path){
		try {
			image = ImageIO.read(new File(path));
			scaledImage = image.getScaledInstance(Settings.convertWidth(image.getWidth()), Settings.convertHeight(image.getHeight()), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Arquivo (" + path + ") não encontrado!");
		}
		
		this.width[2] = Settings.convertWidth(image.getWidth());
		this.height[2] = Settings.convertHeight(image.getHeight());
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}
	
	public void redimensionImageByWidth(int width) {
		float scaledHeight = image.getHeight() * ((float)width / (float)image.getWidth());
		scaledImage = image.getScaledInstance(width, (int) scaledHeight, Image.SCALE_SMOOTH);
		this.width[2] = width;
		this.height[2] = (int) scaledHeight;
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}

	// SETS - STRING
	
	public void setString(String string, Font font, Color color, float alpha){
		this.string = string;
		this.font = font;
		this.color = color;
		this.alpha[2] = alpha;
		this.alpha[1] = alpha;
	}
	
	public void setStringName(String string){
		this.string = string;
	}

	public void setSpacementString(double spacement){
		this.spacedFont = true;
		this.fontSpacement = spacement;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFormatString(byte formatText) {
		this.textFormat = formatText;
	}
	
	// SETS - LOCATION

	public void setInitialLocation(int x, int y) {
		this.posX[0] = x;
		this.posY[0] = y;
	}	
	
	public void setLocation(int x, int y) {
		this.posX[1] = x;
		this.posY[1] = y;
	}
	
	public void setFinalLocation(int x, int y) {
		this.posX[2] = x;
		this.posY[2] = y;
	}
	
	public void setLocationToInitial() {
		this.posX[1] = this.posX[0];
		this.posY[1] = this.posY[0];
	}
	
	public void setLocationToFinal() {
		this.posX[1] = this.posX[2];
		this.posY[1] = this.posY[2];
	}
	
	// SETS - POSITION X
	
	public void setInitialPosX(int x) {
		this.posX[0] = x;
	}
	
	public void setPosX(int x) {
		this.posX[1] = x;
	}
	
	public void setFinalPosX(int x) {
		this.posX[2] = x;
	}
	
	public void setPosXToInitial() {
		this.posX[1] = this.posX[0];
	}
	
	public void setPosXToFinal() {
		this.posX[1] = this.posX[2];
	}
	
	// SETS - POSITION Y
	
	public void setInitialPosY(int y) {
		this.posY[0] = y;
	}
	
	public void setPosY(int y) {
		this.posY[1] = y;
	}
	
	public void setFinalPosY(int y) {
		this.posY[2] = y;
	}
	
	public void setPosYToInitial() {
		this.posY[1] = this.posY[0];
	}
	
	public void setPosYToFinal() {
		this.posY[1] = this.posY[2];
	}
	
	// SETS - DIMENSION
		
	public void setInitialDimension(int width, int height){
		this.width[0] = width;
		this.height[0] = height;
	}
	
	public void setDimension(int width, int height){
		this.width[1] = width;
		this.height[1] = height;
	}
	
	public void setFinalDimension(int width, int height){
		this.width[2] = width;
		this.height[2] = height;
	}
	
	public void setDimensionToInitial() {
		this.width[1] = this.width[0];
		this.height[1] = this.height[0];
	}
	
	public void setDimensionToFinal() {
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}
	
	// SETS - WIDTH
	
	public void setInitialWidth(int width){
		this.width[0] = width;
	}
	
	public void setWidth(int width){
		this.width[1] = width;
	}
	
	public void setFinalWidth(int width){
		this.width[2] = width;
	}
	
	public void setWidthToInitial() {
		this.width[1] = this.width[0];
	}
	
	public void setWidthToFinal() {
		this.width[1] = this.width[2];
	}
	
	// SETS - HEIGHT
	
	public void setInitialHeight(int height){
		this.height[0] = height;
	}
	
	public void setHeight(int height){
		this.height[1] = height;
	}
	
	public void setFinalHeight(int height){
		this.height[2] = height;
	}
	
	public void setHeightToInitial() {
		this.height[1] = this.height[0];
	}
	
	public void setHeightToFinal() {
		this.height[1] = this.height[2];
	}
	
	// SETS - ALPHA
	
	public void setVisible (boolean visibility) {
		if (visibility == true){
			this.alpha[1] = 1f;
		} else {
			this.alpha[1] = 0f;
		}
	}

	public void setInitialAlpha(float alpha){
		this.alpha[0] = alpha;
	}
	
	public void setAlpha(float alpha){
		this.alpha[1] = alpha;
	}
	
	public void setFinalAlpha(float alpha) {
		this.alpha[2] = alpha;
	}
	
	public void setAlphaToInitial() {
		this.alpha[1] = this.alpha[0];
	}
	
	public void setAlphaToFinal() {
		this.alpha[1] = this.alpha[2];
	}
	
	// SETS - ANIMATION
	
	public void setAnimation (boolean animation){
		this.animation = animation;
	}
	
	public void setMotionAnimation(float posX, float posY, float width, float height, float alpha){
		if (posX != 0) {
			this.spdX = (this.posX[2] - this.posX[0]) / (posX * Settings.FPS);
			this.cX[1] = (int) (Settings.FPS * posX);
		} else { this.cX[1] = 0;
		} if (posY != 0) {
			this.spdY = (this.posY[2] - this.posY[0]) / (posY * Settings.FPS);
			this.cY[1] = (int) (Settings.FPS * posY);
		} else { this.cY[1] = 0;
		} if (width != 0) {
			this.spdWidth = (this.width[2] - this.width[0]) / (width * Settings.FPS);
			this.cW[1] = (int) (Settings.FPS * width);
		} else { this.cW[1] = 0;
		} if (height != 0) {
			this.spdHeight = (this.height[2] - this.height[0]) / (height * Settings.FPS);
			this.cH[1] = (int) (Settings.FPS * height);
		} else { this.cH[1] = 0;
		} if (alpha != 0) {
			this.spdAlpha = (this.alpha[2] - this.alpha[0]) / (alpha * Settings.FPS);
			this.cA[1] = (int) (Settings.FPS * alpha);
		} else { this.cA[1] = 0;
		}
	}
	
	public void setPressed(boolean pressed){
		
		this.pressed = pressed;
		
	}
	
	public void resetCountAnimation () {
		this.cX[0] = 0;
		this.cY[0] = 0;
		this.cW[0] = 0;
		this.cH[0] = 0;
		this.cA[0] = 0;
	}
	
	// SETS - COLORS
	
	public void setColor (Color color){
		this.color = color;
	}
	
	public void setRadialGradient (int radius, float[] dist, Color[] colors) {
		this.gradientRadius = radius;
		this.gradientDiffusion = dist;
		this.gradientColor = colors;
		this.radialGradient = new RadialGradientPaint(this.posX[1] + this.width[2]/2,this.posY[1] + this.height[2]/2, radius, dist, colors, CycleMethod.NO_CYCLE);
	}

	public void grayScale() {
		ImageFilter filter = new GrayFilter(true, 0);  
		ImageProducer producer = new FilteredImageSource(scaledImage.getSource(), filter);  
		scaledImage = Toolkit.getDefaultToolkit().createImage(producer);
	}
	
	// GETS - STRING
	
	public Font getFont() {
		return this.font;
	}
	
	public int getStringWidth(Graphics g){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		return metrics.stringWidth(string);
	}
	
	public int getStringHeight(Graphics g){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		int height = (int)font.createGlyphVector(metrics.getFontRenderContext(), string).getVisualBounds().getHeight();	
		return height;
		
	}
	
	// GETS - LOCATION
	
	public int getInitX() {
		return this.posX[0];
	}
	
	public int getPosX() {
		return this.posX[1];
	}
	
	public int getFinalX() {
		return this.posX[2];
	}
	
	public int getInitY() {
		return this.posY[0];
	}
	
	public int getPosY() {
		return this.posY[1];
	}
	
	public int getFinalY() {
		return this.posY[2];
	}
		
	// GETS - DIMENSION
	
	public int getInitWidth() {
		return width[0];
	}
	
	public int getWidth() {
		return width[1];
	}
	
	public int getFinalWidth() {
		return width[2];
	}
	
	public int getInitHeight() {
		return height[0];
	}
	
	public int getHeight() {
		return height[1];
	}
	
	public int getFinalHeight() {
		return height[2];
	}
	
	// GETS - ALPHA
	
	public float getInitAlpha() {
		return alpha[0];
	}
	
	public float getAlpha() {
		return alpha[1];
	}
	
	public float getFinalAlpha() {
		return alpha[2];
	}
	
	// GETS - ANIMATION
	
	public boolean getAnimation() {
		return this.animation;
	}
	
	// GETS - IMAGE
	
	public Image getImage(){
		return scaledImage;
	}
	
}
