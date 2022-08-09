package Ver1;

public class Field {
    private final Square[][] field;
    private final int bombs;
    private int flagged;
    private boolean lost = false;

    public Field(int h, int w, int b) {
        field = new Square[h][w];
        bombs = b;
        setField();
        setNumbers();
        flagged = 0;
    }

    //set the field squares to empty or bomb
    private void setField() {
        for(int h = 0; h < field.length; h++)
            for (int w = 0; w < field[0].length; w++)
                field[h][w] = new Square(Square.TILE_EMPTY);
        int bombsLeft = bombs;
        while(bombsLeft > 0) {
            int ry = (int) (Math.random() * field.length);
            int rx = (int) (Math.random() * field[0].length);
            if (field[ry][rx].tile == Square.TILE_EMPTY) {
                field[ry][rx].tile = Square.TILE_BOMB;
                bombsLeft--;
            }
        }
    }

    //give each empty square their number of neighboring bombs
    private void setNumbers() {
        for (int h = 0; h < field.length; h++) {
            for (int w = 0; w < field[0].length; w++) {
                if(field[h][w].tile == Square.TILE_BOMB) {
                    incrementNeighborBombs(h-1,w);
                    incrementNeighborBombs(h-1,w+1);
                    incrementNeighborBombs(h,w+1);
                    incrementNeighborBombs(h+1,w+1);
                    incrementNeighborBombs(h+1,w);
                    incrementNeighborBombs(h+1,w-1);
                    incrementNeighborBombs(h,w-1);
                    incrementNeighborBombs(h-1,w-1);
                }
            }
        }
    }

    private void incrementNeighborBombs(int h, int w) {
        if(h >= 0 && w >= 0 && h < field.length && w < field[0].length) {
            field[h][w].neighborBombs++;
        }
    }

    //print the field
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\n   ");
        for (int w = 0; w < field[0].length; w++) {
            s.append(num(w));
        }
        s.append(" x\n");
        for(int h = 0; h < field.length; h++) {
            s.append(num(h));
            for(int w = 0; w < field[0].length; w++) {
                s.append(field[h][w]);
            }
            s.append("\n");
        }
        s.append("y");
        s.append("   ".repeat(field[0].length));
        s.append("Bombs left: " + (bombs - flagged) + "\n");
        return s.toString();
    }

    //convert a number to a string of length 3
    private String num(int n) {
        if(n < 10) return n + "  ";
        if(n < 100) return n + " ";
        return n + "";
    }

    //reveal the tile at (w,h). Return false if it is flagged, true otherwise; if safe, reveal nearby tiles
    public boolean reveal(int h, int w) {
        if(field[h][w].state == Square.STATE_FLAGGED) return false;
        if(
                field[h][w].reveal()
        ) lost = true;
        if(field[h][w].safe()) {
            chainReveal(h-1,w);
            chainReveal(h-1,w+1);
            chainReveal(h,w+1);
            chainReveal(h+1,w+1);
            chainReveal(h+1,w);
            chainReveal(h+1,w-1);
            chainReveal(h,w-1);
            chainReveal(h-1,w-1);
        }
        return true;
    }

    //reveal a tile only if empty
    private void chainReveal(int h, int w) {
        if(h < 0 || w < 0 || h >= field.length || w >= field[0].length) return;
        if(field[h][w].state == Square.STATE_HIDDEN && field[h][w].tile == Square.TILE_EMPTY) {
            reveal(h,w);
        }
    }

    //toggle flag on a tile at (w,h). Return false if it was already revealed, true otherwise
    public boolean toggleFlag(int h, int w) {
        if(field[h][w].state == Square.STATE_REVEALED) return false;
        if(field[h][w].state == Square.STATE_HIDDEN) {
            field[h][w].state = Square.STATE_FLAGGED;
            flagged++;
        } else {
            field[h][w].state = Square.STATE_HIDDEN;
            flagged--;
        }
        return true;
    }
    //same method as toggleFlag(int,int)
    public boolean flag(int h, int w) {
        return toggleFlag(h,w);
    }

    //return true if the game is lost
    public boolean isLost() {
        return lost;
    }

    //return true if the game is won
    public boolean isWon() {
        for(Square[] row: field) {
            for(Square s: row) {
                if(s.state == Square.STATE_HIDDEN) return false;
                if(s.state == Square.STATE_FLAGGED && s.tile == Square.TILE_EMPTY) return false;
            }
        }
        return true;
    }

    //return the number of bombs in this round
    public int bombs() {
        return bombs;
    }

    //reveal the whole field
    public void revealAll() {
        for(Square[] row: field)
            for(Square s: row)
                s.reveal();
    }
}
