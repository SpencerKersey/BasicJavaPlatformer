package platformer.objects.characters;

public class Arrow extends CharacterBase {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;

    public Arrow(int x, int y) {
        super(x, y);

        initMissile();
    }
    
    private void initMissile() {
        
        loadImage("../../Platformer_Game_Art/Buch_Platformer_Art/tempArrow.png");
        getImageDimensions();        
    }

    public void move() {
        
        x += MISSILE_SPEED;
        
        if (x > BOARD_WIDTH)
            visible = false;
    }
}
