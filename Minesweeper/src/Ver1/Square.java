package Ver1;

public class Square {

    State state;
    TileType tile;

    int neighborBombs;

    public Square(TileType tile) {
        this.tile = tile;
        this.state = State.HIDDEN;
        neighborBombs = 0;
    }

    public String toString() {
        if(state == State.FLAGGED) {
            return "?  "; //🚩 <- too big (width)
        } else if(state == State.HIDDEN) {
            return "■  ";
        }
        //state is revealed
        if(tile == TileType.BOMB || neighborBombs == 0) {
            if(tile == TileType.BOMB)
                return "▪  ";
            else //has to be empty
                return ".  ";
        } else {
            return neighborBombs + "  ";
        }
    }

    //reveal the tile. Return true if it was a bomb
    public boolean reveal() {
        this.state = State.REVEALED;
        return tile != TileType.EMPTY;
    }

    //returns true if a tile is safe: has no neighboring bombs
    public boolean safe() {
        return neighborBombs == 0;
    }
}
