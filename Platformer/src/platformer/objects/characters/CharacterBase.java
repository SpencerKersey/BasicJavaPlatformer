package platformer.objects.characters;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class CharacterBase {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
    public ArrayList<ArrayList<Integer>> maskArray;

    public CharacterBase(int x, int y) {
    	maskArray = new ArrayList<ArrayList<Integer>>();
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    public ArrayList<ArrayList<Integer>> getMaskArray() {
    	
    	// Get the current dimensions of the currently displayed image
    	getImageDimensions();
    	
    	// Turn the normal image into a BufferedImage to easily get RGB Values
    	BufferedImage buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics2D buffImageGraphics = buffImage.createGraphics();
    	buffImageGraphics.drawImage(buffImage, 0, 0, null);
    	
    	// Set each value in the mask ArrayList to 1 or 0 based on whether it exists or not
    	for(int i = 0; i < width; i++) {
    		maskArray.add(new ArrayList<Integer>());
    		for(int j = 0; j < height; j++) {
        		if(buffImage.getRGB(i, j) != -16777216) {
        			maskArray.get(i).add(1);
        		} else {
        			maskArray.get(i).add(0);
        		}
        	}
    	}
    	
    	// Return the completed maskArray
    	return maskArray;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getCollisionX() {
        return getX() - (getBounds().width / 2);
    }

    public int getCollisionY() {
    	return getY() - (getBounds().height / 2);
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}