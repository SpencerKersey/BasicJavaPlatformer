package platformer.UI;

import platformer.executable.Runner;
import platformer.objects.stationary.FloorsAndCeilings;
import platformer.objects.stationary.StationaryObject;
import platformer.objects.characters.Robinhood;
import platformer.objects.characters.Deputy;
import platformer.objects.characters.Arrow;
import platformer.objects.characters.CharacterBase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
	private Timer timer;
    private Robinhood robinhood;
    private FloorsAndCeilings levelOne;
    private List<Deputy> aliens;
    private String state;
    private float scaleFactor = 1;
    private int bWidth = 320;
    private int bHeight = 180;
    private final int IROBIN_X = 40;
    private final int IROBIN_Y = 839;
    private int B_MIN_WIDTH = 320;
    private int B_MIN_HEIGHT = 180;
    private final int DELAY = 15;
    private MainWindow mw;

    public GameBoard( MainWindow i ) {
    	mw = i;
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        addComponentListener(new FrameListen());
        setFocusable(true);
        setBackground(Color.GRAY);
        state = "start";

        setMinimumSize(new Dimension(bWidth, bHeight));

        robinhood = new Robinhood(IROBIN_X, IROBIN_Y);
        levelOne = new FloorsAndCeilings(0, 0);

        initAliens();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void initAliens() {
        
        aliens = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (state.equals("start")) {
        	drawStartScreen(g);
        } else if(state.equals("ingame")) {
        	drawObjects(g);
        } else {
            drawGameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
    	
    	if (levelOne.isVisible()) {
            g.drawImage(levelOne.getImage(), 0, 0,
                    bWidth, bHeight, this);
        }
    	
        if (robinhood.isVisible()) {
        	// Calculate Robinhood's x and y value based on the center of the character and scaleFactor
        	updateRobinHood();
        	int x = (int)((robinhood.getX() - (robinhood.getBounds().width / 2)) * scaleFactor);
        	int y = (int)((robinhood.getY() - (robinhood.getBounds().height / 2)) * scaleFactor);
            g.drawImage(robinhood.getImage(), x, y,
                    (int)(robinhood.getBounds().width * scaleFactor), 
                    (int)(robinhood.getBounds().height * scaleFactor), this);
            //System.out.println("X:" + robinhood.getX() + " Y:" + robinhood.getY());
        }

        List<Arrow> ms = robinhood.getMissiles();

        for (Arrow missile : ms) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), 
                        missile.getY(), this);
            }
        }

        for (Deputy alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
        }
    }

    private void drawGameOver(Graphics g) {

        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (bWidth - fm.stringWidth(msg)) / 2,
                bHeight / 2);
    }
    
    private void drawStartScreen(Graphics g) {

        String msg = "Welcome to\nBasic Platformer!\n\nPress Space to Begin";
        Font small = new Font("Helvetica", Font.BOLD, 24);

        g.setColor(Color.white);
        g.setFont(small);
        drawStringCentered(g, msg, bWidth / 2, bHeight / 2);
    }
    
    private void drawStringCentered(Graphics g, String text, int x, int y) {
    	// Gets the lineHeight of the current font
    	int lineHeight = g.getFontMetrics().getHeight();
    	
    	// Gets the number of lines for calculating the positions of the text
    	int numLines = 1;
    	for (String line : text.split("\n"))
    		numLines++;
    	
    	// Calculates the location of the first line of text
    	y -= (numLines * lineHeight) / 2;
    	
    	// Draws each line of text as it sees a newline character
        for (String line : text.split("\n"))
            g.drawString(line, x - (g.getFontMetrics().stringWidth(line) / 2), y += lineHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        inGame();

        updateMissiles();

        checkCollisionPossibility();

        repaint();
    }

    private void inGame() {

        if (state.equals("over")) {
            timer.stop();
        }
    }

    private void updateRobinHood() {

        if (robinhood.isVisible()) {
            
            robinhood.move();
        }
    }

    private void updateMissiles() {

        List<Arrow> ms = robinhood.getMissiles();

        for (int i = 0; i < ms.size(); i++) {

            Arrow m = ms.get(i);

            if (m.isVisible()) {
                m.move();
            } else {
                ms.remove(i);
            }
        }
    }

    public void checkCollisionPossibility() {

        Rectangle rRhd = robinhood.getBounds();
        Rectangle rFAC = levelOne.getBounds();
        
        if(rRhd.intersects(rFAC)) {
        	if(checkFloorandCharacterCollision(robinhood, levelOne)) {
        		
        	}
        }

//        List<Arrow> ms = robinhood.getMissiles();
//
//        for (Arrow m : ms) {
//
//            Rectangle r1 = m.getBounds();
//
//            for (Deputy alien : aliens) {
//
//                Rectangle r2 = alien.getBounds();
//
//                if (r1.intersects(r2)) {
//                    
//                    m.setVisible(false);
//                    alien.setVisible(false);
//                }
//            }
//        }
    }
    
    public boolean checkFloorandCharacterCollision(CharacterBase chrctr, StationaryObject flor) {
    	
    	ArrayList<ArrayList<Integer>> cMask = chrctr.getMaskArray();
    	ArrayList<ArrayList<Integer>> fMask = flor.getMaskArray();
    	
    	// Find the first line where the two sprites might overlap
    	int cLine;
    	int fLine;
    	if (chrctr.getCollisionY() <= flor.getY()) {
    	    cLine = flor.getY() - chrctr.getCollisionY();
    	    fLine = 0;
    	} else {
    	    cLine = 0;
    	    fLine = chrctr.getCollisionY() - flor.getY();
    	}
    	int line = Math.max(cLine, fLine);
    	
    	// Get the shift between the two
    	int x = chrctr.getCollisionX() - flor.getX();
    	int maxLines = Math.max(chrctr.getBounds().height, flor.getBounds().height);
    	
    	// NOT FINISHED
//    	for ( line < maxLines; line ++) {
//    	    // if width > 32, then you need a second loop here
//    	    long playerMask = playerArray[linePlayer];
//    	    long enemyMask = enemyArray[lineEnemy];
//    	    // Reproduce the shift between the two sprites
//    	    if (x < 0) {
//    	    	playerMask << (-x);
//    	    } else {
//    	    	enemyMask << x;
//    	    }
//    	    // If the two masks have common bits, binary AND will return != 0
//    	    if ((playerMask & enemyMask) != 0) {
//    	        // Contact!
//    	    }
//
//    	}
    	
    	return false;
    }
    
    private class FrameListen implements ComponentListener{
        public void componentResized(ComponentEvent arg0) {
        	int mult = 0;
        	
            if((mw.getWidth() / 16 ) < (mw.getHeight() / 9)) {
            	mult = mw.getWidth() / 16;
            } else {
            	mult = mw.getHeight() / 9;
            }
            
            if(((16 * mult) > B_MIN_WIDTH) && ((9 * mult) > B_MIN_HEIGHT)) {
            	scaleFactor = ((float)bWidth) / 1600;
            	System.out.println(scaleFactor);
            	bWidth = 16 * mult;
            	bHeight = 9 * mult;
            	setSize(new Dimension(bWidth, bHeight));
            }
            
            setLocation(mw.getWidth() / 2 - bWidth / 2, mw.getHeight() / 2 - bHeight / 2);
            
            System.out.println(mw.getWidth() + " -> " + (16 * mult) + " - " + mw.getHeight() + " -> " + (9 * mult));
        }

		@Override
		public void componentMoved(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// TODO Auto-generated method stub
			
		}
    }
    
    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	robinhood.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	if(state.equals("start")) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    state = "ingame";
                }
        	} else {
        		robinhood.keyPressed(e);
        	}
        }
    }
}
