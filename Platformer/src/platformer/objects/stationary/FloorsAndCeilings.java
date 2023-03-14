package platformer.objects.stationary;

public class FloorsAndCeilings extends StationaryObject {

    public FloorsAndCeilings(int x, int y) {
        super(x, y);

        initFloorsAndCeilings();
    }
    
    private void initFloorsAndCeilings() {
        
        loadImage("../../Platformer_Game_Art/Buch_Platformer_Art/testingLevel.png");
        getImageDimensions();        
    }
}
