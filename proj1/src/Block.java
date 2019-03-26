public class Block {
    int posX,
        posY;
    Orientation orientation;
    Boolean dead;

    enum Orientation{
        VERTICAL,
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    Block(int x, int y){
        posX = x;
        posY = y;
        orientation = Orientation.VERTICAL;
        dead = false;
    }
    Block(){
        orientation = Orientation.VERTICAL;
        dead = false;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void moveNorth(Board mBoard){
        if(orientation == Orientation.VERTICAL){
            if(posX > 1){
                posX -= 2;
                orientation = Orientation.NORTH;
            }
        }
    }
}
