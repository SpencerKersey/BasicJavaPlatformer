package platformer.objects.characters;

public class Deputy extends CharacterBase {

    private final int INITIAL_X = 400;

    public Deputy(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {

        loadImage("../../Platformer_Game_Art/Buch_Platformer_Art/tempDeputy.png");
        getImageDimensions();
    }

    public void move() {

        if (x < 0) {
            x = INITIAL_X;
        }

        x -= 1;
    }
}