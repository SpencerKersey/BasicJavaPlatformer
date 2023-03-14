package platformer.objects.characters;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Robinhood extends CharacterBase {
	private double fltX;
	private double fltY;
	private double velX;
    private double velY;
    private boolean leftNotPressed;
    private boolean rightNotPressed;
    private List<Arrow> arrows;

    public Robinhood(int x, int y) {
        super(x, y);
        fltX = x;
        fltY = y;

        initRobinhood();
    }

    private void initRobinhood() {
        
        arrows = new ArrayList<>();
        loadImage("../../Platformer_Game_Art/Buch_Platformer_Art/tempMainCharacter.png");
        getImageDimensions();

        velX = 0;
        velY = 0;
        leftNotPressed = true;
        rightNotPressed = true;
    }
    
    public void jump() {
    	velY = -8;
    }

    public void move() {
    	
    	if(leftNotPressed && rightNotPressed) {
    		if(velX > 0) {
    			velX -= .147;
    			if(velX < .147) {
    				velX = 0;
    			}
    		} else {
    			velX += .147;
    			if(velX > -.147) {
    				velX = 0;
    			}
    		}
    	}
    	
    	velY += .147;


        fltX += velX;
        fltY += velY;
        
        // Make sure the main character doesnt go off the side of the map
        if (fltX <= 29) {
        	fltX = 29;
        	if(velX < 0) {
        		velX = 0;
        	}
        } else if(fltX >= 1572) {
        	fltX = 1572;
        	if(velX > 0) {
        		velX = 0;
        	}
        }
        
        // Make sure the main character doesnt go off the bottom/top of the map
        if (fltY <= 41) {
        	fltY = 41;
        	if(velY < 0) {
        		velY = 0;
        	}
        } else if(fltY >= 839) {
        	fltY = 839;
        	if(velY > 0) {
        		velY = 0;
        	}
        }
        
        x = (int)fltX;
        y = (int)fltY;
//        System.out.print("Vx:" + velX + " Vy:" + velY);
//        System.out.println("Dx:" + fltX + " Dy:" + fltY);
    }

    public List<Arrow> getMissiles() {
        return arrows;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_Q) {
            fire();
        }
        
        if (key == KeyEvent.VK_SPACE) {
            jump();
        }

        if (key == KeyEvent.VK_LEFT) {
            velX = -4;
            leftNotPressed = false;
            System.out.println("FALSE");
        }

        if (key == KeyEvent.VK_RIGHT) {
            velX = 4;
            rightNotPressed = false;
            System.out.println("FALSE");
        }
    }

    public void fire() {
        arrows.add(new Arrow(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	leftNotPressed = true;
        	System.out.println("TRUE");
        }

        if (key == KeyEvent.VK_RIGHT) {
        	rightNotPressed = true;
        	System.out.println("TRUE");
        }
//
//        if (key == KeyEvent.VK_UP) {
//        	velY = 0;
//        }
//
//        if (key == KeyEvent.VK_DOWN) {
//        	velY = 0;
//        }
    }
}
