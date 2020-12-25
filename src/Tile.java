import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile {

    private boolean isOccupied = false;
    private int row;
    private int col;
    private int pos;
    private ImageView img;

    public Tile (int row, int col, int pos, ImageView img) {
        this.row = row;
        this.col = col;
        this.pos = pos;
    }

    public void setOccupied () {
        this.isOccupied = !this.isOccupied;
    }

    public int getRow () {
        return this.row;
    }

    public int getCol () {
        return this.col;
    }

    public int getPos () {
        return this.pos;
    }

    public boolean occupied () {
        return isOccupied;
    }
}
