package Ver1;

public class Square {
    public static final int STATE_HIDDEN = 0;
    public static final int STATE_FLAGGED = 1;
    public static final int STATE_REVEALED = 2;

    public static final char TILE_EMPTY = '.';
    public static final char TILE_BOMB = '▪';

    int state;
    char tile;

    int neighborBombs;

    public Square(char tile) {
        this.tile = tile;
        this.state = STATE_HIDDEN;
        neighborBombs = 0;
    }

    public String toString() {
        if(state == STATE_FLAGGED) {
            return "?  "; //🚩
        } else if(state == STATE_HIDDEN) {
            return "■  ";
        }
        //state is revealed
        if(tile == TILE_BOMB || neighborBombs == 0) {
            return tile + "  ";
        } else {
            return neighborBombs + "  ";
        }
    }

    //reveal the tile. Return true if it was a bomb
    public boolean reveal() {
        this.state = STATE_REVEALED;
        return tile != TILE_EMPTY;
    }

    //returns true if a tile is safe: has no neighboring bombs
    public boolean safe() {
        return neighborBombs == 0;
    }
}
