public class Tile {

    private boolean isOccupied = false;
    private int row;
    private int col;
    private int pos;

    public Tile (int row, int col, int pos) {
        this.row = row;
        this.col = col;
        this.pos = pos;
    }

    public void setOccupied (boolean bool) { this.isOccupied = bool; }

    public int getRow () {
        return this.row;
    }

    public int getCol () {
        return this.col;
    }

    public int getPos () {
        return this.pos;
    }

    public boolean getOccupied () {
        return isOccupied;
    }

}
