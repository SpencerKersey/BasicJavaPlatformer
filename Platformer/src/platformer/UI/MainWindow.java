package platformer.UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow extends JFrame {
	 /** Final used to denote the height of a popup menu */
    public static final int WIDTH_OF_POPUP = 400;

    /** Final used to denote the width of a popup menu */
    public static final int HEIGHT_OF_POPUP = 150;

    /** This variable denotes the maximum length of winSize input in gameSettings */
    public static final int MAX_INPUT_LENGTH = 6;

    /**
     * This is the amount of "wins" added to each players' score when a draw occurs
     */
    public static final double DRAW_POINTS = 0.5;

    /**
     * Height of the JFrame
     */
    public static final int HEIGHT = 1000;

    /**
     * Width of the JFrame
     */
    public static final int WIDTH = 1000;

    /**
     * Dimension of the Window
     */
    //public static Dimension WINDOW_DIMENSION;

    /**
     * This is the label used to display to the user info
     */
    //private final JLabel displayLabel;

    /**
     * Holds the information at the top of the screen
     */
    //private final JPanel gameInfoPanel;

    /**
     * Default Constructor
     * @param winSize this is the number of pieces needed to win the game
     * @param option is the option of whether to display the GUI or not. It is set so the GUI
     * doesn't display in test cases.
     */
    public MainWindow() {
        setTitle("Platformer");
        ImageIcon icon = new ImageIcon("../../Platformer_Game_Art/Buch_Platformer_Art/tempMainCharacter.png");
        setIconImage(icon.getImage());
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setResizable(true);
        
        // Add the Play area Board
        add(new GameBoard(this));
        
        // Set the minimum Window size and intial size
        setMinimumSize(new Dimension(320, 180));
        setSize(new Dimension(800, 450));

        // Sets the game GUI in the middle of the screen
        this.setLocationRelativeTo(null);
        
        // Set the GUI to be visible
        setVisible(true);

    }
}



